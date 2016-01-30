package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 一元夺宝实体类
 * Created by Administrator on 2015/12/29 on 16:44.
 * Author Clown Fish
 */
public class YiYuanDuoBaoBean implements Serializable {
    private String goodsId;
    private String goodsName;
    private String goodsImage;
    private String goodsNum;
    private String goodsPrice;
    private String goodsDiscount;
    private String activityPrice;

    /**
     * 一元夺宝商品列表
     * @param goodsId 商品 id
     * @param goodsName 商品名称
     * @param goodsImage 商品图片
     * @param goodsNum 商品数量
     * @param goodsPrice 商品价格
     * @param goodsDiscount 商品折扣
     * @param goodsDiscount 商品折扣
     */
    public YiYuanDuoBaoBean(String goodsId, String goodsName, String goodsImage, String goodsNum, String goodsPrice, String goodsDiscount,String activityPrice) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsImage = goodsImage;
        this.goodsNum = goodsNum;
        this.goodsPrice = goodsPrice;
        this.goodsDiscount = goodsDiscount;
        this.activityPrice = activityPrice;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsDiscount() {
        return goodsDiscount;
    }

    public void setGoodsDiscount(String goodsDiscount) {
        this.goodsDiscount = goodsDiscount;
    }

    public String getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(String activityPrice) {
        this.activityPrice = activityPrice;
    }
}
