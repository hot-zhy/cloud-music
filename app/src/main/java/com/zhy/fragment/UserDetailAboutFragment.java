package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.util.Constant;
import com.zhy.zhycloudmusic.databinding.FragmentUserDetailAboutBinding;

public class UserDetailAboutFragment extends BaseViewModelFragment<FragmentUserDetailAboutBinding>{
    public static UserDetailAboutFragment newInstance(String userId) {

        Bundle args = new Bundle();
        args.putString(Constant.ID,userId);

        UserDetailAboutFragment fragment = new UserDetailAboutFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
