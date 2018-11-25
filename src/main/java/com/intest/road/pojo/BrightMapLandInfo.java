package com.intest.road.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrightMapLandInfo {
    private String landName;

    private double[][] landBound;

    public double[][] getLandBound() {
        return landBound;
    }

    public void setLandBound(double[][] landBound) {
        this.landBound = landBound;
    }

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }
}
