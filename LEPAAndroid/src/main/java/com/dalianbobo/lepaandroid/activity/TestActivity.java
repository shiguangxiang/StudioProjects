package com.dalianbobo.lepaandroid.activity;


import android.os.Bundle;
import android.util.Log;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.SwipeBackLayout;

/**
 * Created by Administrator on 2015/12/9 on 11:38.
 * Author Clown Fish
 */
public class TestActivity extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate","TestActivity");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public int setContentView() {
        return R.layout.login_activity;
    }

    @Override
    public void initViews() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public void initListeners() {

    }
}
