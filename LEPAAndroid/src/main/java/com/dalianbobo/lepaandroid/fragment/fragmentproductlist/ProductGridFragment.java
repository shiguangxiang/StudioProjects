package com.dalianbobo.lepaandroid.fragment.fragmentproductlist;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.adapter.CommonAdapter;
import com.dalianbobo.lepaandroid.adapter.ViewHolder;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.base.ProductListBean;
import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ClownFish on 2016/1/26.
 */
public class ProductGridFragment extends BaseFragment {

    private GridView mGridView;
    private List productListBeans;
    private BaseAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.product_grid_fragment;
    }

    @Override
    public void initViews() {
        mGridView = (GridView) mView.findViewById(R.id.gv_product);
        mGridView.setVerticalSpacing(8);
        mGridView.setHorizontalSpacing(8);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initDatas() {

        getProductData();
    }

    /**
     * 获取列表数据
     */
    private void getProductData() {
        productListBeans = new ArrayList<>();
        ProductListBean productListBean = new ProductListBean("1", "Bananle is at looking whta!", "￥88", "http://img02.taobaocdn.com/imgextra/etao/i2/T1PnR2XgXdXXcqKyI8_100806.jpg_220x220.jpg","769");
        ProductListBean productListBean1 = new ProductListBean("1", "Bananle", "￥88", "http://i4.3conline.com/images/piclib/201001/07/batch/1/51431/1262874754098qjxe5ru3is_medium.jpg","7567");
        ProductListBean productListBean2 = new ProductListBean("2", "Apple", "￥68", "http://i1.3conline.com/images/piclib/201112/31/batch/1/123390/1325322620418siiwayuc12_medium.jpg","776");
        ProductListBean productListBean3 = new ProductListBean("2", "Apple", "￥68", "http://image.photophoto.cn/nm-7/018/041/0180410073.jpg","89798");
        productListBeans.add(productListBean);
        productListBeans.add(productListBean1);
        productListBeans.add(productListBean2);
        productListBeans.add(productListBean3);

        setListAdapter(productListBeans);
    }

    /**
     * 适配数据适配器
     * @param productListBeans
     */
    private void setListAdapter(List productListBeans) {
        adapter = new CommonAdapter<ProductListBean>(getContext(),productListBeans,R.layout.product_grid_item) {
            @Override
            public void convert(ViewHolder helper, ProductListBean item) {
                TextView productName = helper.getView(R.id.tv_product_name_gv);
                productName.setText(item.getProductName());
                TextView productPrice = helper.getView(R.id.tv_product_price_gv);
                productPrice.setTextColor(getResources().getColor(R.color.red));
                productPrice.setText(item.getProductPrice());
                TextView productNum = helper.getView(R.id.tv_product_num_gv);
                productNum.setText("销量:"+item.getGetProducNum());
                SimpleDraweeView draweeView = helper.getView(R.id.iv_product_image_gv);
                ImageRequest iamgeRequest = ConfigConstants.getImageRequest(draweeView,item.getGetProducImg());
                DraweeController draweeController = ConfigConstants.getDraweeController(iamgeRequest);
                draweeView.setController(draweeController);
            }
        };
        mGridView.setAdapter(adapter);
    }
}
