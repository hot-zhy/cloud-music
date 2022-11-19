package com.zhy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.zhy.superUI.reflect.ReflectUtil;

public abstract class BaseViewModelFragment<VB extends ViewBinding> extends BaseLogicFragment{
    protected VB binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ReflectUtil.newViewBinding(getLayoutInflater(),getClass());
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return binding.getRoot();
    }

    /**
     * 将binding设置为空，避免内存泄露
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
