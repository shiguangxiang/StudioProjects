package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 个人中心页面（我)
 * Created by Administrator on 2015/12/23 on 15:05.
 * Author Clown Fish
 */
public class UserMemberIndexBean implements Serializable {
    private UserMemberBean memberBean;
    private String order;
    private String favorites;
    private String coin;

    public UserMemberIndexBean(UserMemberBean memberBean, String order, String favorites, String coin) {
        this.memberBean = memberBean;
        this.order = order;
        this.favorites = favorites;
        this.coin = coin;
    }

    public UserMemberBean getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(UserMemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "UserMemberIndexBean{" +
                "memberBean=" + memberBean +
                ", order='" + order + '\'' +
                ", favorites='" + favorites + '\'' +
                ", coin='" + coin + '\'' +
                '}';
    }
}
