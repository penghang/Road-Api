package com.intest.road.pojo;

/**
 * @author PengHang
 * @date 2018/11/14
 */
public class RoadInfo extends FieldRoad {
    private double[][] bounds;

    public double[][] getBounds() {
        return bounds;
    }

    public void setBounds(double[][] bounds) {
        this.bounds = bounds;
    }
}
