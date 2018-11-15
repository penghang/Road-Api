package com.intest.road.service.impl;

import com.intest.road.mapper.FieldRoadLandMapper;
import com.intest.road.pojo.FieldRoadLand;
import com.intest.road.service.FieldRoadLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/14
 */
@Service
public class FieldRoadLandServiceImpl implements FieldRoadLandService {
    @Autowired
    private FieldRoadLandMapper fieldRoadLandMapper;

    @Override
    public List<FieldRoadLand> queryList(Short roadId) {
        return fieldRoadLandMapper.selectByRoadId(roadId);
    }
}
