package com.dalianbobo.lepaandroid.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 承载图片的容主器
 * Created by Administrator on 2015/12/1 on 14:06.
 * Author Clown Fish
 */
public class NyScrollView extends ScrollView {
    //每页显示15张图片
    public static final int PAGE_SIZE = 15;
    //页数
    private int page;
    //得到三个线性布局容器的对象
    private LinearLayout firstLayout;
    private LinearLayout secondLayout;
    private LinearLayout thridLayout;
    //得到三个线性容器的对象高度
    private LinearLayout firstColumnHeigth;
    private LinearLayout secondColumnHeigth;
    private LinearLayout thridColumnHeigth;
    //拿到主容器
    private View mainLayout;

    private int columnWidth;
    /**
     * 等到属性值
     * @param context
     * @param attrs
     */
    public NyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
