package com.zhy.zhycloudmusic;

import android.os.Bundle;

import androidx.viewbinding.ViewBinding;

import com.zhy.superUI.reflect.ReflectUtil;
import com.zhy.zhycloudmusic.databinding.ActivityStartBinding;

public class BaseViewModeActivity<VB extends ViewBinding> extends  BaseLogicActivity{
    protected VB binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        反射机制
        binding=ReflectUtil.newViewBinding(getLayoutInflater(),this.getClass());
        setContentView(binding.getRoot());

    }
}
