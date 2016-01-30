package com.dalianbobo.lepaandroid.bean;

/**
 * 用户登录实体对象
 * Created by Administrator on 2015/12/17 on 11:33.
 * Author Clown Fish
 */
public class UserLoginBean {
    private String member_mobile;
    private String member_passwd;

    public UserLoginBean(String userName, String userPwd) {
        this.member_mobile = userName;
        this.member_passwd = userPwd;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getMember_passwd() {
        return member_passwd;
    }

    public void setMember_passwd(String member_passwd) {
        this.member_passwd = member_passwd;
    }
}
