package com.dalianbobo.lepaandroid.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.bean.ApiToKenBean;
import com.dalianbobo.lepaandroid.uitls.AESCrypt;
import com.dalianbobo.lepaandroid.uitls.MD5;
import com.dalianbobo.lepaandroid.weiget.WaitLoadingDialog;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Fragment 父类 （碎片）
 * Created by Administrator on 2015/11/23 on 16:19.
 * Author Clown Fish
 */
public abstract class BaseFragment extends Fragment {
    protected View mView;
    //自定义加载框
    private WaitLoadingDialog waitDialog;
    private String resultJson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (setContentView() != 0) {
            mView = inflater.inflate(setContentView(), null);
            Log.i("tab","view");
        }
        initViews();
        initListeners();
        initDatas();
        return mView;
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
     * 初始化数据
     */
    public abstract void initDatas();

    /**
     * 提示加载框
     *
     * @param message
     */
    public void showScreen(String message) {
        if (null == waitDialog) {
            waitDialog = new WaitLoadingDialog(getActivity());
        }
        waitDialog.setMessage(message);
        if (null != waitDialog && !getActivity().isFinishing() && !waitDialog.isShowing()) {
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
        Toast.makeText(getActivity(), getResources().getString(msg), Toast.LENGTH_SHORT).show();
    }
    /**
     * 土司短时间提示(用资源文件id)
     *
     * @param
     */
    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 土司长时间提示(用用于字符串)
     *
     * @param msg
     */
    public void longToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 通过类名和布局启动Fragment
     *
     * @param fragment
     */
    protected void startFragment(Fragment fragment, int layout) {
        startFragment(fragment, layout, null);
    }

    /**
     * 通过fragment类名和布局启动Fragmenty，并且含有Bundle数据
     *
     * @param fragment
     * @param bundle
     */
    protected void startFragment(Fragment fragment, int layout, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(layout, fragment);
        transaction.commit();
    }
    /**
     * 通过类名和布局启动Fragment 而且有返回
     *
     * @param fragment
     */
    protected void startFragmentBack(Fragment fragment, int layout) {
        startFragment(fragment, layout, null);
    }

    /**
     * 通过fragment类名和布局启动Fragmenty，并且含有Bundle数据
     *
     * @param fragment
     * @param bundle
     */
    @SuppressLint("transaction")
    protected void startFragmentBack(Fragment fragment, int layout, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack(null);
        transaction.replace(layout, fragment);
        transaction.commit();
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

    /**
     * 关闭系统软键盘
     *
     * @param mEditText
     */
    protected void closeKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 把数据加密
     *
     * @param data
     */
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
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * post请求
     * @param url 请求地址
     * @param params 请求参数
     * @param callBack 请求回调
     */
    public void HttpPost(String url, AjaxParams params, AjaxCallBack<String> callBack){
        FinalHttp finalHttp = new FinalHttp();
//        finalHttp.addHeader("HTTP_USER_AGENT","Android");
        finalHttp.configUserAgent("Android");
        finalHttp.post(url, params, callBack);
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
    public void onDestroy() {
        dissScreen();
    }
}
