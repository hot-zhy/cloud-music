package com.zhy.zhycloudmusic;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//所有activity父类
public class BaseActivity extends AppCompatActivity {
    /**
     * 找到控件
     */
    protected void initViews(){

    }

    /**
     * 设置数据
     */
    protected void initDatum() {

    }

    /**
     * 设置监听器
     */
    protected void initListeners() {

    }
    /**
     * 在oncreate方法后面调用
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initViews();
        initDatum();
        initListeners();
    }



}
