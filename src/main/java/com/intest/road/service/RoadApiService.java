package com.intest.road.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intest.road.enums.ResultEnum;
import com.intest.road.exception.RoadException;
import com.intest.road.pojo.*;
import com.intest.road.properties.BrightMapProperties;
import com.intest.road.utils.FileUtil;
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
            BrightMapLandInfo[] brightMapLandInfos = roadInfo.getLandList();
            for (int j = 0; j < brightMapLandInfos.length; j++) {
                ArrayList<double[][]> boundList = brightMapLandInfos[j].getLandBound();
                for (int k = 0; k < boundList.size(); k++) {
                    if (polygonService.isPointInPolygon(point, boundList.get(k))) {
                        return roadInfo.getRoadName();
                    }
                }
            }
        }
        return "";
    }

    /**
     * 获取道路列表
     * @return
     */
    public List<String> getRoads() {
        ArrayList<String> roads = new ArrayList<>();
        roadInfoList.forEach(roadInfo -> {
            roads.add(roadInfo.getRoadName());
        });
        return roads;
    }

    /**
     * 获取道路列表
     * @return
     */
    public String[] getByroads() {

        String filePath = getFilePath("byroad");
        String content = FileUtil.readTxtFile(filePath);
        return content.split("\r\n");
    }

    public boolean setByroads(String names) {
        String filePath = getFilePath("byroad");
        return FileUtil.writeFile(filePath, names.replace(",","\r\n"));
    }

    /**
     * 获取地图地址
     * @return
     */
    public String getMapAddress() {
        String filePath = getFilePath("map-address");
        return FileUtil.readTxtFile(filePath);
    }

    public Object setMapAddress(String url) {
        String filePath = getFilePath("map-address");
        return FileUtil.writeFile(filePath, url);
    }

    public boolean loadFromBrightMap() {
        List<BrightMapRoadInfo> brightMapRoadInfoList = requestRoadInfoFromBrightMap();
        if (status) {
            roadInfoList = brightMapRoadInfoList;
        }
        return getStatus();
    }

    private String getFilePath(String fileName) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        return path + "/conf/" + fileName;
    }
}
