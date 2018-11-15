package com.intest.road;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author PengHang
 * @date 2018/11/8
 */
@SpringBootApplication
@MapperScan(basePackages = "com.intest.road.mapper")
@ServletComponentScan
public class RoadApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RoadApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
