package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 说明：购物车的相关信息
 * Created by Administrator on 2015/12/2 on 11:25.
 * Author Clown Fish
 */
public class ShoppingCartBean implements Serializable {
    private String proImg;
    private String ProName;
    private String shopPrice;

    public ShoppingCartBean(String proImg, String proName, String shopPrice) {
        this.proImg = proImg;
        ProName = proName;
        this.shopPrice = shopPrice;
    }

    public String getProImg() {
        return proImg;
    }

    public void setProImg(String proImg) {
        this.proImg = proImg;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
}