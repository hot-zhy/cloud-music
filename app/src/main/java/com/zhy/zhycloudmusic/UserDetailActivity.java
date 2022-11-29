package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.util.Constant;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.databinding.ActivityUserDetailBinding;

/**
 * 用户详情页面
 */
public class UserDetailActivity extends BaseTitleActivity<ActivityUserDetailBinding> {
    private String id;
    private User data;

    /**
     * 加载数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();
//        跳转详情时获取传递的id
        id = getIntent().getStringExtra(Constant.ID);
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
        binding.nickname.setText(data.getNickname());

    }
    /**
     * 实现ViewPager结构
     */
}