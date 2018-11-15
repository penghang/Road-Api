package com.intest.road.controller;

import com.intest.road.enums.ResultEnum;
import com.intest.road.exception.RoadException;
import com.intest.road.pojo.FieldRoad;
import com.intest.road.pojo.Result;
import com.intest.road.pojo.BrightMapRoadInfo;
import com.intest.road.pojo.RoadInfo;
import com.intest.road.properties.BrightMapProperties;
import com.intest.road.service.RoadApiService;
import com.intest.road.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@RestController
public class RoadApiController {
    @Autowired
    private RoadApiService roadApiService;

    private static Date initTime = new Date();

    /**
     * 查看配置
     * @return
     */
    @GetMapping(value = "/config")
    public Result<BrightMapProperties> getConfig() {
        return ResultUtil.success(roadApiService.getConfig());
    }

    @GetMapping(value = "/initTime")
    public Result<String> getInitTime() {
        DateFormat df = DateFormat.getDateTimeInstance();
        return ResultUtil.success(df.format(initTime));
    }

    /**
     * 获取状态
     * @return
     */
    @GetMapping(value = "/status")
    public Result<Boolean> getStatus() {
        return ResultUtil.success(roadApiService.getStatus());
    }

    /**
     * 从晶众地图加载数据
     * @return
     */
    @GetMapping(value = "/load/bm")
    public Result loadFromBrightMap() {
        return ResultUtil.success(roadApiService.loadFromBrightMap());
    }

    /**
     * 从数据库加载数据
     * @return
     */
    @GetMapping(value = "/load/db")
    public Result loadFromDB() {
        return ResultUtil.success(roadApiService.loadFromDB());
    }

    /**
     * 获取全部道路数据-返回晶众接口的数据格式
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/roadsInfo")
    public Result<List<BrightMapRoadInfo>> getRoadsInfo() {
        return ResultUtil.success(roadApiService.getRoadsInfo());
    }

    /**
     * 获取全部道路数据-返回晶众接口的数据格式
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/roadInfo")
    public Result<BrightMapRoadInfo> getRoadInfo(@RequestParam("roadName") String roadName) {
        return ResultUtil.success(roadApiService.getRoadInfo(roadName));
    }

    /**
     * 通过经纬度定位道路信息
     * @param lat
     * @param lng
     * @return
     */
    @GetMapping(value = "/location")
    public Result<String> getRoadInfoByLatLng(@RequestParam("lat") double lat,
                                        @RequestParam("lng") double lng) {
        return ResultUtil.success(roadApiService.getRoadName(lat, lng));
    }

    /**
     * 获取所有道路名称
     * @return
     */
    @GetMapping(value = "/roads")
    public Result<List<FieldRoad>> getRoadNames() {
        return ResultUtil.success(roadApiService.getRoads());
    }

    /**
     * 获取所有辅路名称
     * @return
     */
    @GetMapping(value = "/roads/{type}")
    public Result<List<FieldRoad>> getAssistNames(@PathVariable("type") short roadType) {
        return ResultUtil.success(roadApiService.getRoads(roadType));
    }

    /**
     * 获取地图地址
     * @return
     */
    @GetMapping(value = "/mapAddress")
    public Result<String> getMapAddress() {
        return ResultUtil.success(roadApiService.getMapAddress());
    }

    @PostMapping(value = "/sync/{key}")
    public Result sync(@PathVariable("key") String key) {
        final String KEY = "intest";
        if (key.equals(KEY)) {
            roadApiService.sync();
            return ResultUtil.success();
        } else {
            throw new RoadException(ResultEnum.KEY_ERROR);
        }
    }

    @GetMapping(value = "/bounds/all")
    public Result<List<RoadInfo>> getAllRoadBounds() {
        List<FieldRoad> fieldRoadList = roadApiService.getRoads();
        List<RoadInfo> roadInfoList = new ArrayList<>();
        fieldRoadList.forEach(fieldRoad -> {
            BrightMapRoadInfo brightMapRoadInfo = roadApiService.getRoadInfo((int)fieldRoad.getId());
            RoadInfo roadInfo = new RoadInfo();
            roadInfo.setBounds(brightMapRoadInfo.getRoadBound());
            roadInfo.setId(fieldRoad.getId());
            roadInfo.setRoadName(fieldRoad.getRoadName());
            roadInfo.setRoadType(fieldRoad.getRoadType());
            roadInfoList.add(roadInfo);
        });
        return ResultUtil.success(roadInfoList);
    }

    @GetMapping(value = "/bounds")
    public Result<double[][]> getRoadBoundsByName(@RequestParam("roadName") String roadName) {
        BrightMapRoadInfo roadInfo = roadApiService.getRoadInfo(roadName);
        return ResultUtil.success(roadInfo.getRoadBound());
    }

    @GetMapping(value = "/bounds/{id}")
    public Result<double[][]> getRoadBoundsById(@PathVariable("id") Integer id) {
        BrightMapRoadInfo roadInfo = roadApiService.getRoadInfo(id);
        return ResultUtil.success(roadInfo.getRoadBound());
    }
}
