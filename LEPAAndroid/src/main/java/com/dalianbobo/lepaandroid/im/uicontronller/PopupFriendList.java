package com.dalianbobo.lepaandroid.im.uicontronller;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dalianbobo.lepaandroid.R;

/**
 *  圆形的popupwindow，用来显示前几个好友<p/>
 * Created by qin on 2016/1/27.
 */
public class PopupFriendList {

    private Context context;
    private int mStartX = 0;
    private int mStartY = 0;

    public PopupFriendList(Context context) {
        super();
        this.context = context;
    }

    /**
     * 设置展示多少个好友
     * @param number 展示好友数量
     * */
    public void setRequstFriendsNumber(int number) {

    }

    /**
     * 设置是否展示名字
     * @param enable 如果你传入true，那么会显示好友的名字；另外的，传入false则不显示名字
     * */
    public void setFriendsNameEnable(boolean enable) {
        if (enable) {

        } else {

        }
    }

    /**
     * 调用该方法来展示圆形popupwindow
     * */
    public void show() {
        final View popupFriendsView = LayoutInflater.from(context).inflate(R.layout.popup_friend_list, null, false);
        final View popup_friend_parent = popupFriendsView.findViewById(R.id.popup_friend_parent);
        final View popup_Friends_view = popupFriendsView.findViewById(R.id.popup_Friends_view);
        popup_Friends_view.setFocusable(true);
        final PopupWindow popupWindow = new PopupWindow(
                popupFriendsView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                true
        );
        popup_friend_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getX();
                        mStartY = (int) event.getY();
                        int FriendViewLeft = popup_Friends_view.getLeft();
                        int FriendViewRight = popup_Friends_view.getRight();
                        int FriendViewTop = popup_Friends_view.getTop();
                        int FriendViewBottom = popup_Friends_view.getBottom();
                        if (mStartX < FriendViewLeft) {
                            popupWindow.dismiss();
                        } else if (mStartX > FriendViewRight) {
                            popupWindow.dismiss();
                        } else if (mStartX > FriendViewLeft && mStartX < FriendViewRight) {
                            if (mStartY < FriendViewTop || mStartY > FriendViewBottom) {
                                popupWindow.dismiss();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
        popupWindow.showAtLocation(popupFriendsView, Gravity.FILL, 0, 0);
    }
}
