package com.zmc.springcloud.utils;

/**
 * Created by xyy on 2018/11/19.
 */
public class Json implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8949941320355528929L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}

