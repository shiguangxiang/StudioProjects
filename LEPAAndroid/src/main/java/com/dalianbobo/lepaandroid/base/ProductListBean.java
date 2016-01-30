package com.dalianbobo.lepaandroid.base;

import java.io.Serializable;

/**
 * Created by ClownFish on 2016/1/21.
 */
public class ProductListBean implements Serializable {

    private String productId;
    private String productName;
    private String productPrice;
    private String getProducImg;
    private String getProducNum;

    public ProductListBean(String productId, String productName, String productPrice, String getProducImg, String getProducNum) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.getProducImg = getProducImg;
        this.getProducNum = getProducNum;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getGetProducImg() {
        return getProducImg;
    }

    public void setGetProducImg(String getProducImg) {
        this.getProducImg = getProducImg;
    }

    public String getGetProducNum() {
        return getProducNum;
    }

    public void setGetProducNum(String getProducNum) {
        this.getProducNum = getProducNum;
    }
}
