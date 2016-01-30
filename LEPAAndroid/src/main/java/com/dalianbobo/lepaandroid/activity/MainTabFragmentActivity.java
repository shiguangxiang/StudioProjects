package com.dalianbobo.lepaandroid.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.fragment.LoginFragment;
import com.dalianbobo.lepaandroid.fragment.MineFragment;
import com.dalianbobo.lepaandroid.fragment.ShoppingCartFragment;
import com.dalianbobo.lepaandroid.fragment.MallFragment;
import com.dalianbobo.lepaandroid.fragment.CommunityFragment;
import com.dalianbobo.lepaandroid.fragment.ClassifyFragment;


/**
 * 选项卡主页面
 * Created by Administrator on 2015/11/24 on 11:04.
 * Author Clown Fish
 */
public class MainTabFragmentActivity extends FragmentActivity implements View.OnClickListener {
    //分别是：商城页面，分类页面，社区页面，购物车页面，我的页面
    private Fragment mTabFragmentOne, mTabFragmentTwo, mTabFragmentThree, mTabFragmentFour, mTabFragmentFive,mTabFragmentLogin;

    private LinearLayout mLlOne, mLlTwo, mLlThree, mLlFour, mLlFive;
    //分别是：商城的ico,分类的ico,社区的ico，购物车的ico,我的ico
    private ImageView mIvIco1, mIvIco2, mIvIco3, mIvIco4, mIvIco5;

    //分别是：商城的文字,分类的文字,社区的文字，购物车的文字,我的文字
    private TextView mTvText1, mTvText2, mTvText3, mTvText4, mTvText5;

    private final int FragmentOne = 0;
    private final int FragmentTwo = 1;
    private final int FragmentThree = 2;
    private final int FragmentFour = 3;
    private final int FragmentFive = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_tab_fragmentactivity);
        LEPAppLication.getInstance().addActivity(this);
        initViews();
        initListener();
        setSelectTabFragment(FragmentOne);
    }

    /**
     * 找到控件
     */
    private void initViews() {
        mLlOne = (LinearLayout) findViewById(R.id.ll_1);
        mLlTwo = (LinearLayout) findViewById(R.id.ll_2);
        mLlThree = (LinearLayout) findViewById(R.id.ll_3);
        mLlFour = (LinearLayout) findViewById(R.id.ll_4);
        mLlFive = (LinearLayout) findViewById(R.id.ll_5);


        mTvText1 = (TextView) findViewById(R.id.tv_1);
        mTvText2 = (TextView) findViewById(R.id.tv_2);
        mTvText3 = (TextView) findViewById(R.id.tv_3);
        mTvText4 = (TextView) findViewById(R.id.tv_4);
        mTvText5 = (TextView) findViewById(R.id.tv_5);

        mIvIco1 = (ImageView) findViewById(R.id.iv_1);
        mIvIco2 = (ImageView) findViewById(R.id.iv_2);
        mIvIco3 = (ImageView) findViewById(R.id.iv_3);
        mIvIco4 = (ImageView) findViewById(R.id.iv_4);
        mIvIco5 = (ImageView) findViewById(R.id.iv_5);
    }

    /**
     * 给控件设置点击事件
     */
    private void initListener() {
        mLlOne.setOnClickListener(this);
        mLlTwo.setOnClickListener(this);
        mLlThree.setOnClickListener(this);
        mLlFour.setOnClickListener(this);
        mLlFive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:
                setSelectTabFragment(FragmentOne);
                break;
            case R.id.ll_2:
                setSelectTabFragment(FragmentTwo);
                break;
            case R.id.ll_3:
                setSelectTabFragment(FragmentThree);
                break;
            case R.id.ll_4:
                setSelectTabFragment(FragmentFour);
                break;
            case R.id.ll_5:
                setSelectTabFragment(FragmentFive);
                break;
        }
    }

    /**
     * 设置选择的tab选项卡页面
     *
     * @param i
     */
    private void setSelectTabFragment(int i) {
        resetTvColor();//重置文字的颜色
        resetIvIco();//重置图片的颜色
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (i) {
            case FragmentOne:
                mIvIco1.setBackgroundResource(R.mipmap.tab_ico1);
                mTvText1.setTextColor(Color.parseColor("#0FC5AC"));
                if (mTabFragmentOne == null) {
                    mTabFragmentOne = new MallFragment();
                }
                if (!mTabFragmentOne.isAdded()) {
                    transaction.add(R.id.tab_fragment_content, mTabFragmentOne);
                }
                transaction.show(mTabFragmentOne);

                break;
            case FragmentTwo:
                mIvIco2.setBackgroundResource(R.mipmap.tab_ico2);
                mTvText2.setTextColor(Color.parseColor("#0FC5AC"));
                if (mTabFragmentTwo == null) {
                    mTabFragmentTwo = new ClassifyFragment();
                }
                if (!mTabFragmentTwo.isAdded()) {
                    transaction.add(R.id.tab_fragment_content, mTabFragmentTwo);
                }
                transaction.show(mTabFragmentTwo);

                break;
            case FragmentThree:
                mIvIco3.setBackgroundResource(R.mipmap.tab_ico3);
                mTvText3.setTextColor(Color.parseColor("#0FC5AC"));
                if (mTabFragmentThree == null) {
                    mTabFragmentThree = new CommunityFragment();
                }
                if (!mTabFragmentThree.isAdded()) {
                    transaction.add(R.id.tab_fragment_content, mTabFragmentThree);
                }
                transaction.show(mTabFragmentThree);
                break;
            case FragmentFour:
                mIvIco4.setBackgroundResource(R.mipmap.tab_ico4);
                mTvText4.setTextColor(Color.parseColor("#0FC5AC"));
                if (mTabFragmentFour == null) {
                    mTabFragmentFour = new ShoppingCartFragment();
                }
                if (!mTabFragmentFour.isAdded()) {
                    transaction.add(R.id.tab_fragment_content, mTabFragmentFour);
                }
                transaction.show(mTabFragmentFour);
                break;
            case FragmentFive:
                mIvIco5.setBackgroundResource(R.mipmap.tab_ico5);
                mTvText5.setTextColor(Color.parseColor("#0FC5AC"));
                //如果登录状态登录true就显现我的页面
                Toast.makeText(MainTabFragmentActivity.this,LEPAppLication.isLogin+"",Toast.LENGTH_LONG).show();
                if (LEPAppLication.isLogin == true){
                    if (mTabFragmentFive == null) {
                        mTabFragmentFive = new MineFragment();
                    }
                    if (!mTabFragmentFive.isAdded()) {
                        transaction.add(R.id.tab_fragment_content, mTabFragmentFive);
                    }
                    transaction.show(mTabFragmentFive);
                }else {//否则就进入登录页面
                    if (mTabFragmentLogin == null){
                        mTabFragmentLogin = new LoginFragment();
                    }
                    if (!mTabFragmentLogin.isAdded()){
                        transaction.add(R.id.tab_fragment_content,mTabFragmentLogin);
                    }
                    transaction.show(mTabFragmentLogin);
                }
                break;
        }
        transaction.commit();
    }


    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mTabFragmentOne != null) {
            transaction.hide(mTabFragmentOne);
        }
        if (mTabFragmentTwo != null) {
            transaction.hide(mTabFragmentTwo);
        }
        if (mTabFragmentThree != null) {
            transaction.hide(mTabFragmentThree);
        }
        if (mTabFragmentFour != null) {
            transaction.hide(mTabFragmentFour);
        }
        if (mTabFragmentFive != null) {
            transaction.hide(mTabFragmentFive);
        }
        if (mTabFragmentLogin != null) {
            transaction.hide(mTabFragmentLogin);
        }
    }

    /**
     * 重置文字的颜色
     */
    private void resetTvColor() {
        mTvText1.setTextColor(Color.parseColor("#9D9D9D"));
        mTvText2.setTextColor(Color.parseColor("#9D9D9D"));
        mTvText3.setTextColor(Color.parseColor("#9D9D9D"));
        mTvText4.setTextColor(Color.parseColor("#9D9D9D"));
        mTvText5.setTextColor(Color.parseColor("#9D9D9D"));
    }

    /**
     * 重置图片的颜色
     */
    private void resetIvIco() {
        mIvIco1.setBackgroundResource(R.mipmap.tab_ico11);
        mIvIco2.setBackgroundResource(R.mipmap.tab_ico22);
        mIvIco3.setBackgroundResource(R.mipmap.tab_ico33);
        mIvIco4.setBackgroundResource(R.mipmap.tab_ico44);
        mIvIco5.setBackgroundResource(R.mipmap.tab_ico55);
    }

    /**
     * 按返回键处理事件
     */
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            //对话框提示
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("提示");
            builder.setMessage("您需要退出乐啪客户端吗？");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    LEPAppLication.getInstance().exit();
                }
            });
            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            super.onBackPressed();
        }
    }

}
