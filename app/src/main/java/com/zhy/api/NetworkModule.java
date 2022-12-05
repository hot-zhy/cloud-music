package com.zhy.api;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.zhy.AppContext;
import com.zhy.config.Config;
import com.zhy.util.JSONUtil;
import com.zhy.util.PreferenceUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        //公共请求参数
        builder.addNetworkInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                //执行每个网络请求时，在这里可以拦截到
                PreferenceUtil sp=PreferenceUtil.getInstance(AppContext.getInstance());
                Request request=chain.request();
                if(sp.isLogin()){
                    //如果登录了，获取token
                    //如果没有登录，服务器端就获取不到这个请求头，网络请求会中断
//                    String session=sp.getSession();
//                    //为网络请求添加请求头
//                    request=request.newBuilder()
//                            .addHeader("Cookie",session)
//                            .build();
                }
                //继续执行网络请求
                return chain.proceed(request);
            }
        });
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
