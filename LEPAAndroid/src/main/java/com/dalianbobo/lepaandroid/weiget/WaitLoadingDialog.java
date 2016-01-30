package com.dalianbobo.lepaandroid.weiget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;

/**
 * 自定义加载框
 * Created by Administrator on 2015/11/23 on 16:26.
 * Author Clown Fish
 */
public class WaitLoadingDialog extends Dialog {
    private LayoutInflater inflater;
    private View contentView;
    private String message;

    public WaitLoadingDialog(Context context, int theme) {
        super(context, R.style.dialog);
    }

    protected WaitLoadingDialog(Context context, boolean cancelable,
                                OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public WaitLoadingDialog(Context context) {
        super(context, R.style.dialog);
        inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(R.layout.wait_dialog_content_view, null);
        setContentView(contentView);
        setCanceledOnTouchOutside(false);
    }

    public void setMessage(String message) {
        this.message = message;
        TextView tvMessage = (TextView) contentView
                .findViewById(R.id.tvDialogMessage);
        tvMessage.setText(this.message);
    }
}
