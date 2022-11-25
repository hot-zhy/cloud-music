package com.zhy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * 所有BottomSheetDialogFragment的父类
 */
public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    /**
     * 将Fragment中的方法划分为三个部分，便于管理
     */
    /**
     * 找控件
     */
    protected void initViews(){

    }
    /**
     * 设置数据
     */
    protected void initDatum(){

    }
    /**
     * 绑定监听器
     */
    protected void initListeners(){

    }
    /**
     * 返回显示的控件
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=getLayoutView(inflater,container,savedInstanceState);
        return view;
    }

    /**
     * 子类进行重写的方法
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) ;

    /**
     * view创建之后调用
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initDatum();
        initListeners();
    }
}
