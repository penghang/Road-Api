package com.intest.road.utils;

import com.intest.road.enums.ResultEnum;
import com.intest.road.pojo.Result;

/**
 * @author PengHang
 * @date 2018/11/8
 */
public class ResultUtil {
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }
    public static Result success() {
        return success(null);
    }
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
