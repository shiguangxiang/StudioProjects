package com.dalianbobo.lepaandroid.constant;

/**
 * 请求所以的接口
 * Created by Administrator on 2015/12/16 on 14:27.
 * Author Clown Fish
 */
public class RequestUrls {
    /**
     * 所有接口父类
     */
    public static final String BASE_URL = "http://192.168.0.102:80/lepa/api/web/index.php?";

    public static final String BASE_IMG = "http://192.168.0.102/";
    /**
     * 获取验证码接口
     */
    public static final String MESCODE_URL = BASE_URL + "r=member/code";
    /**
     * 注册接口
     */
    public static final String REGISTER_URL = BASE_URL + "r=member/register";
    /**
     * 登录接口
     */
    public static final String LOGIN_URL = BASE_URL + "r=member/login";
    /**
     * 会员个人中心
     */
    public static final String INDEX_URL = BASE_URL + "r=member/index";
    /**
     * 支付宝支付异步通知B-09
     */
    public static final String PAYUSEALIPAY_URL = BASE_URL + "index.php/Winery/Alipay/notify_url";
    /**
     * 一元夺宝
     */
    public static final String YIYUANDUOBAO_URL = BASE_URL + "r=site/one";
    /**
     * 商城页面
     */
    public static final String SITEINDEX_URL = BASE_URL + "r=site/index";
    /**
     * 分类页面接口
     */
    public static final String GOODSCLASS_URL = BASE_URL + "r=goods-class/index";


}
