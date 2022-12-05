package com.zhy.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;
import com.zhy.util.Constant;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.databinding.FragmentUserDetailAboutBinding;

public class UserDetailAboutFragment extends BaseViewModelFragment<FragmentUserDetailAboutBinding>{
    private String userId;

    public static UserDetailAboutFragment newInstance(String userId) {

        Bundle args = new Bundle();
        args.putString(Constant.ID,userId);

        UserDetailAboutFragment fragment = new UserDetailAboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        userId=getArguments().getString(Constant.ID);
        loadData();
    }

    private void loadData() {
//        获取用户详情
        DefaultRepository.getInstance().userDetail(userId)
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        showData(data.getData());
                    }
                });
    }

    @SuppressLint("StringFormatInvalid")
    private void showData(User data) {
        binding.nickname.setText(getResources().getString(R.string.nickname_value,data.getUsername()));
        binding.gender.setText(getResources().getString(R.string.gender_value,data.getGenderFormat()));
        binding.birthday.setText(getResources().getString(R.string.birthday_value,data.getBirthday()));
        binding.detail.setText(getResources().getString(R.string.detail_value,data.getDetail()));

    }
}
