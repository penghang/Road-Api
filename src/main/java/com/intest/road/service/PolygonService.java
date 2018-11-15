package com.intest.road.service;

import org.springframework.stereotype.Service;

/**
 * @author PengHang
 * @date 2018/11/9
 */
@Service
public class PolygonService {
    public boolean isPointInPolygon(double[] point, double[][] points) {
        int len = points.length;
        final int unPolygonLines = 3;
        if (len < unPolygonLines) {
            return false;
        }
        boolean result = false;
        for (int i = 0, j = len - 1; i < len; j = i++) {
            double[] p1 = points[i];
            double[] p2 = points[j];
            // 纵坐标落在2点之间
            boolean yContains = (p1[1] < point[1] && p2[1] >= point[1])
                || (p2[1] < point[1] && p1[1] >= point[1]);
            if (yContains) {
                boolean isCross = p1[0] + (point[1] - p1[1]) / (p2[1] - p1[1]) * (p2[0] - p1[0]) < point[0];
                if (isCross) {
                    result = !result;
                }
            }
        }
        return result;
    }
}
