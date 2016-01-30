package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 接口验证参数
 * Created by Administrator on 2015/12/22 on 10:00.
 * Author Clown Fish
 */
public class ApiToKenBean implements Serializable {
    private String api_token;

    public ApiToKenBean(String api_token) {
        this.api_token = api_token;
    }
}
