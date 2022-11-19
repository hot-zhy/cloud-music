package com.zhy.fragment;

import android.content.Intent;

import com.zhy.zhycloudmusic.BaseCommonActivity;

/**
 * 启动界面
 */
public abstract class BaseCommonFragment extends BaseFragment{
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
        getHostActivity().finish();
    }

//    获取界面方法
    protected BaseCommonActivity getHostActivity(){
        return (BaseCommonActivity) getActivity();

    }
}
