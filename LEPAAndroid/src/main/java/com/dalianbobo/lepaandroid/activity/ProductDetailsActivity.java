package com.dalianbobo.lepaandroid.activity;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.dalianbobo.lepaandroid.R;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;

/**
 * 产品详情页面
 * Created by ClownFish on 2016/1/28.
 */
public class ProductDetailsActivity extends SwipeBackActivity implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener {

    private SliderLayout mSliderLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public int setContentView() {
        return R.layout.product_details_activity;
    }

    @Override
    public void initViews() {
        mSliderLayout = (SliderLayout) findViewById(R.id.product_sliderlayout);
    }

    @Override
    public void initListeners() {
    }

    @Override
    protected void initDatas() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        for (String name : url_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            mSliderLayout.addSlider(textSliderView);

        }
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
//        mSliderLayout.setDuration(4000);
        mSliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        mSliderLayout.stopAutoCycle();
        super.onStop();

    }
}
