package com.dalianbobo.lepaandroid.cache;

import java.io.File;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.dalianbobo.lepaandroid.app.LEPAppLication;
import com.dalianbobo.lepaandroid.cache.libcore.io.DiskLruCache;


@SuppressLint("NewApi")
public class LruImageCache extends LruCache<String, Bitmap> implements ImageCache {

	private static String CACHE_DIR_NAME;
	private static DiskLruCache mDiskLruCache;

	@Override
	protected int sizeOf(String key, Bitmap bitmap) {
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR1){//SDK>=3.1使用的方法不一样
			/**
			 * SDK的兼容性开发：
			 * 我们经常碰到高版本使用的方法和低版本使用的方法不一样。
			 * 比如：bitmap.getByteCount()在12的api才能调用得到
			 * 就应该判断版本号选用不同的方法
			 * 如果检测编译都不能通过，则：
			 * 	@SuppressLint("NewApi")
			 * 	@disable
			 */
			return bitmap.getByteCount();
		}else{
			return bitmap.getRowBytes()*bitmap.getHeight();
		}
	}

	public LruImageCache(String cache_dir_name, int discMaxSize,int memoryMaxSize) {
		super(memoryMaxSize);
		try {
			CACHE_DIR_NAME = cache_dir_name;
			mDiskLruCache = DiskLruCache.open(
					getDiskCacheDir(LEPAppLication.sInstance,CACHE_DIR_NAME),
					getAppVersion(LEPAppLication.sInstance), //版本号，跟app的版本号一直
					1, //缓存一个文件是否允许同一个文件缓存两个
					discMaxSize);//缓存的最大限制
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getAppVersion(Context context) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	private File getDiskCacheDir(Context context, String appLabel) {
		String path;///mnt/sdcard/cache/appLabel
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//sd卡存储（/mnt/sdcard/cache）
			path = context.getExternalCacheDir().getPath();
		}else{
			//没有SD卡，缓存到系统存储
			path = context.getCacheDir().getPath();
		}
		return new File(path+File.separator+appLabel);
	}

	@Override
	public Bitmap getBitmap(String str) {//str--url
		System.out.println("getBitmap--str:"+str);
		Bitmap bitmap = null;
		bitmap = get(str);
		String key = generateKey(str);
		//从lrucache内存里面拿图片
		try {
			if(bitmap!=null){
				System.out.println("拿到内存缓存");
				//内存里面有图片
				return bitmap;
			}
			System.out.println("没有内存缓存");
			// 当请求网络图片之前volley会回调该方法判断是否有缓存，有就直接返回，没有就调用网络
			//1.去内存里面拿图片
			//2.磁盘拿图片
			DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
			if(snapshot!=null){
				InputStream is = snapshot.getInputStream(0);
				bitmap = BitmapFactory.decodeStream(is);
				if(bitmap!=null){
					put(str, bitmap);//缓存到内存里面
					System.out.println("拿到外部缓存"+bitmap);
				}
			}
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	@Override
	public void putBitmap(String str, Bitmap bitmap) {
		System.out.println("putBitmap--str:"+str);
		put(str, bitmap);
		String key = generateKey(str);
		try {
			//缓存到lruCache内部缓存
			System.out.println("保存到内存缓存");
			//当本地没有缓存，请求完网络后，将图片缓存起来
			System.out.println("保存到外部缓存");
			if(mDiskLruCache.get(key)==null){
				DiskLruCache.Editor edit = mDiskLruCache.edit(key);
				OutputStream os = edit.newOutputStream(0);
				boolean compress = bitmap.compress(CompressFormat.JPEG, 100, os);
				if(compress){
					edit.commit();
				}else{
					edit.abort();
				}
				mDiskLruCache.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String generateKey(String url){
		String cacheKey = null;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(url.getBytes());
			byte[] digest = mDigest.digest();
			//byte转成16进制的字符串(效果：根据url，加密，后生成一串固定长度的字符串)
			cacheKey = bytesToHexString(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheKey;

	}

	//byte转成16进制的字符串
	private static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hexString = Integer.toHexString(0xFF & bytes[i]);
			if(hexString.length()==1){
				sb.append('0');
			}
			sb.append(hexString);
		}
		return sb.toString();
	}

}
