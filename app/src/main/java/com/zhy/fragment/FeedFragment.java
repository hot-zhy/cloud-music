package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.FeedAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.model.Feed;
import com.zhy.model.response.ListResonse;
import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentFeedBinding;

import java.util.HashMap;

/**
 *
 * 动态界面
 */
public class FeedFragment extends BaseViewModelFragment<FragmentFeedBinding> {
    private FeedAdapter adapter;

    @Override
    protected void initViews() {
        super.initViews();
        binding.list.setHasFixedSize(true);

    }

    @Override
    protected void initDatum() {
        super.initDatum();
        adapter = new FeedAdapter(R.layout.item_feed);
        binding.list.setAdapter(adapter);
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
                        adapter.setNewInstance(data.getData().getData());
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
