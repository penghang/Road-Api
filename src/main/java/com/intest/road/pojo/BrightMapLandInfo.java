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

    public ArrayList<double[][]> getLandBound() {
        return landBound;
    }

    public void setLandBound(ArrayList<double[][]> landBound) {
        this.landBound = landBound;
    }

    private ArrayList<double[][]> landBound;

    public String getLandName() {
        return landName;
    }

    public void setLandName(String landName) {
        this.landName = landName;
    }
}
