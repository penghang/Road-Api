package com.intest.road.handle;

import com.intest.road.enums.ResultEnum;
import com.intest.road.exception.RoadException;
import com.intest.road.pojo.Result;
import com.intest.road.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author PengHang
 * @date 2018/11/12
 */
@ControllerAdvice
public class ExceptionHandle {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof RoadException) {
            RoadException roadException = (RoadException) e;
            return ResultUtil.error(roadException.getCode(), roadException.getMessage());
        } else {
            logger.error("未知错误", e);
            return ResultUtil.error(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getMsg());
        }
    }
}
