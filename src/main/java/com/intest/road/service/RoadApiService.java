package com.intest.road.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intest.road.enums.ResultEnum;
import com.intest.road.exception.RoadException;
import com.intest.road.pojo.*;
import com.intest.road.properties.BrightMapProperties;
import com.intest.road.properties.RoadProperties;
import com.intest.road.utils.HttpClientUtil;
import com.intest.road.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@Service
public class RoadApiService {

    @Autowired
    private PolygonService polygonService;

    @Autowired
    private RoadProperties roadProperties;

    @Autowired
    private FieldRoadService fieldRoadService;

    @Autowired
    private FieldRoadLandService fieldRoadLandService;

    @Autowired
    private FieldRoadLocationService fieldRoadLocationService;

    private BrightMapProperties brightMapProperties;

    private static final Logger logger = LoggerFactory.getLogger(RoadApiService.class);

    private List<BrightMapRoadInfo> roadInfoList;

    private static int HTTPREQUEST_OK = 200;

    public boolean getStatus() {
        return status;
    }

    private boolean status = false;

    @Autowired
    public RoadApiService(BrightMapProperties brightMapProperties) {
        this.brightMapProperties = brightMapProperties;
        roadInfoList = requestRoadInfoFromBrightMap();
    }

    public List<BrightMapRoadInfo> requestRoadInfoFromBrightMap() {
        logger.info("从晶众接口获取道路坐标");
        List<BrightMapRoadInfo> result = new ArrayList<>();
        List<List<BrightMapRoadInfo>> cache;
        try{
            HttpClientResult httpClientResult = HttpClientUtil.doGet(brightMapProperties.getRoadInfo());
            if(httpClientResult.getCode() == HTTPREQUEST_OK) {
                status = true;
                cache = JsonUtil.deserialize(httpClientResult.getContent(), new TypeReference<List<List<BrightMapRoadInfo>>>() {
                });
                if (cache.size() > 0) {
                    result = cache.get(0);
                }
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
            logger.error("从晶众接口获取道路坐标失败", e);
        }
        return result;
    }

    public BrightMapProperties getConfig() {
        return brightMapProperties;
    }

    /**
     * 获取所有道路信息列表-返回晶众接口的数据格式
     * @return
     */
    public List<BrightMapRoadInfo> getRoadsInfo() {
        return roadInfoList;
    }

    /**
     * 根据道路名称获取道路信息-返回晶众接口的数据格式
     * @param roadName
     * @return
     */
    public BrightMapRoadInfo getRoadInfo(String roadName) {
        for (int i = 0; i < roadInfoList.size(); i++) {
            BrightMapRoadInfo roadInfo = roadInfoList.get(i);
            if (roadInfo.getRoadName().equals(roadName)) {
                return roadInfo;
            }
        }
        throw new RoadException(ResultEnum.UNKNOWN_ROAD);
    }
    /**
     * 根据道路id获取道路信息
     * @param id
     * @return
     */
    public BrightMapRoadInfo getRoadInfo(Integer id) {
        if (id >= 0 && id < roadInfoList.size()) {
            return roadInfoList.get(id);
        }
        throw new RoadException(ResultEnum.UNKNOWN_ROAD);
    }

    /**
     * 通过经纬度查询道路信息
     * @param lat
     * @param lng
     * @return
     */
    public String getRoadName(double lat, double lng) {
        for (int i = 0; i < roadInfoList.size(); i++) {
            BrightMapRoadInfo roadInfo = roadInfoList.get(i);
            double[] point = new double[]{lng, lat};
            if (polygonService.isPointInPolygon(point, roadInfo.getRoadBound())){
                return roadInfo.getRoadName();
            }
        }
        return "";
    }

    /**
     * 获取道路列表
     * @return
     */
    public List<FieldRoad> getRoads() {
        return fieldRoadService.queryList();
    }

    /**
     * 获取道路列表
     * @return
     */
    public List<FieldRoad> getRoads(short roadType) {
        return fieldRoadService.queryList(roadType);
    }

    /**
     * 获取地图地址
     * @return
     */
    public String getMapAddress() {
        return roadProperties.getMapAddress();
    }

    /**
     * 从晶众地图API同步数据
     */
    public void sync() {
        fieldRoadService.sync(roadInfoList);
    }

    public boolean loadFromBrightMap() {
        List<BrightMapRoadInfo> brightMapRoadInfoList = requestRoadInfoFromBrightMap();
        if (status) {
            roadInfoList = brightMapRoadInfoList;
        }
        return getStatus();
    }

    public boolean loadFromDB() {
        List<BrightMapRoadInfo> brightMapRoadInfoList = loadRoadInfoFromDB();
        if (status) {
            roadInfoList = brightMapRoadInfoList;
        }
        return getStatus();
    }

    public List<BrightMapRoadInfo> loadRoadInfoFromDB() {
        logger.info("从数据库获取道路坐标");
        List<BrightMapRoadInfo> brightMapRoadInfoList = new ArrayList<>();
        List<FieldRoad> fieldRoadList = fieldRoadService.queryList();
        fieldRoadList.forEach(fieldRoad -> {
            BrightMapRoadInfo brightMapRoadInfo = new BrightMapRoadInfo();
            // 设置道路名称
            brightMapRoadInfo.setRoadName(fieldRoad.getRoadName());

            // 设置车道
            List<FieldRoadLand> fieldRoadLandList = fieldRoadLandService.queryList(fieldRoad.getId());
            BrightMapLandInfo[] brightMapLandInfos = new BrightMapLandInfo[fieldRoadLandList.size()];
            for (int i = 0; i < fieldRoadLandList.size(); i++) {
                FieldRoadLand fieldRoadLand = fieldRoadLandList.get(i);
                BrightMapLandInfo brightMapLandInfo = new BrightMapLandInfo();
                brightMapLandInfo.setLandName(fieldRoadLand.getLandName());

                // 设置边界坐标
                List<FieldRoadLocation> locations = fieldRoadLocationService.queryList(fieldRoadLand.getId(), (short)2);
                ArrayList<double[][]> landBounds = new ArrayList<>();

                Short lastGroupId;
                int size = locations.size();
                for (int j = 0; j < size;) {
                    FieldRoadLocation location = locations.get(j);
                    Short groupId = location.getGroupId();
                    lastGroupId = groupId;
                    ArrayList<double[]> groupBounds = new ArrayList<>();
                    while (groupId.equals(lastGroupId)) {
                        double[] point = {location.getLng(), location.getLat()};
                        groupBounds.add(point);
                        j++;
                        if (j >= size) {
                            break;
                        } else {
                            location = locations.get(j);
                            groupId = location.getGroupId();
                        }
                    }

                    landBounds.add(groupBounds.toArray(new double[groupBounds.size()][2]));
                }
                brightMapLandInfo.setLandBound(landBounds);
                brightMapLandInfos[i] = brightMapLandInfo;
            }
            brightMapRoadInfo.setLandList(brightMapLandInfos);

            // 设置边界坐标
            List<FieldRoadLocation> locations = fieldRoadLocationService.queryList(fieldRoad.getId(), (short)1);
            double[][] roadBounds = new double[locations.size()][2];
            for (int i = 0; i < locations.size(); i++) {
                roadBounds[i][0] = locations.get(i).getLng();
                roadBounds[i][1] = locations.get(i).getLat();
            }
            brightMapRoadInfo.setRoadBound(roadBounds);
            brightMapRoadInfoList.add(brightMapRoadInfo);
        });
        if (brightMapRoadInfoList.size() > 0) {
            status = true;
        } else {
            status = false;
        }
        return brightMapRoadInfoList;
    }
}
