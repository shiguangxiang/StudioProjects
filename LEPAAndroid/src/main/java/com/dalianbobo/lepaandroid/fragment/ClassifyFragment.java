package com.dalianbobo.lepaandroid.fragment;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.adapter.CommonAdapter;
import com.dalianbobo.lepaandroid.adapter.ViewHolder;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.bean.ListClassfyBean;
import com.dalianbobo.lepaandroid.cache.ImageCacheManager;
import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类页面
 * Created by Administrator on 2015/11/24 on 11:27.
 * Author Clown Fish
 */
public class ClassifyFragment extends BaseFragment {
    private final String TAG = "ClassifyFragment";
    //分类列表数据
    private ListView mLvClassfy;
    //自定义适配器
    private BaseAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.tabfragment_two;
    }


    @Override
    public void initViews() {
        mLvClassfy = (ListView) mView.findViewById(R.id.lv_classify);
    }

    /**
     * 给ListView适配数据
     */
//    private void setListAdapter() {
//        List<ListClassfyBean> listClassfyBeans = new ArrayList<>();
////        ListClassfyBean classfyBean1 = new ListClassfyBean("安全套", R.mipmap.fenlei1, "超博套  颗粒套  旋螺套", "果味套  冰火套  异性套");
////        ListClassfyBean classfyBean2 = new ListClassfyBean("情趣内衣", R.mipmap.fenlei2, "性感睡裙  制服诱惑  丝袜美腿", "性感 T 库  开档连体  性感内衣");
////        ListClassfyBean classfyBean3 = new ListClassfyBean("男用趣味", R.mipmap.fenlei3, "女优名器  充气美人  手动飞机杯", "延时套环  臂胸倒膜  震动飞机杯");
////        ListClassfyBean classfyBean4 = new ListClassfyBean("女性玩具", R.mipmap.fenlei4, " G 点震动棒  内外双刺激  跳蛋", "仿真男根  AV按摩棒  性爱机器");
////        ListClassfyBean classfyBean5 = new ListClassfyBean("调情趣味", R.mipmap.fenlei5, "情侣共振  体味道具  束缚游戏", "震动套环  按摩油  调情用品");
////        ListClassfyBean classfyBean6 = new ListClassfyBean("激情加倍", R.mipmap.fenlei6, "男性延时  男性增强  催情香水", "女性提升  女性保养  润滑液");
////        listClassfyBeans.add(classfyBean1);
////        listClassfyBeans.add(classfyBean2);
////        listClassfyBeans.add(classfyBean3);
////        listClassfyBeans.add(classfyBean4);
////        listClassfyBeans.add(classfyBean5);
////        listClassfyBeans.add(classfyBean6);
//        adapter = new CommonAdapter<ListClassfyBean>(getActivity(), listClassfyBeans, R.layout.fragmenttow_classfy_item) {
//            @Override
//            public void convert(ViewHolder helper, ListClassfyBean item) {
//                ImageView classImg = helper.getView(R.id.iv_classImg);
//                TextView className = helper.getView(R.id.tv_className);
//                TextView classNameOne = helper.getView(R.id.tv_classNameOne);
//                TextView classNameTwo = helper.getView(R.id.tv_classNameTwo);
//
////                classImg.setImageResource(item.getClassImg());
//                className.setText(item.getClassName());
//                classNameOne.setText(item.getClassNameOne());
//                classNameTwo.setText(item.getClassNameTwo());
//            }
//        };
//        mLvClassfy.setAdapter(adapter);
//    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initDatas() {
        final List<ListClassfyBean> listClassfyBeans = new ArrayList<>();
        final String api_token = apiToken("goods-class", "index");
        StringRequest request = new StringRequest(Request.Method.POST, RequestUrls.GOODSCLASS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dissScreen();
                Log.i(TAG, "分类数据：" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            String className = data.getString("name");
                            String classImage = data.getString("img");
                            String classNameOne = data.getString("one");
                            String classNameTwo = data.getString("two");
                            ListClassfyBean classfyBean = new ListClassfyBean(className, classImage, classNameOne, classNameTwo);
                            listClassfyBeans.add(classfyBean);
                        }
                        setListAdapter(listClassfyBeans);
                    }
                    showToast(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                showToast("请求错误！");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.i(TAG, "API Token:" + api_token);
                map.put("api_token", api_token);
                return map;
            }
        };
        LEPAppLication.sInstance.getRequestQueue().add(request);
    }

    /**
     * 适配数据
     * @param listClassfyBeans 数据资源
     */
    private void setListAdapter(List<ListClassfyBean> listClassfyBeans) {
        adapter = new CommonAdapter<ListClassfyBean>(getActivity(),listClassfyBeans,R.layout.fragmenttow_classfy_item) {
            @Override
            public void convert(ViewHolder helper, ListClassfyBean item) {
                SimpleDraweeView classImg = helper.getView(R.id.iv_classImg);
                TextView className = helper.getView(R.id.tv_className);
                TextView classNameOne = helper.getView(R.id.tv_classNameOne);
                TextView classNameTwo = helper.getView(R.id.tv_classNameTwo);

                className.setText(item.getClassName());
                classNameOne.setText(item.getClassNameOne());
                classNameTwo.setText(item.getClassNameTwo());
                Log.i(TAG,RequestUrls.BASE_IMG + item.getClassImg());
                ImageRequest imageRequest = ConfigConstants.getImageRequest(classImg, RequestUrls.BASE_IMG + item.getClassImg());
                DraweeController draweeController = ConfigConstants.getDraweeController(imageRequest);
                classImg.setController(draweeController);
            }
        };

        mLvClassfy.setAdapter(adapter);
    }
}
