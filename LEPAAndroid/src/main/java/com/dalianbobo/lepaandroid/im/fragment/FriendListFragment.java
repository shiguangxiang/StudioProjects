package com.dalianbobo.lepaandroid.im.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qin on 2016/1/28.
 */
public class FriendListFragment extends Fragment {

    private RecyclerView recyclerview_friendlist;
    private SwipeRefreshLayout swiperefresh_friendlist;
    private List<Map<String, Object>> list;
    private Map<String, Object> map;
    private FriendListAdapter mAdapter;
    private SimpleDraweeView headimg;

    private String url = "";
    private String rong_token;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_friendlist, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview_friendlist = (RecyclerView) view.findViewById(R.id.recyclerview_friendlist);
        swiperefresh_friendlist = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh_friendlist);
        headimg = (SimpleDraweeView) view.findViewById(R.id.recyclerview_item_friendlist_iv_headimg);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            map = new HashMap<>();
            map.put("username", "第" + i + "个");
            list.add(map);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerview_friendlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FriendListAdapter();
        recyclerview_friendlist.setAdapter(mAdapter);
    }

    private void initListener() {
        swiperefresh_friendlist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


    private void RequestFriendList() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        swiperefresh_friendlist.setRefreshing(false);
                        ResovlJson(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),"网络错误~_~",Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("api_token", rong_token);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Android");
                return headers;
            }
        };
        LEPAppLication.getInstance().getRequestQueue().add(request).setTag(request);
    }

    private void ResovlJson(String json) {
        try {
            JSONObject object = new JSONObject(json);
            String url = object.getString("headimgurl");
            String username = object.getString("username");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHodler> {

        @Override
        public FriendListViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            FriendListViewHodler hodler = new FriendListViewHodler(
                    LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_item_friendlist, parent, false)
            );
            return hodler;
        }

        @Override
        public void onBindViewHolder(FriendListViewHodler holder, int position) {
            holder.recyclerview_item_friendlist_iv_headimg.setImageURI((Uri) list.get(position).get("headimgurl"));
            holder.recyclerview_item_friendlist_tv_name.setText((String) list.get(position).get("username"));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class FriendListViewHodler extends RecyclerView.ViewHolder {
            SimpleDraweeView recyclerview_item_friendlist_iv_headimg;
            TextView recyclerview_item_friendlist_tv_name;

            public FriendListViewHodler(View itemView) {
                super(itemView);
                recyclerview_item_friendlist_iv_headimg = (SimpleDraweeView) itemView.findViewById(R.id.recyclerview_item_friendlist_iv_headimg);
                recyclerview_item_friendlist_tv_name = (TextView) itemView.findViewById(R.id.recyclerview_item_friendlist_tv_name);
            }
        }
    }

}
