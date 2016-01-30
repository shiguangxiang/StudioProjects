package com.dalianbobo.lepaandroid.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.adapter.CommonAdapter;
import com.dalianbobo.lepaandroid.adapter.ViewHolder;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;
import com.dalianbobo.lepaandroid.base.PayResult;
import com.dalianbobo.lepaandroid.bean.YiYuanDuoBaoBean;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.uitls.SignUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * 正在进行页面
 * Created by Administrator on 2015/11/27 on 9:20.
 * Author Clown Fish
 */
public class IndianaActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    private final String TAG = "IndianaActivity";
    // 商户PID
    public static final String PARTNER = "2088021216671284";
    // 商户收款账号
    public static final String SELLER = "wuxwda@163.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMIIQQrM+/YZuPuy\n" +
            "aPUKw7Lk0xuNSeT/lcUvPg3DFsESanhxxrX+ycGTnHuZDZQqGulAms8CfnNQiFCw\n" +
            "mfQs+IoAOAmfyVe85MPklIzCMJz9gjVSGTxcZpDXiE1GhKdoZNAU0UVCij67AGAh\n" +
            "NGqr0gzcrXvBRKyavYH7KwEhmWYXAgMBAAECgYB5cWccVn5hY2onh1wv+wPP5W7I\n" +
            "cN9OSDY6i74gKPoF7/vZ1QSVhppK4ZPwvDQGh2/+jCQY6yuwCzi2oEJL1RXdWxNt\n" +
            "TgmZTNDqRYHJ19p82P232sOFIS7KhnX6ZgrsrG4KLHNAc2jl4Xx3GhQ+m8lb78HO\n" +
            "7wgWQn5/g59I7UVx4QJBAOCp15pAk5fr+NPU3gjmDTPgWFxi/VH585Nf51BUeexA\n" +
            "GjUYedy35AYbQblCmYp6A/y0RfbFEu3NX2APaRDwTocCQQDdGKZbfgX3PIONpLtN\n" +
            "OeGd5EmDT9T6AssnohyBPfwMNNiO8rMA9WO49KrudHZBNFZ7K5boiuNyZ7TmkbBw\n" +
            "Yf/xAkEA3jdAWYOcOT2fXi+D+vtJfdFetOr4s3fu9At9KZiVDBgPlAsEpkuBkISv\n" +
            "SoZUhoLPpMnPFhmx8mMb/lDf1HBMywJBALN8dlih9w/dpWJQj+lCiuEV2YPrRBdu\n" +
            "OyRCm2dkPYBfSkrJxf2KOFmG7ljEXNfUODynh0virIBMpnPjY9voAtECQDEPaFiU\n" +
            "nIMggcrMxDwPAA62314x879JHJcy61CnsyHGgN07JkroK+2tsUtuNIgwDHYWlV9P\n" +
            "uWFUchV8pCR9M7g=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;//支付的标记

    private static final int SDK_CHECK_FLAG = 2;//检测账户的标记

    private BaseAdapter adapter;
    private ListView mLv;
    private TextView mTvHeadTitle;
    private List<YiYuanDuoBaoBean> strings = new ArrayList<>();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    Log.i("pay", "支付宝返回的:" + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.i("pay", "支付成功!");
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showToast("支付结果确认中!");
                        } else {
                            showToast("支付失败!");
                        }
                    }
                    break;
                case SDK_CHECK_FLAG:
                    showToast("检查结果为：" + msg.obj);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public int setContentView() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        return R.layout.yiyuanduobao_fragmentone;
    }

    @Override
    public void initViews() {

        mTvHeadTitle = (TextView) findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("一元夺宝");
        getData();

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initDatas() {

    }

    /**
     * 请求支付宝接口
     */
    private void payGoods(String goodsName) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(IndianaActivity.this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    IndianaActivity.this.finish();
                                }
                            }).show();
            return;
        }
        //生成订单表
        String orderInfo = getOrderInfo(goodsName, "最好用的哦！", "0.1");
        Log.i("pay", "支付订单：" + orderInfo);
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLDecoder.decode(sign, "UTF-8");
            Log.i("pay", "签名:" + sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(IndianaActivity.this);
                //调用支付接口，获取支付结果
                String result = payTask.pay(payInfo);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 获取数据
     *
     * @return 返回商品集合
     */
    private void getData() {
        final String api_token = apiToken("site", "one");
        StringRequest request = new StringRequest(Request.Method.POST, RequestUrls.YIYUANDUOBAO_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i(TAG, "一元夺宝:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String message = jsonObject.getString("message");
                    String code = jsonObject.getString("code");
                    if ("success".equals(code)) {
                        JSONArray dataArr = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArr.length(); i++) {
                            JSONObject object = dataArr.getJSONObject(i);
                            String good_id = "1";
                            String goods_name = object.getString("goods_name");
                            String goods_price = object.getString("goods_price");
                            String activity_discount = object.getString("activity_discount");
                            String activity_num = object.getString("activity_num");
                            String activity_price = object.getString("activity_price");
                            String goods_image = object.getString("goods_image");
                            YiYuanDuoBaoBean yiYuanDuoBaoBean = new YiYuanDuoBaoBean(good_id, goods_name, goods_image, activity_num, goods_price, activity_discount, activity_price);
                            strings.add(yiYuanDuoBaoBean);
                            showToast(strings.size() + "");
                        }

                        setGoodsAdapter(strings);
                    }

                    showToast(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                showToast("请求错误！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("api_token", api_token);
                map.put("page_size ","1");
                return map;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(request);
    }

    private void setGoodsAdapter(List<YiYuanDuoBaoBean> stringList) {
//        longToast(stringList.get(0).getGoodsName());
        adapter = new CommonAdapter<YiYuanDuoBaoBean>(IndianaActivity.this, stringList, R.layout.yiyuanduobao_fragmentone_item) {
            @Override
            public void convert(ViewHolder helper, YiYuanDuoBaoBean item) {
                TextView goodsName = helper.getView(R.id.tv_yibao_name);
                goodsName.setText(item.getGoodsName());
            }
        };

        mLv.setAdapter(adapter);
    }

////    private class InitGetData extends AsyncTask<Void, Void, List<String>> {
////
////        @Override
////        protected List<String> doInBackground(Void... params) {
////            try {
////                Thread.sleep(2000);
////
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            return "";
////        }
////
////        @Override
////        protected void onPostExecute(List<String> stringList) {
////            adapter.notifyDataSetChanged();
////            mLv.onRefreshComplete();
////            super.onPostExecute(stringList);
////        }
////
////
////    }
////
////
////    private class InitGetDataDown extends AsyncTask<Void, Void, List<String>> {
////
////        @Override
////        protected List<String> doInBackground(Void... params) {
////            try {
////                Thread.sleep(2000);
////                strings.add("shiguangxiang");
////                strings.add("mamammama");
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            return strings;
////        }
////
////        @Override
////        protected void onPostExecute(List<String> stringList) {
////            setAdapter(stringList);
////            adapter.notifyDataSetChanged();
////            mLv.onRefreshComplete();
////            super.onPostExecute(stringList);
////        }
//    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price) {

        // 商品详情
        //orderInfo += "&body=" + "\"" + body + "\"";
        String orderInfo = "body=" + "\"" + body + "\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
        //orderInfo += "&out_trade_no=" + "\"" + number + "\"";


        // 签约合作者身份ID
        orderInfo += "&partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";
        //orderInfo += "&subject=" + "\"" + "mc" + "\"";


        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + RequestUrls.PAYUSEALIPAY_URL
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";


        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
