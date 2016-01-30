package com.dalianbobo.lepaandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.bean.ShoppingCartBean;

import java.util.HashMap;
import java.util.List;


public class ShoppingCartAdapter extends BaseAdapter {
    // 上下文
    private Context mContext;
    // 填充数据的list
    private List<ShoppingCartBean> mList;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    // 用来导入布局
    private LayoutInflater inflater = null;
    public ShoppingCartAdapter(Context mContext, List<ShoppingCartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
        isSelected = new HashMap<>();
        // 初始化数据  
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < mList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return this.mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mList == null) {
            return null;
        } else {
            return this.mList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
//            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.shopingcar_item, null, true);
            convertView = inflater.inflate(R.layout.shopingcar_item, null);
            holder.image = (ImageView) convertView.findViewById(R.id.porImage);
            holder.proChek = (CheckBox) convertView.findViewById(R.id.cb_porChek);
            holder.proName = (TextView) convertView.findViewById(R.id.tv_porName);
            holder.proPrice = (TextView) convertView.findViewById(R.id.porPrice);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mList != null) {
            ShoppingCartBean shoppingList = this.mList.get(position);
            holder.proName.setText(shoppingList.getProName().toString());
            holder.proPrice.setText(shoppingList.getShopPrice().toString());
            holder.proChek.setChecked(getIsSelected().get(position));

        }
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        ShoppingCartAdapter.isSelected = isSelected;
    }

    /*定义item对象*/
    public static class ViewHolder {
        public ImageView image;
        public TextView proName;
        public CheckBox proChek;
        public TextView proPrice;

    }

}