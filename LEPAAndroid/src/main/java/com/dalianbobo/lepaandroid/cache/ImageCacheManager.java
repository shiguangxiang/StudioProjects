package com.dalianbobo.lepaandroid.cache;

import android.app.ActivityManager;
import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.dalianbobo.lepaandroid.app.LEPAppLication;

public class ImageCacheManager {

	//getRunTime.maxMemory/8
	private static final int MEMORY_CACHE_MAX_SIZE = 1024*1024*((ActivityManager) LEPAppLication.sInstance.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass()/8;
	private static LruImageCache imageCache = new LruImageCache(
			"LepaImageCache",
			1024*1024*30,//外置存储缓存最大值
			MEMORY_CACHE_MAX_SIZE);//内存缓存最大值
	public static ImageLoader mImagerLoader = new ImageLoader(
			LEPAppLication.getInstance().getRequestQueue(),
			imageCache);

	public static ImageContainer loadImage(String url,ImageLoader.ImageListener listener){
		return mImagerLoader.get(url, listener);
	}

}
