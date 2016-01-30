package com.dalianbobo.lepaandroid.activity;

import android.view.View;
import android.widget.ImageView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;

/**
 * 忘记密码页面
 * Created by Administrator on 2016/1/6 on 9:54.
 * Author Clown Fish
 */
public class WjMiMaActivity extends SwipeBackActivity {
    private ImageView mIvHeadBack;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void initDatas() {

    }

    @Override
    public int setContentView() {
        return R.layout.wangjimima_activity;
    }

    @Override
    public void initViews() {
        mIvHeadBack = (ImageView) findViewById(R.id.head_right);
        mIvHeadBack.setVisibility(View.GONE);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void initListeners() {

    }
}
