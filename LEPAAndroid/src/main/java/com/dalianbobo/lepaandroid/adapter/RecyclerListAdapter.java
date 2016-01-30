package com.dalianbobo.lepaandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import java.util.List;

/**
 * Recycler 数据适配器
 * Created by ClownFish on 2016/1/21.
 */
public abstract class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.RVHolder> {

    private List<?> list;
    private Context context;

    public RecyclerListAdapter(Context context, List<?> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerListAdapter.RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(onCreateViewLayoutID(viewType), null);
        return new RVHolder(view);
    }

    public abstract int onCreateViewLayoutID(int viewType);


    @Override
    public void onViewRecycled(RVHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(final RVHolder holder, int position) {
        onBindViewHolder(holder.getViewHolder(), position);
        if (onItemClickListener != null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(null, v, holder.getPosition(), holder.getItemId());
                }
            });
        }
    }

    protected abstract void onBindViewHolder(RecViewHolder viewHolder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    private AdapterView.OnItemClickListener onItemClickListener;

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public class RVHolder extends RecyclerView.ViewHolder {


        private RecViewHolder mRecViewHolder;

        public RVHolder(View itemView) {
            super(itemView);
            mRecViewHolder = RecViewHolder.getViewHolder(itemView);
        }

        public RecViewHolder getViewHolder() {
            return mRecViewHolder;
        }

    }
}
