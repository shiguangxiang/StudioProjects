package com.dalianbobo.lepaandroid.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.SwipeBackActivity;
import com.dalianbobo.lepaandroid.base.BaseFragment;

/**
 * 消息页面
 * Created by Administrator on 2015/11/30 on 10:59.
 * Author Clown Fish
 */
public class MessageActivity extends SwipeBackActivity implements View.OnClickListener {
    private TextView mTvHeadTitle;
    private LinearLayout mLlHeadBack;

    @Override
    protected void initDatas() {

    }

    @Override
    public int setContentView() {
        return R.layout.message_fragment_five;
    }

    @Override
    public void initViews() {
        mLlHeadBack = (LinearLayout) findViewById(R.id.ll_back);
        mLlHeadBack.setVisibility(View.VISIBLE);
        mTvHeadTitle = (TextView) findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("消息");
    }

    @Override
    public void initListeners() {
        mLlHeadBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
