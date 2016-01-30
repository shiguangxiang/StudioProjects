package com.dalianbobo.lepaandroid.weiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * 向右滑动finish关闭Activity
 * Created by Administrator on 2015/12/9 on 9:35.
 * Author Clown Fish
 */
public class SildingActivityFinish extends RelativeLayout implements View.OnTouchListener {
    /**
     * 滑动的最小距离
     */
    private final int mTouchSlop;
    /**
     * 滑动类
     */
    private final Scroller mScroller;
    /**
     * SildingActivityFinish布局的父布局
     */
    private ViewGroup mParentView;
    /**
     * SildingActivityFinish的宽度
     */
    private int viewWidth;

    private OnSildingFinishListener onSildingFinishListener;
    /**
     * 处理滑动逻辑的View
     */
    private View touchView;
    /**
     * 临时存储X坐标
     */
    private float tempX;
    /**
     * 按下点的X坐标
     */
    private float downX;
    /**
     * 按下点的Y坐标
     */
    private int downY;
    /**
     * 记录是否正在滑动
     */
    private boolean isSilding;
    private boolean isFinish;

    public SildingActivityFinish(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SildingActivityFinish(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mParentView = (ViewGroup) this.getParent();
            viewWidth = this.getWidth();
        }
    }

    /**
     * 设置OnSildingFinishListener，在onSildingFinish()中关闭Actiivty
     *
     * @param onSildingFinishListener
     */
    public void setOnSildingFinishListener(OnSildingFinishListener onSildingFinishListener) {
        this.onSildingFinishListener = onSildingFinishListener;
    }

    /**
     * 设置触摸的View
     *
     * @param touchView
     */
    public void setmTouchView(View touchView) {

        this.touchView = touchView;
        touchView.setOnTouchListener(this);
    }

    public View getTouchView() {
        return touchView;
    }

    /**
     * 向右边滑动出页面
     */
    private void scrollRigth() {
        final int dalta = (viewWidth + mParentView.getScrollX());
        //调用startScroll方法来设置一些滚动的参数，我们在computeScroll方法中来滚动item
        mScroller.startScroll(mParentView.getScrollX(), 0, -dalta + 1, 0, Math.abs(dalta));
        postInvalidate();
    }

    /**
     * 滚动到起始位置
     */
    private void scrollOrigin() {
        int dalta = mParentView.getScrollX();
        mScroller.startScroll(mParentView.getScrollX(), 0, -dalta, 0, Math.abs(dalta));
        postInvalidate();
    }

    /**
     * touch的View是否是AbsListView， 例如ListView, GridView等其子类
     *
     * @return
     */
    private boolean isTouchOnAbsListView() {
        return touchView instanceof AbsListView ? true : false;
    }

    /**
     * touch的view是否是ScrollView或者其子类
     *
     * @return
     */
    private boolean isTouchOnScrollView() {
        return touchView instanceof ScrollView ? true : false;
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = tempX = (int) event.getRawX();
                downY = (int) event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int daltaX = (int) tempX - moveX;
                tempX = moveX;
                if (Math.abs(moveX - downX) > mTouchSlop && Math.abs(event.getRawY() - downY)
                        < mTouchSlop) {
                    isSilding = true;
                    // 若touchView是AbsListView，
                    // 则当手指滑动，取消item的点击事件，不然我们滑动也伴随着item点击事件的发生
                    if (isTouchOnAbsListView()) {
                        MotionEvent cancelEvent = MotionEvent.obtain(event);
                        cancelEvent.setAction(MotionEvent.ACTION_CANCEL
                                | (event.getActionIndex() << event.ACTION_POINTER_INDEX_SHIFT));
                        v.onTouchEvent(cancelEvent);
                    }
                }

                if (moveX - downX >= 0 && isSilding) {
                    mParentView.scrollBy(daltaX, 0);
                    // 屏蔽在滑动过程中ListView ScrollView等自己的滑动事件
                    if (isTouchOnScrollView() || isTouchOnAbsListView()) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isSilding = false;
                if (mParentView.getScaleX() <= -viewWidth / 2) {
                    isSilding = true;
                    scrollRigth();
                } else {
                    scrollOrigin();
                    isSilding = false;
                }
                break;
        }
        // 假如touch的view是AbsListView或者ScrollView 我们处理完上面自己的逻辑之后
        // 再交给AbsListView, ScrollView自己处理其自己的逻辑
        if (isTouchOnScrollView() || isTouchOnAbsListView()) {
            return v.onTouchEvent(event);
        }
        // 其他的情况直接返回true
        return true;
    }

    @Override
    public void computeScroll() {
        // 调用startScroll的时候scroller.computeScrollOffset()返回true，
        if (mScroller.computeScrollOffset()) {
            mParentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished()) {
                if (onSildingFinishListener != null && isFinish) {
                    onSildingFinishListener.onSildingFinish();
                }
            }
        }
    }

    public interface OnSildingFinishListener {
        public void onSildingFinish();
    }
}
