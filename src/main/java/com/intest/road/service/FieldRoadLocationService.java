package com.intest.road.service;

import com.intest.road.pojo.FieldRoadLocation;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/13
 */
public interface FieldRoadLocationService {
    public List<FieldRoadLocation> queryList(Short pId, Short coordinateType);

}
