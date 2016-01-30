package com.dalianbobo.lepaandroid.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dalianbobo.lepaandroid.bean.JsonExceptionBean;
import com.dalianbobo.lepaandroid.uitls.AESCrypt;
import com.dalianbobo.lepaandroid.weiget.WaitLoadingDialog;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Activity 父类
 * Created by Administrator on 2015/11/23 on 16:18.
 * Author Clown Fish
 */
public abstract class BaseActivity extends Activity {
    //自定义加载框
    protected WaitLoadingDialog waitDialog;
    private String resultJson = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (setContentView() != 0) {
            setContentView(setContentView());
        }
        initViews();
        initListeners();
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
    public void longToast(String msg) {
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
     * @param url 请求地址
     * @param params 请求参数
     * @param callBack 请求回调
     */
    public static void HttpPost(String url,AjaxParams params,AjaxCallBack<Object> callBack){
        FinalHttp finalHttp = new FinalHttp();
        finalHttp.post(url, params, callBack);
    }
    /**
     * 解析json code码
     *
     * @param jsonData
     * @return
     */
    public boolean getJsonError(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        String code = jsonObject.getString("code");
        String message = jsonObject.getString("message");
        if ("success".equals(code)){
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        dissScreen();
        super.onDestroy();
    }
}
