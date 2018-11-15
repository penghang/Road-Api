package com.intest.road.enums;

/**
 * @author PengHang
 * @date 2018/11/12
 */
public enum ResultEnum {
    /**
     * 未知异常
     */
    UNKNOWN_ERROR(-1, "未知错误"),
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * key错误
     */
    KEY_ERROR(1, "key错误"),
    UNKNOWN_ROAD(2, "未找到道路")
    ;
    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
