package com.intest.road.service;

import com.intest.road.pojo.FieldRoad;
import com.intest.road.pojo.BrightMapRoadInfo;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/13
 */
public interface FieldRoadService {
    public void save(FieldRoad fieldRoad);
    public void update(FieldRoad fieldRoad);
    public void deleteAll();
    public FieldRoad query(short id);
    public FieldRoad query(String roadName);
    public List<FieldRoad> queryList();
    public List<FieldRoad> queryList(short roadType);
    /**
     * 同步数据
     * @param roadInfoList
     */
    public void sync(List<BrightMapRoadInfo> roadInfoList);
}
