package com.dalianbobo.lepaandroid.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.LoginActivity;
import com.dalianbobo.lepaandroid.activity.RegisterActivity;
import com.dalianbobo.lepaandroid.activity.SettingActivity;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.bean.UserMemberBean;
import com.dalianbobo.lepaandroid.bean.UserMemberIndexBean;
import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.activity.MessageActivity;
import com.dalianbobo.lepaandroid.uitls.SPUtils;
import com.dalianbobo.lepaandroid.weiget.SlideSwitchView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人中心页面
 * Created by Administrator on 2015/11/24 on 11:30.
 * Author Clown Fish
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "MineFragment";
    //标题
    private TextView mTvHeadTitle;
    private LinearLayout mLlMessageIndex, mLlFiveMessage, mLlNoToLogin,mLlSetting;

    private Button mBtnToLogin;
    private Button mBtnToRegister;
    private ImageView mIvHeadRigth;
    private UserMemberBean userMemberBean;
    private UserMemberIndexBean memberIndexBean;
    //头像
    private SimpleDraweeView mIvMemberAvatar;
    //昵称，个性签名，隐私锁状态
    private TextView mTvMemberName, mTvMemberSign, mTvOrder, mTvFavoites, mTvCoin;
    //左右开关
    private SlideSwitchView slideSwitchView;

    @Override
    public int setContentView() {
        return R.layout.tabfragment_five;
    }


    @Override
    public void initViews() {
        mTvHeadTitle = (TextView) mView.findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("我");
        mLlMessageIndex = (LinearLayout) mView.findViewById(R.id.ll_message_index);
        mLlFiveMessage = (LinearLayout) mView.findViewById(R.id.ll_five_message);
        mIvHeadRigth = (ImageView) mView.findViewById(R.id.head_right);
        mIvMemberAvatar = (SimpleDraweeView) mView.findViewById(R.id.p_ioc);
        mTvMemberName = (TextView) mView.findViewById(R.id.tv_member_name);
        mTvMemberSign = (TextView) mView.findViewById(R.id.tv_member_sign);
        mTvOrder = (TextView) mView.findViewById(R.id.tv_order);
        mTvFavoites = (TextView) mView.findViewById(R.id.tv_favorites);
        mTvCoin = (TextView) mView.findViewById(R.id.tv_coin);
        slideSwitchView = (SlideSwitchView) mView.findViewById(R.id.mSlideSwitchView);
        mLlNoToLogin = (LinearLayout) mView.findViewById(R.id.ll_noLgoin);
        mBtnToRegister = (Button) mView.findViewById(R.id.btn_toRegister);
        mBtnToLogin = (Button) mView.findViewById(R.id.btn_toLogin);
        mLlSetting = (LinearLayout) mView.findViewById(R.id.ll_setting);

    }

    /**
     * 获取个人中心数据
     */
    private void getHttpIndexData() {
        showScreen("正在加载...");
        final String api_token = apiToken("member", "index");
        final String access_token = (String) SPUtils.get(getActivity(), "access_token", "");
        final String member_id = (String) SPUtils.get(getActivity(), "u_id", "");
        StringRequest request = new StringRequest(Request.Method.POST, RequestUrls.INDEX_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dissScreen();
                Log.i(TAG, "个人中心数据:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        mLlNoToLogin.setVisibility(View.GONE);
                        mLlMessageIndex.setVisibility(View.VISIBLE);
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        JSONObject memberObj = dataObj.getJSONObject("member");
                        String member_id = memberObj.getString("member_id");
                        String member_name = memberObj.getString("member_name");
                        String member_sign = memberObj.getString("member_sign");
                        String member_avatar = memberObj.getString("member_avatar");
                        String member_privacy = memberObj.getString("member_privacy");
                        userMemberBean = new UserMemberBean(member_id, member_name, member_sign, member_avatar, member_privacy);
                        String order = dataObj.getString("order");
                        String favorites = dataObj.getString("favorites");
                        String coin = dataObj.getString("coin");
                        memberIndexBean = new UserMemberIndexBean(userMemberBean, order, favorites, coin);
                        mTvMemberName.setText(member_name);
                        mTvMemberSign.setText(member_sign);
                        mTvOrder.setText(order);
                        mTvFavoites.setText(favorites);
                        mTvCoin.setText(coin);
                        Log.i(TAG, member_avatar);
                        ImageRequest imageRequest = ConfigConstants.getImageRequest(mIvMemberAvatar, member_avatar);
                        DraweeController draweeController = ConfigConstants.getDraweeController(imageRequest);
                        mIvMemberAvatar.setController(draweeController);
                        if (member_privacy.equals("close")) {
                            slideSwitchView.setChecked(false);
                        } else {
                            slideSwitchView.setChecked(true);
                        }

                    }
                    longToast(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                longToast("请求错误！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("api_token", api_token);
                map.put("access_token", access_token);
                map.put("member_id", member_id);
                return map;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void initListeners() {
        mLlMessageIndex.setOnClickListener(this);
        mLlFiveMessage.setOnClickListener(this);
        mIvHeadRigth.setOnClickListener(this);
        mBtnToRegister.setOnClickListener(this);
        mBtnToLogin.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_right:
                SPUtils.clear(getActivity());
                LEPAppLication.getInstance().exit();
                break;
            case R.id.ll_message_index:
                startActivity(PersonalMessageFragment.class);//个人信息页面
                break;
            case R.id.ll_five_message:
                startActivity(MessageActivity.class);///我的消息页面
                break;

            case R.id.btn_toLogin:
                startActivity(LoginActivity.class);
                break;

            case R.id.btn_toRegister:
                startActivity(RegisterActivity.class);
                break;
            case R.id.ll_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if (!SPUtils.get(getActivity(), "u_id", "").equals("")) {
            ConfigConstants.init(getResources());
            getHttpIndexData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();// 关闭图片缓存～注意：一定要关闭，C++里new的空间
    }
}
