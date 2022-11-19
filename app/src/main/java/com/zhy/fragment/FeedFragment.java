package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentFeedBinding;

/**
 *
 * 动态界面
 */
public class FeedFragment extends BaseViewModelFragment<FragmentFeedBinding> {
    public static FeedFragment newInstance() {

        Bundle args = new Bundle();

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
