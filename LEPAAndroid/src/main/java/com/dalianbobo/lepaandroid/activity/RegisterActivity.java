package com.dalianbobo.lepaandroid.activity;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;
import com.dalianbobo.lepaandroid.bean.RegisterBean;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.uitls.StringUtil;
import com.lwh.util.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册页面
 * Created by Administrator on 2015/12/15 on 10:08.
 * Author Clown Fish
 */
public class RegisterActivity extends SwipeBackActivity implements View.OnClickListener {
    private final String TAG = "RegisterActivity";
    private SwipeBackLayout mSwipeBackLayout;
    //注册布局最外层容器
    private LinearLayout mLLRegister, mBtnLogin;
    //用户手机号码，验证码
    private EditText mEdtUserPhone, mEdtUserCode, mEdtUserPwd;
    //获取验证码,到登录按钮
    private Button mBtnGetCode, mBtnRegister;
    //定时器时间
    private TimeCount mTimeCount;

    @Override
    protected void initDatas() {

    }

    @Override
    public int setContentView() {
        return R.layout.register_activity;
    }

    @Override
    public void initViews() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mLLRegister = (LinearLayout) findViewById(R.id.id_register);
        mEdtUserPhone = (EditText) findViewById(R.id.edt_userPhone);
        mEdtUserCode = (EditText) findViewById(R.id.edt_userCode);
        mBtnGetCode = (Button) findViewById(R.id.btn_getCode);
        mBtnLogin = (LinearLayout) findViewById(R.id.btn_login_register);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEdtUserPwd = (EditText) findViewById(R.id.edt_userPwd);
    }

    @Override
    public void initListeners() {
        mLLRegister.setOnClickListener(this);
        mBtnGetCode.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_register://点击屏幕收起键盘
                closeKeybord(mEdtUserPhone);
                closeKeybord(mEdtUserCode);
                break;
            case R.id.btn_getCode://获取验证码
                mTimeCount = new TimeCount(60000, 1000);
                if (CommonUtils.isNetworkAvailable(this)) {
                    readyGetCode();
                } else {
                    showToast("网络不可用！");
                }
                break;
            case R.id.btn_login_register://跳到登录页面
                finish();
                break;
            case R.id.btn_register://注册按钮
                if (CommonUtils.isNetworkAvailable(this)) {
                    readyRegister();
                } else {
                    showToast(R.string.network_disable_error);
                }
                break;
        }
    }

    /**
     * 准备获取验证码
     */
    private void readyGetCode() {
        String userPhone = mEdtUserPhone.getText().toString().trim();
        if (CommonUtils.isNetworkAvailable(this)) {
            if (TextUtils.isEmpty(userPhone)) {
                showToast("手机号码不能为空！");
            } else if (!StringUtil.isMobileNO(userPhone)) {
                showToast("输入的手机号码不符合格式！");
            } else {
                showScreen("正在加载...");
                getCodeIng(userPhone);
            }
        } else {
            showToast("网络不可用！");
        }
    }

    /**
     * 正在获取验证码
     *
     * @param userPhone 用户电话号码
     */
    private void getCodeIng(final String userPhone) {

        showScreen("正在发送...");
        final String url = RequestUrls.MESCODE_URL;
        Log.i(TAG, "短信接口地址：" + url);
        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                dissScreen();
                mTimeCount.start();
                Log.i(TAG, "获取验证码结果:" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                    }
                    showToast(URLDecoder.decode(message));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                Log.i(TAG, "请求错误！" + volleyError.toString());
                showToast("请求错误！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String api_token = apiToken("member","code");
                map.put("api_token",api_token);
                map.put("mobile_no", userPhone);
                Log.i(TAG,api_token);
                return map;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(request).setTag(TAG);

    }

    /**
     * 准备注册
     */
    private void readyRegister() {
        //获取用户输入的值
        String userPhone = mEdtUserPhone.getText().toString().trim();
        String userCode = mEdtUserCode.getText().toString().trim();
        String userPwd = mEdtUserPwd.getText().toString().trim();
        if (TextUtils.isEmpty(userPhone)) {
            showToast("手机号码不能为空！");
        } else if (!StringUtil.isMobileNO(userPhone)) {
            showToast("输入的手机号码不符合格式！");
        } else if (TextUtils.isEmpty(userPwd)) {
            showToast("密码不能为空!");
        } else if (userPwd.length() < 6) {
            showToast("密码不能小于6为位数");
        } else if (TextUtils.isEmpty(userCode)) {
            showToast("验证码不能为空！");
        } else {
            showScreen("正在注册...");
            registerIng(userPhone, userPwd, userCode);
        }
    }

    /**
     * 正在注册
     *
     * @param userPhone 用户手机号
     * @param userPwd   用户密码
     * @param userCode  用户验证码
     */
    private void registerIng(final String userPhone, final String userCode, final String userPwd) {
        String url = RequestUrls.REGISTER_URL;
        Log.i(TAG, "注册接口地址：" + url);
        RegisterBean registerBean = new RegisterBean(userPhone, userCode, userPwd);
        final String registerInfo = toJsonObjectOrJsonArray(registerBean);
        Log.i(TAG, "注册接口传参：" + registerInfo);
        StringRequest request = new StringRequest(Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String data) {
                dissScreen();
                Log.i(TAG, "注册用户结果：" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String code = jsonObject.getString("code");
                    if ("error".equals(code)) {
                        JSONArray msgArr = jsonObject.getJSONArray("message");
                        String errorMsg = msgArr.getString(0).toString();
                        showToast(errorMsg);
                    } else if ("success".equals(code)) {
                        finish();//关闭当前Activity
                        String SuccMsg = jsonObject.getString("message");
                        showToast(SuccMsg);
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                showToast("请求错误!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String api_token = apiToken("member","register");
                map.put("api_token",api_token);
                map.put("MemberForm", registerInfo);
                Log.i(TAG, map.toString());
                return map;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(request);
    }

    /**
     * 自定义时间类
     *
     * @author Administrator
     */
    class TimeCount extends CountDownTimer {
        // 参数依次为总时长,和计时的时间间隔
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            mBtnGetCode.setClickable(false);
            mBtnGetCode.setText(millisUntilFinished / 1000 + "s重新获取");
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            mBtnGetCode.setClickable(true);
            mBtnGetCode.setText(R.string.login_btn_usercode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LEPAppLication.getInstance().getRequestQueue().cancelAll(TAG);
    }
}
