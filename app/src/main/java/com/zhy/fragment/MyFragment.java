package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentMyBinding;

/**
 * 我的界面
 */
public class MyFragment extends BaseViewModelFragment<FragmentMyBinding> {
    public static MyFragment newInstance() {

        Bundle args = new Bundle();

        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
