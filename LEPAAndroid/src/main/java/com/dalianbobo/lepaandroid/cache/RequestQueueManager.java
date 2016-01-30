package com.dalianbobo.lepaandroid.cache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dalianbobo.lepaandroid.app.LEPAppLication;

public class RequestQueueManager {
	public static RequestQueue queue = Volley.newRequestQueue(LEPAppLication.sInstance);

	public static void addRequest(Request<?> request,Object token){
		if(token!=null){
			request.setTag(token);
		}
		queue.add(request);
//		queue.getCache().
//		queue.stop()
	}

	/**
	 * //将队列里面所有标志位tag的请求移除
	 * @param tag
	 */
	public static void cancelAllRequestByTag(Object tag){
		queue.cancelAll(tag);
	}

}
