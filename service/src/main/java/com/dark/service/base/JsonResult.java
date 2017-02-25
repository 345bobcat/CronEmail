package com.dark.service.base;

import java.io.Serializable;

/**
 * json 结果返回对象
 */
public class JsonResult implements Serializable {
    private boolean success;
    private String msg;
    private Object data;

    public JsonResult() {
        this(true, "");
    }

    public JsonResult(Object data) {
        this(true, "");
        this.data = data;
    }

    public JsonResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public JsonResult(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
