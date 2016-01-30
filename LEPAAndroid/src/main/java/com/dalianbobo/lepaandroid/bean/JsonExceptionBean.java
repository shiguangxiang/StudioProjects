package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * json响应实体
 * Created by Administrator on 2015/12/18 on 15:08.
 * Author Clown Fish
 */
public class JsonExceptionBean implements Serializable {
    private String code;
    private String message;

    public JsonExceptionBean(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
