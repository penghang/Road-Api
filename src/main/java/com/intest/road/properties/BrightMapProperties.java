package com.intest.road.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@Component
@ConfigurationProperties(prefix = "bright-map")
public class BrightMapProperties {
    private String url;
    private String roadInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoadInfo() {
        return roadInfo;
    }

    public void setRoadInfo(String roadInfo) {
        this.roadInfo = roadInfo;
    }
}
