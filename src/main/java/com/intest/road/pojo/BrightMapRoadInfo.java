package com.intest.road.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrightMapRoadInfo {
    private String roadName;
    private double[][] roadBound;

    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BrightMapLandInfo[] landList;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public double[][] getRoadBound() {
        return roadBound;
    }

    public void setRoadBound(double[][] roadBound) {
        this.roadBound = roadBound;
    }

    public BrightMapLandInfo[] getLandList() {
        return landList;
    }

    public void setLandList(BrightMapLandInfo[] landList) {
        this.landList = landList;
    }
}
