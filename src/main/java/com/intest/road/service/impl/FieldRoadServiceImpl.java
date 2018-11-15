package com.intest.road.service.impl;

import com.intest.road.mapper.FieldRoadLandMapper;
import com.intest.road.mapper.FieldRoadLocationMapper;
import com.intest.road.mapper.FieldRoadMapper;
import com.intest.road.pojo.*;
import com.intest.road.service.FieldRoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/13
 */
@Service
public class FieldRoadServiceImpl implements FieldRoadService {
    @Autowired
    private FieldRoadMapper fieldRoadMapper;

    @Autowired
    private FieldRoadLandMapper fieldRoadLandMapper;

    @Autowired
    private FieldRoadLocationMapper fieldRoadLocationMapper;

    @Override
    public void save(FieldRoad fieldRoad) {
        fieldRoadMapper.insert(fieldRoad);
    }

    @Override
    public void update(FieldRoad fieldRoad) {
        fieldRoadMapper.updateByPrimaryKey(fieldRoad);
    }

    @Override
    public void deleteAll() {
        fieldRoadMapper.deleteAll();
    }

    @Override
    public FieldRoad query(short id) {
        return fieldRoadMapper.selectByPrimaryKey(id);
    }

    @Override
    public FieldRoad query(String roadName) {
        return fieldRoadMapper.selectByRoadName(roadName);
    }

    @Override
    public List<FieldRoad> queryList() {
        return fieldRoadMapper.selectAll();
    }

    @Override
    public List<FieldRoad> queryList(short roadType) {
        return fieldRoadMapper.selectByRoadType(roadType);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sync(List<BrightMapRoadInfo> roadInfoList) {
        // 清空之前的数据
        fieldRoadMapper.deleteAll();
        fieldRoadLandMapper.deleteAll();
        fieldRoadLocationMapper.deleteAll();

        short roadId = 0;
        short landId = 0;
        short roadType = 1;

        for (BrightMapRoadInfo roadInfo: roadInfoList) {
            FieldRoad fieldRoad = new FieldRoad();
            fieldRoad.setId(roadId);
            fieldRoad.setRoadName(roadInfo.getRoadName());
            fieldRoad.setRoadType(roadType);
            // 写入道路信息
            fieldRoadMapper.insert(fieldRoad);

            for (BrightMapLandInfo landInfo: roadInfo.getLandList()) {
                FieldRoadLand fieldRoadLand = new FieldRoadLand();
                fieldRoadLand.setId(landId);
                fieldRoadLand.setLandName(landInfo.getLandName());
                fieldRoadLand.setRoadId(roadId);
                // 写入车道信息
                fieldRoadLandMapper.insert(fieldRoadLand);

                ArrayList<double[][]> landBounds = landInfo.getLandBound();
                for (int i = 0; i < landBounds.size(); i++) {
                    // 写入车道边界坐标
                    addLocations(landBounds.get(i), (short)2, landId, (short)i);
                }
                landId++;
            }
            // 写入道路边界坐标
            addLocations(roadInfo.getRoadBound(), (short)1, roadId, (short)0);
            roadId++;
        }
    }

    /**
     * 坐标数据写入
     * @param bounds
     * @param coordinateType
     * @param pId
     * @param groupId
     */
    private void addLocations(double[][] bounds, short coordinateType, short pId, short groupId) {
        Integer seq = 0;
        for (double[] bound : bounds) {
            FieldRoadLocation fieldRoadLocation = new FieldRoadLocation();
            fieldRoadLocation.setLat(bound[1]);
            fieldRoadLocation.setLng(bound[0]);
            fieldRoadLocation.setCoordinateType(coordinateType);
            fieldRoadLocation.setPid(pId);
            fieldRoadLocation.setGroupId(groupId);
            fieldRoadLocation.setSeq(seq++);
            fieldRoadLocationMapper.insert(fieldRoadLocation);
        }
    }
}
