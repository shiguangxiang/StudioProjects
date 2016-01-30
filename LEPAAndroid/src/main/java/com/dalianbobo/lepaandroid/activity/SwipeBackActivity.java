
package com.dalianbobo.lepaandroid.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dalianbobo.lepaandroid.app.SwipeBackLayout;
import com.dalianbobo.lepaandroid.app.Utils;
import com.dalianbobo.lepaandroid.base.SwipeBackActivityBase;
import com.dalianbobo.lepaandroid.bean.ApiToKenBean;
import com.dalianbobo.lepaandroid.bean.JsonExceptionBean;
import com.dalianbobo.lepaandroid.uitls.AESCrypt;
import com.dalianbobo.lepaandroid.uitls.MD5;
import com.dalianbobo.lepaandroid.weiget.WaitLoadingDialog;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;


public abstract class SwipeBackActivity extends FragmentActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;
    //自定义加载框
    protected WaitLoadingDialog waitDialog;
    private String resultJson;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "base");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
         receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("receiver", "onReceive");
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d("receiver", "screen on");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d("receiver", "screen off");
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d("receiver", "screen unlock");
                    startActivity(SuoPingActivity.class);
                }
            }
        };
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        if (setContentView() != 0) {
            setContentView(setContentView());
        }
        initViews();
        initListeners();
        initDatas();
        Log.d("receiver", "registerReceiver");
        registerReceiver(receiver,filter);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    /**
     * 设置布局
     */
    public abstract int setContentView();

    /**
     * 初始化控件
     */
    public abstract void initViews();

    /**
     * 初始化点击事件
     */
    public abstract void initListeners();

    /**
     * 提示加载框
     *
     * @param message
     */
    /**
     * 初始化数据
     */
    protected abstract void initDatas();

    public void showScreen(String message) {
        if (null == waitDialog) {
            waitDialog = new WaitLoadingDialog(this);
        }
        waitDialog.setMessage(message);
        if (null != waitDialog && !isFinishing() && !waitDialog.isShowing()) {
            waitDialog.show();
        }
    }

    /**
     * 关闭加载框
     */
    public void dissScreen() {
        if (null != waitDialog && waitDialog.isShowing()) {
            waitDialog.cancel();
        }
    }

    /**
     * 土司短时间提示(用资源文件id)
     *
     * @param
     */
    public void showToast(int msg) {
        Toast.makeText(this, getResources().getString(msg), Toast.LENGTH_SHORT).show();
    }

    /**
     * 土司长时间提示(用用于字符串)
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void startActivity(Class<?> pClass) {
        startActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void startActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 把实体类转化为JSONObject对象
     *
     * @param obj
     * @return
     */
    protected String toJsonObjectOrJsonArray(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    protected String setEncrypt(String data) {
        String eData = null;
        try {
            AESCrypt aesCrypt = new AESCrypt();
            if (data != null) {
                eData = aesCrypt.encrypt(data);
                return eData;
            }
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return eData;

    }

    /**
     * 把数据解密
     *
     * @param data
     */
    protected String setDecrypt(String data) {
        String dData = null;
        try {
            AESCrypt aesCrypt = new AESCrypt();
            if (data != null) {
                dData = aesCrypt.decrypt(data);
                return dData;
            }
            return null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dData;
    }

    /**
     * 关闭系统软键盘
     *
     * @param mEditText
     */
    protected void closeKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 请求回调
     */
    public void HttpPost(String url, AjaxParams params, AjaxCallBack<String> callBack) {
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.post(url, params, callBack);
    }

    /**
     * 解析json code码
     *
     * @param jsonData
     * @return
     */
    public JsonExceptionBean getJsonError(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        String code = jsonObject.getString("code");
        String message = jsonObject.getString("message");
        JsonExceptionBean jsonExceptionBean = new JsonExceptionBean(code, message);
        return jsonExceptionBean;
    }

    /**
     * 验证接口token
     * @param ctrl 控制名
     * @param func 方法名
     * @return
     */
    public String apiToken(String ctrl,String func){
        String api_token = "LePa_2015_"+ctrl+"_"+func;
        return MD5.hashKeyForDisk(api_token);
    }

    @Override
    protected void onDestroy() {
        dissScreen();
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
