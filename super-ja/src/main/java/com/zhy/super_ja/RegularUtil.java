package com.zhy.super_ja;

/**
 * 正则表达式工具类
 */
public class RegularUtil {
    public static final String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    /**
     * 用正则表达式判断是否是手机号
     * @param data
     * @return
     */
    public static boolean isPhone(String data) {
        return data.matches(REGEX_PHONE);
    }
}
