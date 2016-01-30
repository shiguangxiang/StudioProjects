package com.dalianbobo.lepaandroid.okhttp;

import android.content.Context;

import com.facebook.imagepipeline.backends.okhttp.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Clown Fish on 2016/1/15.
 */
public class OkHttpImagePipelineConfigFactory {
    public static ImagePipelineConfig.Builder newBuilder(Context context, OkHttpClient okHttpClient) {
        return ImagePipelineConfig.newBuilder(context)
                .setNetworkFetcher(new OkHttpNetworkFetcher(okHttpClient));
    }
}
