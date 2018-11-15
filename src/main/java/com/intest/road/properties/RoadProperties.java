package com.intest.road.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author PengHang
 * @date 2018/11/9
 */
@Component
@ConfigurationProperties(prefix = "road")
public class RoadProperties {
    private String mapAddress;

    public String getMapAddress() {
        return mapAddress;
    }

    public void setMapAddress(String mapAddress) {
        this.mapAddress = mapAddress;
    }
}
