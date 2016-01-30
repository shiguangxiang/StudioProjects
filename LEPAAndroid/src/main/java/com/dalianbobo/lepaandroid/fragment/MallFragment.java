package com.dalianbobo.lepaandroid.fragment;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.ProductDetailsActivity;
import com.dalianbobo.lepaandroid.activity.ProductRecyclerListActivity;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.bean.DoubleBean;
import com.dalianbobo.lepaandroid.bean.OneGuangGaoBean;
import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.dalianbobo.lepaandroid.constant.RequestUrls;
import com.dalianbobo.lepaandroid.weiget.ImageCycleView;
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
 * 商城界面
 * Created by Administrator on 2015/11/24 on 11:26.
 * Author Clown Fish
 */
public class MallFragment extends BaseFragment implements View.OnClickListener {
    private final String TAG = "MallFragment";
    //轮播图对象
    private ImageCycleView mImageCycleView;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private LinearLayout mLlIndex, mLlMainYiyuan,mLlBoBi;

    private SwipeRefreshLayout mRefreshLayout;
    private TextView mTvTt, mTvDoubleName, mTvMaletoysName, mTvSexyLingerieName,mTvFlirtingName,mTvFemaletoysName;

    private SimpleDraweeView mIvOne, mIvSaleImageTwo, mIvSaleImageThree, mIvSaleImageFour;
    private SimpleDraweeView mIvTtImageOne, mIvTtImageTwo, mIvTtImageThree, mIvTtImageFour, mIvTtImageFive, mIvTtImagesix;
    private SimpleDraweeView mIvMaletoysImageOne, mIvMaletoysImageTwo, mIvMaletoysImageThree, mIvMaletoysImageFour, mIvMaletoysImageFive, mIvMaletoysImagesix;
    private SimpleDraweeView mIvFlirtingImageOne, mIvFlirtingImageTwo, mIvFlirtingImageThree, mIvFlirtingImageFour, mIvFlirtingImageFive, mIvFlirtingImagesix;
    private SimpleDraweeView mIvSexyLingerieImageOne, mIvSexyLingerieImageTwo, mIvSexyLingerieImageThree, mIvSexyLingerieImageFour, mIvSexyLingerieImageFive, mIvSexyLingerieImagesix;
    private SimpleDraweeView mIvFemaletoysImageOne, mIvFemaletoysImageTwo, mIvFemaletoysImageThree, mIvFemaletoysImageFour, mIvFemaletoysImageFive, mIvFemaletoysImagesix;
    private SimpleDraweeView mIvDoubleImageOne, mIvDoubleImageTwo, mIvDoubleImageThree, mIvDoubleImageFour, mIvDoubleImageFive, mIvDoubleImagesix;
    private  Handler mHandlerImg = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i(TAG, "图片地址:" + imageUrls);
                    mImageCycleView.setImageResources(imageUrls, imageUrls, mAdCycleViewListener, 1);
                    break;
                case 2:
                    Log.i(TAG, "handle" + msg.obj);
                    ImageRequest imageRequest = ConfigConstants.getImageRequest(mIvOne, "http://img12.360buyimg.com/n1/jfs/t1210/91/369437020/144695/c38c36b4/551ca68dN3f4af6c6.jpg");
                    DraweeController draweeController = ConfigConstants.getDraweeController(imageRequest);
                    mIvOne.setController(draweeController);
            }
            return false;
        }
    }) ;

    @Override
    public int setContentView() {
        getImageData();

        return R.layout.tabfragment_one;
    }

    /**
     * 获取轮播图数据
     */
    private void getImageData() {
        imageUrls = new ArrayList<>();
        imageUrls.add("http://192.168.1.7/res/upload/image/default/2016/01/061452051627170621.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB130RZKFXXXXc4XpXXSutbFXXX.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB19HBlJpXXXXbrXVXXSutbFXXX.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB130RZKFXXXXc4XpXXSutbFXXX.jpg");
        imageUrls.add("https://img.alicdn.com/tps/TB1MLszKpXXXXcoXVXXXXXXXXXX-520-280.jpg");

    }

    @Override
    public void initViews() {
        mLlBoBi = (LinearLayout) mView.findViewById(R.id.ll_bobi);
        mImageCycleView = (ImageCycleView) mView.findViewById(R.id.ad_view);
        mImageCycleView.setImageResources(imageUrls, imageUrls, mAdCycleViewListener, 1);
        Log.i(TAG, "图片滚动：" + imageUrls.toString());
        mLlMainYiyuan = (LinearLayout) mView.findViewById(R.id.ll_main_yiyuan);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_container);
        mRefreshLayout.setColorSchemeResources(R.color.yellow);
        mIvOne = (SimpleDraweeView) mView.findViewById(R.id.image_one);
        mIvSaleImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_image_two);
        mIvSaleImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_image_three);
        mIvSaleImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_image_four);

        mTvTt = (TextView) mView.findViewById(R.id.tv_tt);
        mIvTtImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_one);
        mIvTtImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_two);
        mIvTtImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_three);
        mIvTtImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_four);
        mIvTtImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_five);
        mIvTtImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_tt_six);

        mTvMaletoysName = (TextView) mView.findViewById(R.id.tv_maletoys_name);
        mIvMaletoysImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_one);
        mIvMaletoysImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_two);
        mIvMaletoysImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_three);
        mIvMaletoysImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_four);
        mIvMaletoysImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_five);
        mIvMaletoysImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_maletoys_six);

        mTvSexyLingerieName = (TextView) mView.findViewById(R.id.tv_SexyLingerie_name);
        mIvSexyLingerieImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_one);
        mIvSexyLingerieImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_two);
        mIvSexyLingerieImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_three);
        mIvSexyLingerieImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_four);
        mIvSexyLingerieImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_five);
        mIvSexyLingerieImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_SexyLingerie_six);

        mTvFlirtingName = (TextView) mView.findViewById(R.id.tv_Flirting_name);
        mIvFlirtingImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_one);
        mIvFlirtingImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_two);
        mIvFlirtingImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_three);
        mIvFlirtingImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_four);
        mIvFlirtingImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_five);
        mIvFlirtingImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_Flirting_six);


        mTvFemaletoysName = (TextView) mView.findViewById(R.id.tv_Femaletoys_name);
        mIvFemaletoysImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_one);
        mIvFemaletoysImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_two);
        mIvFemaletoysImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_three);
        mIvFemaletoysImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_four);
        mIvFemaletoysImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_five);
        mIvFemaletoysImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_Femaletoys_six);


        mTvDoubleName = (TextView) mView.findViewById(R.id.tv_double_name);
        mIvDoubleImageOne = (SimpleDraweeView) mView.findViewById(R.id.iv_double_one);
        mIvDoubleImageTwo = (SimpleDraweeView) mView.findViewById(R.id.iv_double_two);
        mIvDoubleImageThree = (SimpleDraweeView) mView.findViewById(R.id.iv_double_three);
        mIvDoubleImageFour = (SimpleDraweeView) mView.findViewById(R.id.iv_double_four);
        mIvDoubleImageFive = (SimpleDraweeView) mView.findViewById(R.id.iv_double_five);
        mIvDoubleImagesix = (SimpleDraweeView) mView.findViewById(R.id.iv_double_six);

    }

    /**
     * 获取个人中心数据
     */
    private void getIndexData() {
        showScreen("正在加载...");
        final List<OneGuangGaoBean> oneGuangGaoBeans = new ArrayList<>();
        final List<DoubleBean> doubleBeans = new ArrayList<>();
        final List<DoubleBean> saleBeans = new ArrayList<>();
        final List<DoubleBean> TTBeans = new ArrayList<>();
        final List<DoubleBean> maletoysBeans = new ArrayList<>();
        final List<DoubleBean> SexyLingerieBeans = new ArrayList<>();
        final List<DoubleBean> FlirtingBeans = new ArrayList<>();
        final List<DoubleBean> FemaletoysBeans = new ArrayList<>();
        final String api_token = apiToken("site", "index");
        StringRequest request = new StringRequest(Method.POST, RequestUrls.SITEINDEX_URL, new Listener<String>() {
            @Override
            public void onResponse(String s) {
                dissScreen();
                //请求成功
                Log.i(TAG, "商城数据:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    String message = jsonObject.getString("message");
                    if ("success".equals(code)) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray saleData = data.getJSONArray("Sale");
                        Log.i(TAG, "促销：" + saleData.toString());
                        for (int i = 3; i < saleData.length(); i++) {
                            JSONObject doubleObj = saleData.getJSONObject(i);
                            String goodsId = doubleObj.getString("goods_id");
                            String goodsPic = doubleObj.getString("goods_pic");
                            String goodsUrl = doubleObj.getString("goods_url");
                            String goodsDisModle = doubleObj.getString("goods_dismodel");
                            DoubleBean doubleBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            saleBeans.add(doubleBean);
                        }
                        getSaleImage(saleBeans);

                        JSONArray ttData = data.getJSONArray("TT");
                        JSONObject ttName = ttData.getJSONObject(0);
                        mTvTt.setText(ttName.getString("name"));
                        for (int i = 1; i < ttData.length(); i++) {
                            JSONObject TTObj = ttData.getJSONObject(i);
                            String goodsId = TTObj.getString("goods_id");
                            String goodsPic = TTObj.getString("goods_pic");
                            String goodsUrl = TTObj.getString("goods_url");
                            String goodsDisModle = TTObj.getString("goods_dismodel");
                            DoubleBean TTBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            TTBeans.add(TTBean);
                        }
                        getTTImage(TTBeans);

                        JSONArray maletoysData = data.getJSONArray("Maletoys");
                        JSONObject MaletoysName = maletoysData.getJSONObject(0);
                        mTvMaletoysName.setText(MaletoysName.getString("name"));
                        for (int i = 1; i < maletoysData.length(); i++) {
                            JSONObject TTObj = maletoysData.getJSONObject(i);
                            String goodsId = TTObj.getString("goods_id");
                            String goodsPic = TTObj.getString("goods_pic");
                            String goodsUrl = TTObj.getString("goods_url");
                            String goodsDisModle = TTObj.getString("goods_dismodel");
                            DoubleBean maletoysBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            maletoysBeans.add(maletoysBean);
                        }
                        getMaletoyImage(maletoysBeans);


                        JSONArray sexyLingerieData = data.getJSONArray("SexyLingerie");
                        JSONObject SexyLingerieName = maletoysData.getJSONObject(0);
                        mTvSexyLingerieName.setText(SexyLingerieName.getString("name"));
                        for (int i = 1; i < sexyLingerieData.length(); i++) {
                            JSONObject TTObj = sexyLingerieData.getJSONObject(i);
                            String goodsId = TTObj.getString("goods_id");
                            String goodsPic = TTObj.getString("goods_pic");
                            String goodsUrl = TTObj.getString("goods_url");
                            String goodsDisModle = TTObj.getString("goods_dismodel");
                            DoubleBean maletoysBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            SexyLingerieBeans.add(maletoysBean);
                        }
                        getSexyLingerieImage(SexyLingerieBeans);



                        JSONArray FlirtingData = data.getJSONArray("Flirting");
                        JSONObject FlirtingName = maletoysData.getJSONObject(0);
                        mTvFlirtingName.setText(FlirtingName.getString("name"));
                        for (int i = 1; i < FlirtingData.length(); i++) {
                            JSONObject TTObj = FlirtingData.getJSONObject(i);
                            String goodsId = TTObj.getString("goods_id");
                            String goodsPic = TTObj.getString("goods_pic");
                            String goodsUrl = TTObj.getString("goods_url");
                            String goodsDisModle = TTObj.getString("goods_dismodel");
                            DoubleBean maletoysBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            FlirtingBeans.add(maletoysBean);
                        }
                        getFlirtingImage(FlirtingBeans);



                        JSONArray FemaletoysData = data.getJSONArray("Femaletoys");
                        JSONObject FemaletoysName = FemaletoysData.getJSONObject(0);
                        mTvFemaletoysName.setText(FemaletoysName.getString("name"));
                        for (int i = 1; i < FemaletoysData.length(); i++) {
                            JSONObject TTObj = FemaletoysData.getJSONObject(i);
                            String goodsId = TTObj.getString("goods_id");
                            String goodsPic = TTObj.getString("goods_pic");
                            String goodsUrl = TTObj.getString("goods_url");
                            String goodsDisModle = TTObj.getString("goods_dismodel");
                            DoubleBean maletoysBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            FemaletoysBeans.add(maletoysBean);
                        }
                        getFemaletoysImage(FemaletoysBeans);

                        JSONArray doubleData = data.getJSONArray("Double");
                        Log.i(TAG, doubleData.toString());
                        JSONObject doubleName = doubleData.getJSONObject(0);
                        mTvDoubleName.setText(doubleName.getString("name"));
                        for (int i = 1; i < doubleData.length(); i++) {
                            JSONObject doubleObj = doubleData.getJSONObject(i);
                            String goodsId = doubleObj.getString("goods_id");
                            String goodsPic = doubleObj.getString("goods_pic");
                            String goodsUrl = doubleObj.getString("goods_url");
                            String goodsDisModle = doubleObj.getString("goods_dismodel");
                            DoubleBean doubleBean = new DoubleBean(goodsId, goodsPic, goodsUrl, goodsDisModle);
                            doubleBeans.add(doubleBean);
                        }

                        getDoubleImage(doubleBeans);
                        Log.i(TAG, "Double:---->" + doubleBeans.toString());

                    }
                    showToast(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dissScreen();
                showToast("请求错误!");
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("api_token", api_token);
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



    /**
     * 促销区
     *
     * @param doubleBeans
     */
    private void getSaleImage(List<DoubleBean> doubleBeans) {
        String saleOneUrl = doubleBeans.get(0).getGoodsPic();
        String saleTwoUrl = doubleBeans.get(1).getGoodsPic();
        String saleThreeUrl = doubleBeans.get(2).getGoodsPic();
        String saleFourUrl = doubleBeans.get(3).getGoodsPic();
        setImageSimpView(mIvOne, RequestUrls.BASE_IMG + saleOneUrl);
        setImageSimpView(mIvSaleImageTwo, RequestUrls.BASE_IMG + saleTwoUrl);
        setImageSimpView(mIvSaleImageThree, RequestUrls.BASE_IMG + saleThreeUrl);
        setImageSimpView(mIvSaleImageFour, RequestUrls.BASE_IMG + saleFourUrl);
        Log.i(TAG, "--------------->sale" + RequestUrls.BASE_IMG + saleTwoUrl);
    }

    /**
     * TT专区
     *
     * @param doubleBeans
     */
    private void getTTImage(List<DoubleBean> doubleBeans) {
        String ttOneUrl = doubleBeans.get(0).getGoodsPic();
        String ttTwoUrl = doubleBeans.get(1).getGoodsPic();
        String ttThreeUrl = doubleBeans.get(2).getGoodsPic();
        String ttFourUrl = doubleBeans.get(3).getGoodsPic();
        String ttFiveUrl = doubleBeans.get(4).getGoodsPic();
        String ttSixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvTtImageOne, RequestUrls.BASE_IMG + ttOneUrl.trim());
        setImageSimpView(mIvTtImageTwo, RequestUrls.BASE_IMG + ttTwoUrl);
        setImageSimpView(mIvTtImageThree, RequestUrls.BASE_IMG + ttThreeUrl);
        setImageSimpView(mIvTtImageFour, RequestUrls.BASE_IMG + ttFourUrl);
        setImageSimpView(mIvTtImageFive, RequestUrls.BASE_IMG + ttFiveUrl);
        setImageSimpView(mIvTtImagesix, RequestUrls.BASE_IMG + ttSixUrl);
        Log.i(TAG, "--------------->TT" + RequestUrls.BASE_IMG + ttOneUrl.trim());
    }

    /**
     * 男人趣味
     *
     * @param doubleBeans
     */
    private void getMaletoyImage(List<DoubleBean> doubleBeans) {
        String maletoyOneUrl = doubleBeans.get(0).getGoodsPic();
        String maletoyTwoUrl = doubleBeans.get(1).getGoodsPic();
        String maletoyThreeUrl = doubleBeans.get(2).getGoodsPic();
        String maletoyFourUrl = doubleBeans.get(3).getGoodsPic();
        String maletoyFiveUrl = doubleBeans.get(4).getGoodsPic();
        String maletoySixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvMaletoysImageOne, RequestUrls.BASE_IMG + maletoyOneUrl);
        setImageSimpView(mIvMaletoysImageTwo, RequestUrls.BASE_IMG + maletoyTwoUrl);
        setImageSimpView(mIvMaletoysImageThree, RequestUrls.BASE_IMG + maletoyThreeUrl);
        setImageSimpView(mIvMaletoysImageFour, RequestUrls.BASE_IMG + maletoyFourUrl);
        setImageSimpView(mIvMaletoysImageFive, RequestUrls.BASE_IMG + maletoyFiveUrl);
        setImageSimpView(mIvMaletoysImagesix, RequestUrls.BASE_IMG + maletoySixUrl);
        Log.i(TAG, "--------------->男人趣味" + RequestUrls.BASE_IMG + maletoyOneUrl);
    }

    /**
     * 情趣内衣
     *
     * @param doubleBeans
     */
    private void getSexyLingerieImage(List<DoubleBean> doubleBeans) {
        String sexyLingrieOneUrl = doubleBeans.get(0).getGoodsPic();
        String sexyLingrieTwoUrl = doubleBeans.get(1).getGoodsPic();
        String sexyLingrieThreeUrl = doubleBeans.get(2).getGoodsPic();
        String sexyLingrieFourUrl = doubleBeans.get(3).getGoodsPic();
        String sexyLingrieFiveUrl = doubleBeans.get(4).getGoodsPic();
        String sexyLingrieSixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvSexyLingerieImageOne, RequestUrls.BASE_IMG + sexyLingrieOneUrl);
        setImageSimpView(mIvSexyLingerieImageTwo, RequestUrls.BASE_IMG + sexyLingrieTwoUrl);
        setImageSimpView(mIvSexyLingerieImageThree, RequestUrls.BASE_IMG + sexyLingrieThreeUrl);
        setImageSimpView(mIvSexyLingerieImageFour, RequestUrls.BASE_IMG + sexyLingrieFourUrl);
        setImageSimpView(mIvSexyLingerieImageFive, RequestUrls.BASE_IMG + sexyLingrieFiveUrl);
        setImageSimpView(mIvSexyLingerieImagesix, RequestUrls.BASE_IMG + sexyLingrieSixUrl);
        Log.i(TAG, "--------------->男人趣味" + RequestUrls.BASE_IMG + sexyLingrieSixUrl);
    }

    /**
     * 调情助兴
     *
     * @param doubleBeans
     */
    private void getFlirtingImage(List<DoubleBean> doubleBeans) {
        String FlirtingOneUrl = doubleBeans.get(0).getGoodsPic();
        String FlirtingTwoUrl = doubleBeans.get(1).getGoodsPic();
        String FlirtingThreeUrl = doubleBeans.get(2).getGoodsPic();
        String FlirtingFourUrl = doubleBeans.get(3).getGoodsPic();
        String FlirtingFiveUrl = doubleBeans.get(4).getGoodsPic();
        String FlirtingSixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvFlirtingImageOne, RequestUrls.BASE_IMG + FlirtingOneUrl);
        setImageSimpView(mIvFlirtingImageTwo, RequestUrls.BASE_IMG + FlirtingTwoUrl);
        setImageSimpView(mIvFlirtingImageThree, RequestUrls.BASE_IMG + FlirtingThreeUrl);
        setImageSimpView(mIvFlirtingImageFour, RequestUrls.BASE_IMG + FlirtingFourUrl);
        setImageSimpView(mIvFlirtingImageFive, RequestUrls.BASE_IMG + FlirtingFiveUrl);
        setImageSimpView(mIvFlirtingImagesix, RequestUrls.BASE_IMG + FlirtingSixUrl);
        Log.i(TAG, "--------------->调情助兴" + RequestUrls.BASE_IMG + FlirtingFiveUrl);
    }


    /**
     * 女性玩具
     * @param doubleBeans
     */
    private void getFemaletoysImage(List<DoubleBean> doubleBeans) {
        String FemaletoysOneUrl = doubleBeans.get(0).getGoodsPic();
        String FemaletoysTwoUrl = doubleBeans.get(1).getGoodsPic();
        String FemaletoysThreeUrl = doubleBeans.get(2).getGoodsPic();
        String FemaletoysFourUrl = doubleBeans.get(3).getGoodsPic();
        String FemaletoysFiveUrl = doubleBeans.get(4).getGoodsPic();
        String FemaletoysSixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvFemaletoysImageOne, RequestUrls.BASE_IMG + FemaletoysOneUrl);
        setImageSimpView(mIvFemaletoysImageTwo, RequestUrls.BASE_IMG + FemaletoysTwoUrl);
        setImageSimpView(mIvFemaletoysImageThree, RequestUrls.BASE_IMG + FemaletoysThreeUrl);
        setImageSimpView(mIvFemaletoysImageFour, RequestUrls.BASE_IMG + FemaletoysFourUrl);
        setImageSimpView(mIvFemaletoysImageFive, RequestUrls.BASE_IMG + FemaletoysFiveUrl);
        setImageSimpView(mIvFemaletoysImagesix, RequestUrls.BASE_IMG + FemaletoysSixUrl);
        Log.i(TAG, "--------------->女性玩具" + RequestUrls.BASE_IMG + FemaletoysSixUrl);
    }

    /**
     * 激情加倍
     * @param doubleBeans
     */
    private void getDoubleImage(List<DoubleBean> doubleBeans) {
        String DoubleOneUrl = doubleBeans.get(0).getGoodsPic();
        String DoubleTwoUrl = doubleBeans.get(1).getGoodsPic();
        String DoubleThreeUrl = doubleBeans.get(2).getGoodsPic();
        String DoubleFourUrl = doubleBeans.get(3).getGoodsPic();
        String DoubleFiveUrl = doubleBeans.get(4).getGoodsPic();
        String DoubleSixUrl = doubleBeans.get(5).getGoodsPic();
        setImageSimpView(mIvDoubleImageOne, RequestUrls.BASE_IMG + DoubleOneUrl);
        setImageSimpView(mIvDoubleImageTwo, RequestUrls.BASE_IMG + DoubleTwoUrl);
        setImageSimpView(mIvDoubleImageThree, RequestUrls.BASE_IMG + DoubleThreeUrl);
        setImageSimpView(mIvDoubleImageFour, RequestUrls.BASE_IMG + DoubleFourUrl);
        setImageSimpView(mIvDoubleImageFive, RequestUrls.BASE_IMG + DoubleFiveUrl);
        setImageSimpView(mIvDoubleImagesix, RequestUrls.BASE_IMG + DoubleSixUrl);
        Log.i(TAG, "--------------->激情加倍" + RequestUrls.BASE_IMG + DoubleSixUrl);
    }



    /**
     * 设置图片
     *
     * @param mIvSaleImage 图片控件
     * @param saleImageUrl 图片地址
     */
    private void setImageSimpView(SimpleDraweeView mIvSaleImage, String saleImageUrl) {
        ImageRequest imageRequest = ConfigConstants.getImageRequest(mIvSaleImage, saleImageUrl);
        DraweeController draweeController = ConfigConstants.getDraweeController(imageRequest);
        mIvSaleImage.setController(draweeController);
    }



    @Override
    public void initListeners() {
        mLlMainYiyuan.setOnClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        mLlBoBi.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        getIndexData();
    }




    /**
     * 轮播图点击监听事件
     */
    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            longToast(imageUrls.get(position).toString());
        }
    };

    private class MyAsnyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
                imageUrls.add("https://aecpm.alicdn.com/simba/img/TB130RZKFXXXXc4XpXXSutbFXXX.jpg");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            getImageData();
//            mImageCycleView.setImageResources(imageUrls, imageUrls, mAdCycleViewListener, 1);
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_yiyuan:
                //启动一个Activity
                startActivity(ProductRecyclerListActivity.class);
                break;
            case R.id.ll_bobi:
                startActivity(ProductDetailsActivity.class);
                break;
        }
    }


}
