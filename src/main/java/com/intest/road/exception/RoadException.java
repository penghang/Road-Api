package com.intest.road.exception;

import com.intest.road.enums.ResultEnum;

/**
 * @author PengHang
 * @date 2018/11/12
 */
public class RoadException extends RuntimeException {
    private Integer code;
    public  RoadException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }
}
