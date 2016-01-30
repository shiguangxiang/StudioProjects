package com.dalianbobo.lepaandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 通用的Adapter
 * @author Administrator
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
	
	protected LayoutInflater mInflater;//LayoutInflater对象
	protected Context mContext;//上下文对象
	protected List<T> mDatas;//泛型集合
	private final int mItemLayoutId;//布局文件id
	
	public CommonAdapter(Context context,List<T> mDatas,int itemLayoutId){
		mInflater=LayoutInflater.from(context);
		this.mContext=context;
		this.mDatas=mDatas;
		this.mItemLayoutId=itemLayoutId;
	}
	//返回数据的个数
	@Override
	public int getCount() {
		return mDatas.size();
	}
	//得到具体位置的对象
	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	//绘制每个位置的页面
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder=getViewHolder(position, convertView, parent);
		convert(viewHolder,getItem(position));
		return viewHolder.getConvertView();
	}
	/**
	 * 定义一个抽象方法，传入资源id,并设置具体的值
	 * @param helper ViewHolder对象
	 * @param item 泛型对象
	 */
	public abstract void convert(ViewHolder helper,T item);
	
	//获取ViewHolder对象
	private ViewHolder getViewHolder(int position, View convertView,ViewGroup parent){
		return ViewHolder.get(mContext, convertView, parent,mItemLayoutId, position);
	}
}
