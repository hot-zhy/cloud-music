package com.zhy.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaParser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewbinding.ViewBinding;

import com.flyco.tablayout.CommonTabLayout;
import com.zhy.AppContext;
import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.util.Constant;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.LoginActivity;
import com.zhy.zhycloudmusic.MainActivity;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.UserDetailActivity;
import com.zhy.zhycloudmusic.databinding.ActivityMainBinding;
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
            if(sp.isLogin()){
                //已经登录了,跳转用户详情
                Intent intent = new Intent(getHostActivity(), UserDetailActivity.class);
                intent.putExtra(Constant.ID,sp.getUserId());
                startActivity(intent);

            }else{
                //没有登录，跳转到登录界面
                startActivity(LoginActivity.class);
            }
        };
//        /**
//         * 点击图标
//         */
//        binding.icon.setOnClickListener(userClick);
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
        //客服点击
        binding.service.setOnClickListener(v -> {
            Intent intent =new Intent(Intent.ACTION_DIAL,Uri.parse("tel:19933153268"));
            startActivity(intent);
        });
        binding.primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
        /**
         * toggle点击
         */
        binding.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //开启
//                    Log.d("toggle","开启");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                }else{
                    //关闭
//                    Log.d("toggle","关闭");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getHostActivity())
                .setTitle("确定要退出登录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppContext.getInstance().logout();
                        showNotLogin();
                    }
                })
                .setNegativeButton("取消",null).show();
    }

    public static MyFragment newInstance () {

            Bundle args = new Bundle();

            MyFragment fragment = new MyFragment();
            fragment.setArguments(args);
            return fragment;
        }

    @Override
    public void onResume() {
        super.onResume();
        showUserInfo();
    }

    private void showUserInfo() {
        if(sp.isLogin()){
//            已经登录了
            loadUserData();
            binding.primary.setVisibility(View.VISIBLE);
        }else{
//            未登录
            showNotLogin();
        }
    }

    /**
     * 加载用户信息，调用获取用户详情信息接口
     */
    private void loadUserData() {
        DefaultRepository.getInstance()
                .userDetail(sp.getUserId())
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        //获取详情信息成功,显示数据
                        showData(data.getData());
                    }
                });
    }

    private void showData(User data) {
//        ImageUtil.showAvatar(binding.icon,data.getIcon());
        binding.nickname.setText(data.getNickname());
    }

    private void showNotLogin() {
//        binding.icon.setImageResource(R.drawable.default_avatar);
        binding.nickname.setText(R.string.login_or_register);
        binding.primary.setVisibility(View.GONE);
    }
}
