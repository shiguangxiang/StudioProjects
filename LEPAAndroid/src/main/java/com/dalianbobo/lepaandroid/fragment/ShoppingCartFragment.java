package com.dalianbobo.lepaandroid.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.MainTabFragmentActivity;
import com.dalianbobo.lepaandroid.adapter.ShoppingCartAdapter;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.bean.ShoppingCartBean;
import com.dalianbobo.lepaandroid.adapter.ShoppingCartAdapter.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车页面
 * Created by Administrator on 2015/11/24 on 11:29.
 * Author Clown Fish
 */
public class ShoppingCartFragment extends BaseFragment  {
    //标题
    private TextView mTvHeadTitle,mTvShoppingCount;
    //去逛逛按钮
    private ImageView mIvToBuy;
    //自定义适配器
    private ShoppingCartAdapter adapter;
    //数据的集合
    private List<ShoppingCartBean> cartBeans;
    //全选框
    private CheckBox mCheckBox;
    private ListView mLv;
    private int checkNum; // 记录选中的条目数量

    @Override
    public int setContentView() {
        getInitData();
        return R.layout.tabfragment_four;
    }

    /**
     * 获取初始化数据
     */
    private void getInitData() {
        cartBeans = new ArrayList<>();
        ShoppingCartBean cartBean = new ShoppingCartBean("","撒可见度立刻撒旦","￥158");
        ShoppingCartBean cartBean1 = new ShoppingCartBean("大数据的","撒可见度立的萨满的卢卡斯刻撒旦","￥178");
        ShoppingCartBean cartBean2 = new ShoppingCartBean("大数hh据的","撒可见度是非得失立刻撒旦","￥128");
        cartBeans.add(cartBean);
        cartBeans.add(cartBean1);
        cartBeans.add(cartBean2);


    }

    @Override
    public void initViews() {
        mTvHeadTitle = (TextView) mView.findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("购物车");
        mIvToBuy = (ImageView)mView.findViewById(R.id.iv_four_toBuy);
        mLv = (ListView) mView.findViewById(R.id.lv_shoppingCar);
        adapter = new ShoppingCartAdapter(getActivity(),cartBeans);
        mLv.setAdapter(adapter);
        mCheckBox = (CheckBox) mView.findViewById(R.id.cb_shopping);
        mTvShoppingCount = (TextView) mView.findViewById(R.id.tv_shopingCount);

        mLv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                longToast("点击 ");
//                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//                ViewHolder holder = (ViewHolder) arg1.getTag();
//                // 改变CheckBox的状态
//                holder.proChek.toggle();
//                // 将CheckBox的选中状况记录下来
//                ShoppingCartAdapter.getIsSelected().put(arg2, holder.proChek.isChecked());
//                // 调整选定条目
//                if (holder.proChek.isChecked() == true) {
//                    checkNum++;
//                } else {
//                    checkNum--;
//                }
//                // 用TextView显示
////                longToast("点击 ");
//                mTvShoppingCount.setText("已选中" + checkNum + "项");
            }
        });
    }

    @Override
    public void initListeners() {
        mIvToBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainTabFragmentActivity.class);
                LEPAppLication.getInstance().finishActivity(MainTabFragmentActivity.class);
            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    longToast("全选模式");
                    for (int i = 0; i < cartBeans.size(); i++) {
                        ShoppingCartAdapter.getIsSelected().put(i, true);
                    }
                    // 数量设为list的长度
                    checkNum = cartBeans.size();
                    // 刷新listview和TextView的显示
                    dataChanged();
                } else {
                    longToast("非全选模式");
                    for (int i = 0; i < cartBeans.size(); i++) {
                        if (ShoppingCartAdapter.getIsSelected().get(i)) {
                            ShoppingCartAdapter.getIsSelected().put(i, false);
                            checkNum--;
                        }
                    }
                    // 刷新listview和TextView的显示
                    dataChanged();
                }
            }
        });

    }

    @Override
    public void initDatas() {

    }

    // 刷新listview和TextView的显示
    private void dataChanged() {
        // 通知listView刷新
        adapter.notifyDataSetChanged();
        // TextView显示最新的选中数目
        mTvShoppingCount.setText("结算(" + checkNum + ")");
    };

}
