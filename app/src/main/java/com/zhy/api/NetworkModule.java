package com.zhy.api;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.zhy.AppContext;
import com.zhy.config.Config;
import com.zhy.util.JSONUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {
    /**
     * 提供OkHttpClient
     */
    public static OkHttpClient providerOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Cache cache = new Cache(AppContext.getInstance().getCacheDir(), Config.NETWORK_CACHE_SIZE);
        builder.cache(cache);
//        连接超时时间
        builder.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
        if (Config.DEBUG) {
//            创建日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

            //添加chucker实现应用内显示网络请求信息拦截器
            builder.addInterceptor(new ChuckerInterceptor.Builder(AppContext.getInstance()).build());
        }
        return builder.build();

    }

    public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().client(okHttpClient).baseUrl(Config.ENDPOINT).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(JSONUtil.createGson())).build();
    }
}
