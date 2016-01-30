package com.dalianbobo.lepaandroid.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;

/**
 * 设置界面
 * Created by Clown Fish on 2016/1/11.
 */
public class SettingActivity extends SwipeBackActivity implements View.OnClickListener {
    private SwipeBackLayout mSwipeBackLayout;
    //头部标题
    private TextView mTvHeadTitle;
    //头部右边消息提示
    private ImageView mIvHeadRigth;

    private LinearLayout mLlFriendSetting;
    @Override
    public int setContentView() {
        return R.layout.setting_activity;
    }

    @Override
    public void initViews() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mTvHeadTitle = (TextView) findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("设置");
        mIvHeadRigth = (ImageView) findViewById(R.id.head_right);
        mIvHeadRigth.setVisibility(View.GONE);

        mLlFriendSetting = (LinearLayout) findViewById(R.id.ll_friends_setting);
    }

    @Override
    public void initListeners() {
        mLlFriendSetting.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_friends_setting:
                startActivity(FriendSettingActivity.class);
                break;
        }
    }
}