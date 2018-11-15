package com.intest.road.service.impl;

import com.intest.road.mapper.FieldRoadLocationMapper;
import com.intest.road.pojo.FieldRoadLocation;
import com.intest.road.service.FieldRoadLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/14
 */
@Service
public class FieldRoadLocationServiceImpl implements FieldRoadLocationService {
    @Autowired
    private FieldRoadLocationMapper fieldRoadLocationMapper;

    @Override
    public List<FieldRoadLocation> queryList(Short pId, Short coordinateType) {
        return fieldRoadLocationMapper.selectByPidAndType(pId, coordinateType);
    }
}
