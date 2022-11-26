package com.zhy.zhycloudmusic.util;

/**
 * 字符串工具类
 */
public class StringUtil {
    public static boolean isPassword(String password) {
        return password.length()>=6&&password.length()<=15;
    }

    public static boolean isNickName(String nickname) {
        return nickname.length()>=2&&nickname.length()<=10;
    }
}
