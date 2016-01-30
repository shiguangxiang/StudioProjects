package com.dalianbobo.lepaandroid.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.weiget.ImageCycleView;

import java.util.ArrayList;

/**
 * 社区页面
 * Created by Administrator on 2015/11/24 on 11:28.
 * Author Clown Fish
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener{
    private TextView mTvHeadTitle;
    //轮播图对象
    private ImageCycleView mImageCycleView;
    private ArrayList<String> imageUrls;
    private LinearLayout mLlIndex, mLlMainYiyuan;
    @Override
    public int setContentView() {
        getImageData();
        return R.layout.tabfragment_three;
    }

    @Override
    public void initViews() {
        mTvHeadTitle = (TextView) mView.findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("社区");
        mImageCycleView = (ImageCycleView) mView.findViewById(R.id.ic_view);
        mImageCycleView.setImageResources(imageUrls, imageUrls, mAdCycleViewListener, 1);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initDatas() {

    }

    /**
     * 获取轮播图数据
     */
    private void getImageData() {
        imageUrls = new ArrayList<>();
        imageUrls.add("https://img.alicdn.com/tps/TB1DqSRKFXXXXcvXpXXXXXXXXXX-520-280.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB130RZKFXXXXc4XpXXSutbFXXX.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB19HBlJpXXXXbrXVXXSutbFXXX.jpg");
        imageUrls.add("https://aecpm.alicdn.com/simba/img/TB130RZKFXXXXc4XpXXSutbFXXX.jpg");
        imageUrls.add("https://img.alicdn.com/tps/TB1MLszKpXXXXcoXVXXXXXXXXXX-520-280.jpg");

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

    @Override
    public void onClick(View v) {

    }
}
