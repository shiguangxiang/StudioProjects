package com.dalianbobo.lepaandroid.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;
import com.dalianbobo.lepaandroid.base.BaseActivity;
import com.dalianbobo.lepaandroid.bean.UserLoginBean;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.uitls.SPUtils;
import com.lwh.util.CommonUtils;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends SwipeBackActivity implements View.OnClickListener {
    private final String TAG = "LoginActivity";
    private RelativeLayout mLlLogin;
    private SwipeBackLayout mSwipeBackLayout;
    //用户输入的用户名和密码
    private EditText mEdtUserAddress, mEdtUserPwd;
    //登录按钮
    private ImageView mBtnLogin;
    //忘记密码
    private TextView mTvForGetPwd, mTvLoginGG;

    private TextView mTvRegister;


    @Override
    public int setContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void initViews() {
        LEPAppLication.getInstance().addActivity(this);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mLlLogin = (RelativeLayout) findViewById(R.id.ll_login);
        mEdtUserAddress = (EditText) findViewById(R.id.edt_userAddress);
        mEdtUserPwd = (EditText) findViewById(R.id.edt_userPwd);
        mBtnLogin = (ImageView) findViewById(R.id.btn_login);
        mTvForGetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvLoginGG = (TextView) findViewById(R.id.tv_loginGG);
    }

    @Override
    public void initListeners() {
        mLlLogin.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvForGetPwd.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvLoginGG.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_login:
                //关闭键盘
                closeKeybord(mEdtUserAddress);
                closeKeybord(mEdtUserPwd);
                break;
            case R.id.btn_login://登录按钮
                if (CommonUtils.isNetworkAvailable(LoginActivity.this)) {
                    readyLogin();
                } else {
                    showToast(R.string.network_disable_error);
                }
                break;
            case R.id.tv_forget_pwd://忘记密码
                startActivity(WjMiMaActivity.class);
                break;
            case R.id.tv_register://注册
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_loginGG:
                startActivity(MainTabFragmentActivity.class);
                break;
        }
    }

    /**
     * 准备登录
     */
    private void readyLogin() {
        String userAddress = mEdtUserAddress.getText().toString().trim();
        String userPwd = mEdtUserPwd.getText().toString().trim();
        if ("".equals(userAddress)) {
            showToast("用户名不能为空");
        } else if (userAddress.length() < 4) {
            showToast("用户名不能小于4位");
        } else if ("".equals(userPwd)) {
            showToast("密码不能为空");
        } else if (userPwd.length() < 4) {
            showToast("密码不能小于4位");
        } else {
            requestLogin(userAddress, userPwd);
        }
    }

    /**
     * 正在登录方法
     *
     * @param userName
     * @param userPwd
     */
    private void requestLogin(String userName, String userPwd) {
        showScreen("正在登录...");
        final UserLoginBean userLoginBean = new UserLoginBean(userName, userPwd);
        final String api_token = apiToken("member", "login");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, RequestUrls.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dissScreen();
                Log.i(TAG,"登陆："+ s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        String access_token = dataObj.getString("access_token");
                        SPUtils.put(LoginActivity.this, "access_token", access_token);
                        SPUtils.put(LoginActivity.this, "u_id", dataObj.getString("member_id"));
                        finish();
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
                Log.i(TAG, "请求错误！");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("api_token", api_token);
                map.put("MemberForm", toJsonObjectOrJsonArray(userLoginBean));
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("User-Agent","Android");
                return headers;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(stringRequest).setTag(stringRequest);
    }
}
