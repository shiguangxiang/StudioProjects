package com.dalianbobo.lepaandroid.bean;

/**
 * 第六块 激情加倍
 * Created by ClownFish on 2016/1/19.
 */
public class DoubleBean {
    private String goodsId;
    private String goodsPic;
    private String goodsUrl;
    private String goodsDisModle;

    public DoubleBean(String goodsId, String goodsPic, String goodsUrl, String goodsDisModle) {
        this.goodsId = goodsId;
        this.goodsPic = goodsPic;
        this.goodsUrl = goodsUrl;
        this.goodsDisModle = goodsDisModle;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsPic() {
        return goodsPic;
    }

    public void setGoodsPic(String goodsPic) {
        this.goodsPic = goodsPic;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }

    public String getGoodsDisModle() {
        return goodsDisModle;
    }

    public void setGoodsDisModle(String goodsDisModle) {
        this.goodsDisModle = goodsDisModle;
    }
}
