package com.dalianbobo.lepaandroid.fragment;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.MainTabFragmentActivity;
import com.dalianbobo.lepaandroid.activity.RegisterActivity;
import com.dalianbobo.lepaandroid.activity.TestActivity;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.bean.UserLoginBean;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.uitls.SPUtils;
import com.lwh.util.CommonUtils;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录界面
 * Created by Administrator on 2015/11/24 on 9:20.
 * Author Clown Fish
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "LoginActivity";
    private RelativeLayout mLlLogin;
    //用户输入的用户名和密码
    private EditText mEdtUserAddress, mEdtUserPwd;
    //登录按钮
    private ImageView mBtnLogin;
    //忘记密码
    private TextView mTvForGetPwd;

    private TextView mTvRegister;

    @Override
    public int setContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void initViews() {
        mLlLogin = (RelativeLayout) mView.findViewById(R.id.ll_login);
        mEdtUserAddress = (EditText) mView.findViewById(R.id.edt_userAddress);
        mEdtUserPwd = (EditText) mView.findViewById(R.id.edt_userPwd);
        mBtnLogin = (ImageView) mView.findViewById(R.id.btn_login);
        mTvForGetPwd = (TextView) mView.findViewById(R.id.tv_forget_pwd);
        mTvRegister = (TextView) mView.findViewById(R.id.tv_register);
    }

    @Override
    public void initListeners() {
        mLlLogin.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvForGetPwd.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void initDatas() {

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
                if (CommonUtils.isNetworkAvailable(getActivity())) {
                    readyLogin();
                } else {
                    showToast(R.string.network_disable_error);
                }
                break;
            case R.id.tv_forget_pwd://忘记密码
                startActivity(TestActivity.class);
                break;
            case R.id.tv_register://注册
                startActivity(RegisterActivity.class);
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
            longToast("用户名不能为空");
        } else if (userAddress.length() < 4) {
            longToast("用户名不能小于4位");
        } else if ("".equals(userPwd)) {
            longToast("密码不能为空");
        } else if (userPwd.length() < 4) {
            longToast("密码不能小于4位");
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
        UserLoginBean userLoginBean = new UserLoginBean(userName, userPwd);
        AjaxParams params = new AjaxParams();
        String api_token = apiToken("member","login");
        params.put("api_token",api_token);
        params.put("MemberForm", toJsonObjectOrJsonArray(userLoginBean));
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onSuccess(String json) {
                super.onSuccess(json);
                dissScreen();
                String jsonData = json.toString();
                Log.i(TAG, jsonData);
                try {
                    JSONObject jsonObject = new JSONObject(jsonData);
                    String code = jsonObject.getString("code");
                    if ("success".equals(code)) {
                        String message = jsonObject.getString("message");
                        startActivity(MainTabFragmentActivity.class);
                        SPUtils.put(getActivity(), "toLogin", true);
                        SPUtils.put(getActivity(), "isLogin", true);
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        String access_token = dataObj.getString("access_token");
                        SPUtils.put(getActivity(),"access_token",access_token);
                        SPUtils.put(getActivity(),"u_id",dataObj.getString("member_id"));
                        longToast(message);
                    }else if ("error".equals(code)){
                        JSONArray msgArr = jsonObject.getJSONArray("message");
                        longToast(msgArr.get(0).toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
                dissScreen();
                Log.i(TAG, "请求错误！");
            }
        };
        HttpPost(RequestUrls.LOGIN_URL, params, callBack);
    }

}
