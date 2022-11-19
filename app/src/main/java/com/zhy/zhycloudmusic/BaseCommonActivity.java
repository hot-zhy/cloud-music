package com.zhy.zhycloudmusic;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 启动界面
 */
public class BaseCommonActivity extends BaseActivity {

    /**
     * 子类启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent=new Intent(getHostActivity(),clazz);
        startActivity(intent);
    }
    /**
     * 启动新界面并关闭当前界面的功能
     * @param clazz
     */
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        Intent intent = new Intent(getHostActivity(),clazz);
        startActivity(intent);
        finish();
    }


    protected BaseCommonActivity getHostActivity(){
        return this;
    }
}
