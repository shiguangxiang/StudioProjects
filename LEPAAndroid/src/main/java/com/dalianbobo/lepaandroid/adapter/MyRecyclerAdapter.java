package com.dalianbobo.lepaandroid.adapter;

import android.content.Context;
import android.content.Entity;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.base.ProductListBean;

import java.util.List;

/**
 * 列表
 * Created by ClownFish on 2016/1/21.
 */
public class MyRecyclerAdapter extends RecyclerListAdapter {
    private List<ProductListBean> list;
    private Context context;
    private int layouID;

    public MyRecyclerAdapter(Context context, List<ProductListBean> list, int layouID) {
        super(context, list);
        this.list = list;
        this.context = context;
        this.layouID = layouID;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return layouID;
    }

    @Override
    protected void onBindViewHolder(RecViewHolder viewHolder, int position) {
        ProductListBean entity = list.get(position);
        viewHolder.getTextView(R.id.tv_product_name).setText(entity.getProductName());
        viewHolder.getTextView(R.id.tv_product_price).setText(entity.getProductPrice());
        viewHolder.getImageView(R.id.iv_product_image);

    }
}
