package com.dalianbobo.lepaandroid.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ClownFish on 2016/1/21.
 */
public class RecViewHolder {
    private SparseArray<View> viewHolder;
    private View view;

    public static  RecViewHolder getViewHolder(View view){
        RecViewHolder viewHolder = (RecViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new RecViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
    private RecViewHolder(View view) {
        this.view = view;
        viewHolder = new SparseArray<View>();
        view.setTag(viewHolder);
    }
    public <T extends View> T get(int id) {
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public View getConvertView() {
        return view;
    }

    public TextView getTextView(int id) {

        return get(id);
    }
    public Button getButton(int id) {

        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int  id,CharSequence charSequence){
        getTextView(id).setText(charSequence);
    }

}
