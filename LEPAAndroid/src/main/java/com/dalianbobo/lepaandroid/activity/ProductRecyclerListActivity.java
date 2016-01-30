package com.dalianbobo.lepaandroid.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.fragment.fragmentproductlist.ProductGridFragment;
import com.dalianbobo.lepaandroid.fragment.fragmentproductlist.ProductListFragment;

/**
 * 产品列表页
 * Created by ClownFish on 2016/1/21.
 */
public class ProductRecyclerListActivity extends SwipeBackActivity implements View.OnClickListener {
    private final String TAG = "ProductRecyclerListActivity";
    private Boolean isListView = true;
    private ImageView mIvHeadRight;
    private ProductListFragment listFragment;
    private ProductGridFragment gridFragment;
    private boolean mTabOne, mTabTwo, mTabThree, mTabFour;
    private LinearLayout mLLOne, mLLTwo, mLLThree, mLLFour;

    @Override
    public int setContentView() {
        return R.layout.product_list_activity;
    }

    @Override
    public void initViews() {
        mIvHeadRight = (ImageView) findViewById(R.id.head_right);
        mLLOne = (LinearLayout) findViewById(R.id.ll_tab_one);
        mLLTwo = (LinearLayout) findViewById(R.id.ll_tab_two);
        mLLThree = (LinearLayout) findViewById(R.id.ll_tab_three);
        mLLFour = (LinearLayout) findViewById(R.id.ll_tab_four);

        setFragmentIsList(true);
    }

    /**
     * 设置Fragment产品页面是否为list
     *
     * @param isListView
     */
    private void setFragmentIsList(Boolean isListView) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideFragments(ft);
        if (isListView == true) {
            if (listFragment == null) {
                listFragment = new ProductListFragment();
                ft.add(R.id.fl_product, listFragment);
            } else {
                ft.show(listFragment);
            }
        } else {
            if (gridFragment == null) {
                gridFragment = new ProductGridFragment();
                ft.add(R.id.fl_product, gridFragment);
            } else {
                ft.show(gridFragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏Fragment
     *
     * @param ft
     */
    private void hideFragments(FragmentTransaction ft) {
        if (listFragment != null) {
            ft.hide(listFragment);
        }
        if (gridFragment != null) {
            ft.hide(gridFragment);
        }
    }

    @Override
    public void initListeners() {
        mLLOne.setOnClickListener(this);
        mLLTwo.setOnClickListener(this);
        mLLThree.setOnClickListener(this);
        mLLFour.setOnClickListener(this);
        mIvHeadRight.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_right:
                if (!isListView) {
                    setFragmentIsList(true);
                    Log.i(TAG, "list");
                    isListView = true;
                } else {
                    setFragmentIsList(false);
                    Log.i(TAG, "grid");
                    isListView = false;
                }

                break;

            case R.id.ll_tab_one:
                mLLOne.setBackgroundColor(getResources().getColor(R.color.gray));
                mLLTwo.setBackgroundColor(getResources().getColor(R.color.white));
                mLLThree.setBackgroundColor(getResources().getColor(R.color.white));
                mLLFour.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case R.id.ll_tab_two:
                mLLTwo.setBackgroundColor(getResources().getColor(R.color.gray));
                mLLOne.setBackgroundColor(getResources().getColor(R.color.white));
                mLLThree.setBackgroundColor(getResources().getColor(R.color.white));
                mLLFour.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case R.id.ll_tab_three:
                mLLThree.setBackgroundColor(getResources().getColor(R.color.gray));
                mLLOne.setBackgroundColor(getResources().getColor(R.color.white));
                mLLTwo.setBackgroundColor(getResources().getColor(R.color.white));
                mLLFour.setBackgroundColor(getResources().getColor(R.color.white));
                break;

            case R.id.ll_tab_four:
                mLLFour.setBackgroundColor(getResources().getColor(R.color.gray));
                mLLOne.setBackgroundColor(getResources().getColor(R.color.white));
                mLLTwo.setBackgroundColor(getResources().getColor(R.color.white));
                mLLThree.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }
    }

}
