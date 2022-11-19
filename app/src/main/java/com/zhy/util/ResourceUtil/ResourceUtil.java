package com.zhy.util.ResourceUtil;

import com.zhy.config.Config;

/**
 * 将相对路径转化为绝对路径
 */
public class ResourceUtil {
    public static String resourceUri(String data) {
        return String.format(Config.RESOURCE_ENDPOINT,data);
    }
}
