package com.zhy.config;


import com.zhy.zhycloudmusic.BuildConfig;

/**
 * 配置文件
 */
public class Config {
    /**
     * 是否为调试模式
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final long SPLASH_DEFAULT_DELAY_TIME = 2000;
    /**
     * 端点
     */
    public static String ENDPOINT = "http://119.91.198.46:9001/";


    /**
     * 资源端点
     */
    public static String RESOURCE_ENDPOINT = "http://119.91.198.46:9000/%s";
    /**
     * 缓存目录大小
     */
    public static final long NETWORK_CACHE_SIZE=1024*1024*100;
}