package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 广告滚动实体类
 * Created by Administrator on 2016/1/6 on 11:11.
 * Author Clown Fish
 */
public class OneGuangGaoBean implements Serializable{
    private String picId;
    private String picName;
    private String picUrl;
    private String picImg;
    private String picDis;

    public OneGuangGaoBean(String picId, String picName, String picUrl, String picImg, String picDis) {
        this.picId = picId;
        this.picName = picName;
        this.picUrl = picUrl;
        this.picImg = picImg;
        this.picDis = picDis;
    }

    public String getPicId() {
        return picId;
    }

    public String getPicName() {
        return picName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getPicImg() {
        return picImg;
    }

    public String getPicDis() {
        return picDis;
    }
}
