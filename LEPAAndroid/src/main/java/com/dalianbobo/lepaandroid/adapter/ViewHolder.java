package com.dalianbobo.lepaandroid.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用的ViewHolder类
 * @author Administrator
 *
 */
public class ViewHolder {

	private final SparseArray<View> mViews;//存放布局中的所有控件
	private View mConvertView;//具体布局的View对象
	
	
	private ViewHolder(Context context,ViewGroup parent,int layoutId,int position){
		this.mViews=new SparseArray<View>();
		mConvertView=LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);//设置tag
	}
	
	/**
	 * 得到一个ViewHolder对象
	 * @param context 上下文对象
	 * @param convertView View对象
	 * @param parent
	 * @param layoutId 布局资源id
	 * @param position 位置索引
	 * @return ViewHolder对象
	 */
	public static ViewHolder get(Context context, View convertView,ViewGroup parent, int layoutId, int position){
		if(convertView==null){
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}
	/**
	 * 通过控件的Id获取对应的控件，如果没有则加入views
	 * @param viewId 控件的id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view=mViews.get(viewId);
		if(view==null){
			view=mConvertView.findViewById(viewId);
			mViews.put(viewId,view);
		}
		return (T)view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
}
