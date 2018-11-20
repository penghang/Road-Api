package com.intest.road.controller;

import com.intest.road.pojo.Result;
import com.intest.road.pojo.BrightMapRoadInfo;
import com.intest.road.properties.BrightMapProperties;
import com.intest.road.service.RoadApiService;
import com.intest.road.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
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
     * 获取全部道路数据-返回晶众接口的数据格式
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/roadsInfo", "/bounds"})
    public Result<List<BrightMapRoadInfo>> getRoadsInfo() {
        return ResultUtil.success(roadApiService.getRoadsInfo());
    }

    /**
     * 获取全部道路数据-返回晶众接口的数据格式
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/roadInfo", "/bound"})
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
    public Result<List<String>> getRoadNames() {
        return ResultUtil.success(roadApiService.getRoads());
    }

    /**
     * 获取所有辅路名称
     * @return
     */
    @GetMapping(value = "/byroads")
    public Result<String[]> getByroads() {
        return ResultUtil.success(roadApiService.getByroads());
    }

    /**
     * 设置辅路
     * @param names
     * @return
     */
    @PostMapping(value = "/byroads")
    public Result<Boolean> setByroads(@RequestParam("names") String names) {
        return ResultUtil.success(roadApiService.setByroads(names));
    }

    /**
     * 获取地图地址
     * @return
     */
    @GetMapping(value = "/mapAddress")
    public Result<String> getMapAddress() {
        return ResultUtil.success(roadApiService.getMapAddress());
    }

    @PostMapping(value = "/mapAddress")
    public Result<Boolean> setMapAddress(@RequestParam("url") String url) {
        return ResultUtil.success(roadApiService.setMapAddress(url));
    }
}
