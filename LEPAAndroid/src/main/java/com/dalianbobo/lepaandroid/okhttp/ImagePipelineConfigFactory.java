package com.dalianbobo.lepaandroid.okhttp;

import android.content.Context;

import com.dalianbobo.lepaandroid.cache.fresco.ConfigConstants;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Sets;
import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.backends.okhttp.*;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.squareup.okhttp.OkHttpClient;

/**
 * 应用程序创建ImagePipeline配置
 * Created by Clown Fish on 2016/1/15.
 */
public class ImagePipelineConfigFactory {
    //缓存路径文件夹
    private static final String IMAGE_PIPELINE_CACHE_DIR = "lepai_iamge_cache";

    private static ImagePipelineConfig sImagePipelineConfig;
    private static ImagePipelineConfig sOkHttpImagePipelineConfig;

    /**
     * 使用android创建配置http栈作为网络后端
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context);
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            sImagePipelineConfig = configBuilder.build();
        }
        return sImagePipelineConfig;
    }
    /**
     * 创建使用OkHttp配置的网络支持。
     */
    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            ImagePipelineConfig.Builder configBuilder =
                    com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
            configureCaches(configBuilder, context);
            configureLoggingListeners(configBuilder);
            sOkHttpImagePipelineConfig = configBuilder.build();
        }
        return sOkHttpImagePipelineConfig;
    }

    /**
     * 配置磁盘和内存缓存不超过常见的限制
     *
     * @param configBuilder
     * @param context
     */
    private static void configureCaches(
            ImagePipelineConfig.Builder configBuilder,
            Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        configBuilder
                .setBitmapMemoryCacheParamsSupplier(
                        new Supplier<MemoryCacheParams>() {
                            public MemoryCacheParams get() {
                                return bitmapCacheParams;
                            }
                        })
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(context.getApplicationContext().getCacheDir())
                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)
                                .build());
    }

    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
        configBuilder.setRequestListeners(
                Sets.newHashSet((RequestListener) new RequestLoggingListener()));
    }
}
