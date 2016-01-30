package com.dalianbobo.lepaandroid.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalianbobo.lepaandroid.R;
import com.dalianbobo.lepaandroid.activity.SwipeBackActivity;
import com.dalianbobo.lepaandroid.base.BaseFragment;
import com.dalianbobo.lepaandroid.uitls.PictureUtils;
import com.dalianbobo.lepaandroid.uitls.SPUtils;
import com.dalianbobo.lepaandroid.weiget.ActionSheet;
import com.dalianbobo.lepaandroid.weiget.RoundImageViewByXfermode;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * 个人信息页面
 * Created by Administrator on 2015/11/25 on 14:34.
 * Author Clown Fish
 */
public class PersonalMessageFragment extends SwipeBackActivity implements View.OnClickListener, ActionSheet.ActionSheetListener {
    private final String TAG = "PersonalMessageFragment";
    private TextView mTvHeadTitle;
    private LinearLayout mLlHeadBack;
    private static final int PHOTO_REQUEST_CAMERA = 0;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 1;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final String CAMERA = "照相机";
    private static final String LOCAL_PHOTO = "选择本地相册";
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "lepa_photo.jpg";
    private RelativeLayout mRlSelectIoc;//选择头像布局
    private Uri uri;
    private File lepaFile;
    //圆形头像
    private RoundImageViewByXfermode imageViewByXfermode;


    @Override
    protected void initDatas() {

    }

    @Override
    public int setContentView() {
        return R.layout.personalmessage_fragment;
    }

    @Override
    public void initViews() {
        mLlHeadBack = (LinearLayout) findViewById(R.id.ll_back);
        mLlHeadBack.setVisibility(View.VISIBLE);
        mTvHeadTitle = (TextView) findViewById(R.id.tv_head_title);
        mTvHeadTitle.setText("个人信息");
        mRlSelectIoc = (RelativeLayout) findViewById(R.id.rl_select_ico);
        imageViewByXfermode = (RoundImageViewByXfermode) findViewById(R.id.message_ioc);
    }

    @Override
    public void initListeners() {
        mLlHeadBack.setOnClickListener(this);
        mRlSelectIoc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.rl_select_ico:
                showActionSheet();
                break;
        }
    }

    /**
     * 弹出选择头像的布局选择框
     */
    private void showActionSheet() {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("关闭")
                .setOtherButtonTitles(CAMERA, LOCAL_PHOTO)
                .setCancelableOnTouchOutside(true)
                .setListener((ActionSheet.ActionSheetListener) this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {

        if (index == PHOTO_REQUEST_CAMERA) {
            camera();
        } else if (index == PHOTO_REQUEST_GALLERY) {
            gallery();
        }
    }

    /**
     * 获取手机系统相册
     */
    private void gallery() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /**
     * 调用手机照相机
     */
    private void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断SD卡是否可用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                lepaFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                uri = Uri.fromFile(lepaFile);
                crop(uri);

            } else {
                showToast("没有找到SD卡");
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                SPUtils.put(this, "bitmap", bitmap);
                String filePath = PictureUtils.saveToLocal(bitmap);
                this.imageViewByXfermode.setImageBitmap(bitmap);
                showToast("上传成功");
                Log.i(TAG, "图片路径" + filePath);
//               String img = PictureUtils.imgToBase64(filePath,bitmap,"jpg");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] buff = stream.toByteArray();
                String img = new String(Base64.encodeToString(buff, Base64.DEFAULT));//将图片数据使用Base64转换为字符串数据
                uploadFileioc(img);//上传头像
                lepaFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 上传头像
     * @param img
     */
    private void uploadFileioc(String img) {
        AjaxParams params = new AjaxParams();
        params.put("ioc",img);
        AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(String json) {
                super.onSuccess(json);
            }

            @Override
            public void onFailure(Throwable t, String strMsg) {
                super.onFailure(t, strMsg);
            }
        };
        HttpPost("",params,callBack);
    }

    /**
     * 剪切图片
     *
     * @param uri
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
