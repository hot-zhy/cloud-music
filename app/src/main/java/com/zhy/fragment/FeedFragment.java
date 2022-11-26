package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.Feed;
import com.zhy.model.response.ListResonse;
import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentFeedBinding;

import java.util.HashMap;

/**
 *
 * 动态界面
 */
public class FeedFragment extends BaseViewModelFragment<FragmentFeedBinding> {
    @Override
    protected void initDatum() {
        super.initDatum();
        //请求数据，调用接口
        loadData();
    }

    private void loadData() {
        HashMap<String,String> param = new HashMap<>();
        DefaultRepository.getInstance()
                .feeds(param)
                .subscribe(new HttpObserver<ListResonse<Feed>>() {
                    @Override
                    public void onSucceeded(ListResonse<Feed> data) {
                        //请求动态列表数据成功

                    }
                });
    }

    public static FeedFragment newInstance() {

        Bundle args = new Bundle();

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
