package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.UserDetailAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;
import com.zhy.util.Constant;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.databinding.ActivityUserDetailBinding;

import java.util.Arrays;

/**
 * 用户详情页面
 */
public class UserDetailActivity extends BaseTitleActivity<ActivityUserDetailBinding> {
    private String id;
    private User data;
    private UserDetailAdapter adapter;

    /**
     * 加载数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();
//        跳转详情时获取传递的id
        id = getIntent().getStringExtra(Constant.ID);
        adapter = new UserDetailAdapter(getHostActivity(), getSupportFragmentManager(), id);
        //设置适配器
        binding.list.setAdapter(adapter);
        adapter.setDatum(Arrays.asList(0,1));
        //将TabLayout和ViewPager绑定工作
        binding.tab.setupWithViewPager(binding.list);
        loadData();
    }

    private void loadData() {
//        获取用户详情
        DefaultRepository.getInstance().userDetail(id)
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        showData(data.getData());
                    }
                });
    }

    private void showData(User data) {
        this.data=data;
        ImageUtil.showAvatar(binding.icon,data.getIcon());
        binding.nickname.setText(data.getUsername());

    }
    /**
     * 实现ViewPager结构
     */
}