package com.intest.road.service;

import com.intest.road.pojo.FieldRoadLand;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/13
 */
public interface FieldRoadLandService {
    public List<FieldRoadLand> queryList(Short roadId);
}
