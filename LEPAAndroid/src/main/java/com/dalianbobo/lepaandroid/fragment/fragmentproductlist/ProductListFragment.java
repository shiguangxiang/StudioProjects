package com.dalianbobo.lepaandroid.fragment.fragmentproductlist;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
public class ProductListFragment extends BaseFragment {
    private ListView mLv;
    private List<ProductListBean> productListBeans;
    private BaseAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.product_list_fragment;
    }

    @Override
    public void initViews() {
        mLv = (ListView) mView.findViewById(R.id.lv_product);
    }

    @Override
    public void initListeners() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showToast(position + "");
            }
        });
    }

    @Override
    public void initDatas() {
        getProductData();
    }

    /**
     * 获取产品数据
     */
    private void getProductData() {
        productListBeans = new ArrayList<>();
        ProductListBean productListBean = new ProductListBean("1", "Bananle", "￥88", "http://img02.taobaocdn.com/imgextra/etao/i2/T1PnR2XgXdXXcqKyI8_100806.jpg_220x220.jpg","9999");
        ProductListBean productListBean1 = new ProductListBean("1", "Bananle", "￥88", "http://i4.3conline.com/images/piclib/201001/07/batch/1/51431/1262874754098qjxe5ru3is_medium.jpg","998");
        ProductListBean productListBean2 = new ProductListBean("2", "Apple", "￥68", "http://i1.3conline.com/images/piclib/201112/31/batch/1/123390/1325322620418siiwayuc12_medium.jpg","3423");
        ProductListBean productListBean3 = new ProductListBean("2", "Apple", "￥68", "http://image.photophoto.cn/nm-7/018/041/0180410073.jpg","213");
        productListBeans.add(productListBean);
        productListBeans.add(productListBean1);
        productListBeans.add(productListBean2);
        productListBeans.add(productListBean3);

        setListAdapter(productListBeans);

    }

    /**
     * 适配数据到适配器
     *
     * @param productListBeans
     */
    private void setListAdapter(List<ProductListBean> productListBeans) {
        adapter = new CommonAdapter<ProductListBean>(getContext(), productListBeans, R.layout.product_list_item) {
            @Override
            public void convert(ViewHolder helper, ProductListBean item) {
                TextView tvProductName = helper.getView(R.id.tv_product_name);
                TextView tvProductPrice = helper.getView(R.id.tv_product_price);
                TextView tvProductNum = helper.getView(R.id.tv_product_num);
                SimpleDraweeView ivProductImg = helper.getView(R.id.iv_product_image);

                tvProductName.setText(item.getProductName());
                tvProductPrice.setText("￥"+item.getProductPrice());
                tvProductPrice.setTextColor(getResources().getColor(R.color.red));
                tvProductNum.setText("销量: "+item.getGetProducNum());
                //图片显示
                ImageRequest iamgeRequest = ConfigConstants.getImageRequest(ivProductImg,item.getGetProducImg());
                DraweeController draweeController = ConfigConstants.getDraweeController(iamgeRequest);
                ivProductImg.setController(draweeController);
            }
        };
        mLv.setAdapter(adapter);
    }
}
