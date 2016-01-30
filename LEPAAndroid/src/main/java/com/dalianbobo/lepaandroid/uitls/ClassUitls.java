package com.dalianbobo.lepaandroid.uitls;

import com.google.gson.Gson;

/**
 * 所以业务类的父类
 * Created by Administrator on 2015/12/17 on 11:36.
 * Author Clown Fish
 */
public class ClassUitls {
    /**
     * 把实体类转化为JSONObject对象
     *
     * @param obj
     * @return
     */
    protected String toJsonObjectOrJsonArray(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
