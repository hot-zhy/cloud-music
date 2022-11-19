package com.zhy.config;


import com.zhy.zhycloudmusic.BuildConfig;

/**
 * 配置文件
 * <p>
 * 例如：API地址，QQ等第三方服务配置信息等
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
    public static String ENDPOINT = "http://cloud-music-lite-sp.ixuea.com/";

    /**
     * 端点
     * 真机访问电脑
     */
//    public static String ENDPOINT = "http://192.168.50.159/";

    /**
     * 端点
     * 自带模拟器访问电脑
     */
//    public static String ENDPOINT = "http://10.0.2.2:8080/";

    /**
     * 资源端点
     */
    public static String RESOURCE_ENDPOINT = "http://course-music-dev.ixuea.com/%s";
    /**
     * 缓存目录大小
     */
    public static final long NETWORK_CACHE_SIZE=1024*1024*100;
}