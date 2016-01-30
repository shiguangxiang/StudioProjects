package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 注册实体类对象
 * Created by Administrator on 2015/12/16 on 17:06.
 * Author Clown Fish
 */
public class RegisterBean implements Serializable {
    private String member_mobile;
    private String member_passwd;
    private String mobile_no ;

    public RegisterBean(String member_mobile, String member_passwd, String mobile_no) {
        this.member_mobile = member_mobile;
        this.member_passwd = member_passwd;
        this.mobile_no = mobile_no;
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

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
