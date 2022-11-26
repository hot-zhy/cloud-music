package com.zhy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 偏好设置工具类
 * 是否登录了，是否显示引导界面，用户id
 */
public class PreferenceUtil {
    private static final String LAST_PLAY_SONG_ID="LAST_PLAY_SONG_ID";
    private static final String USER_ID = "user_id";
    private static final String SESSION="session";
    private static PreferenceUtil instance;
    private final Context context;
    private final SharedPreferences preferences;

    private PreferenceUtil(Context context) {
        this.context=context;
        /**
         * 获取系统默认偏好配置，在设置界面保存的值就可以这样获取
         */
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        /**
         * 自定义偏好配置文件的名称
         */
//        SharedPreferences zhy = context.getSharedPreferences("zhy", Context.MODE_PRIVATE);
    }

    public static PreferenceUtil getInstance(Context context) {
        if(instance==null){
            instance= new PreferenceUtil(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 设置当前播放音乐的id
     * @param data
     */
    public void setLastPlaySongId(String data) {
        preferences.edit().putString(getLastPlaySongIdKey(),data).apply();
    }

    /**
     * 获取最后播放的音乐id
     * @return
     */
    public String getLastPlaySongId(){
        return preferences.getString(getLastPlaySongIdKey(),null);
    }

    /**
     * 获取最后播放音乐id key
     * @return
     */
    private String getLastPlaySongIdKey() {
        return String.format("%s%s",getUserId(),LAST_PLAY_SONG_ID);
    }

    /**
     * 获取当前登录用户的id
     * @return
     */
    public String getUserId() {
        return preferences.getString(USER_ID,Constant.ANONYMOUS);
    }

    /**
     * 设置用户id
     * @param data
     */
    public void setUserId(String data) {
        preferences.edit().putString(USER_ID,data).apply();
    }
    public String getSession(){
        return preferences.getString(SESSION,null);
    }
    public void setSession(String data) {
        preferences.edit().putString(SESSION,data).apply();
    }

    /**
     * 判断用户是否登录
     * @return
     */
    public boolean isLogin() {
        return !getUserId().equals(Constant.ANONYMOUS);
    }

    public void logout() {
        delete(USER_ID);
        delete(SESSION);
    }

    private void delete(String data) {
        preferences.edit().remove(data).apply();
    }
}
