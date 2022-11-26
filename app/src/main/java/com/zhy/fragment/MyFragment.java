package com.zhy.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.LoginActivity;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentMyBinding;

/**
 * 我的界面
 */
public class MyFragment extends BaseViewModelFragment<FragmentMyBinding> {
    @Override
    protected void initListeners() {
        super.initListeners();
        //用户点击
        View.OnClickListener userClick = view -> {
            startActivity(LoginActivity.class);
        };
        /**
         * 点击图标
         */
        binding.icon.setOnClickListener(userClick);
        /**
         * 点击昵称
         */
        binding.nickname.setOnClickListener(userClick);
        //关于点击
        binding.about.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri=Uri.parse("https://hot-zhy.github.io/");
            intent.setData(uri);
            startActivity(intent);
        });
        //设置点击
        binding.setting.setOnClickListener(v -> {

        });
        //客服点击
        binding.service.setOnClickListener(v -> {
            Intent intent =new Intent(Intent.ACTION_DIAL,Uri.parse("tel:19933153268"));
            startActivity(intent);
        });

    }

        public static MyFragment newInstance () {

            Bundle args = new Bundle();

            MyFragment fragment = new MyFragment();
            fragment.setArguments(args);
            return fragment;
        }

}
