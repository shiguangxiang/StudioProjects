package com.dalianbobo.lepaandroid.bean;

import java.io.Serializable;

/**
 * 用户个人资料
 * Created by Administrator on 2015/12/23 on 14:53.
 * Author Clown Fish
 */
public class UserMemberBean implements Serializable {
    private String memberId;
    private String memberName;
    private String memberSign;
    private String memberAvatar;
    private String memberPrivacy;

    public UserMemberBean(String memberId, String memberName, String memberSign, String memberAvatar, String memberPrivacy) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberSign = memberSign;
        this.memberAvatar = memberAvatar;
        this.memberPrivacy = memberPrivacy;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSign() {
        return memberSign;
    }

    public void setMemberSign(String memberSign) {
        this.memberSign = memberSign;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }

    public String getMemberPrivacy() {
        return memberPrivacy;
    }

    public void setMemberPrivacy(String memberPrivacy) {
        this.memberPrivacy = memberPrivacy;
    }

    @Override
    public String toString() {
        return "UserMemberBean{" +
                "memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberSign='" + memberSign + '\'' +
                ", memberAvatar='" + memberAvatar + '\'' +
                ", memberPrivacy='" + memberPrivacy + '\'' +
                '}';
    }
}
