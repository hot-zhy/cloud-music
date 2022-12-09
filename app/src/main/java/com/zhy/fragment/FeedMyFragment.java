package com.zhy.fragment;

import android.os.Bundle;

import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.FeedAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.model.Feed;
import com.zhy.model.Meta;
import com.zhy.model.event.FeedChangedEvent;
import com.zhy.model.event.LoginStatusChangedEvent;
import com.zhy.model.response.ListResonse;
import com.zhy.util.Constant;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.databinding.FragmentFeedBinding;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class FeedMyFragment extends BaseViewModelFragment<FragmentFeedBinding>{
    private FeedAdapter adapter;
    private boolean isRefresh;
    private Meta pageMeta;
    private String userId;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();
        binding.list.setHasFixedSize(true);

    }

    /**
     * 登录状态改变了,重新加载一次数据
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginStatusChangedEvent(LoginStatusChangedEvent event){
        loadData(sp.getUserId());
    }
    /**
     * 动态改变了
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FeedChangedEvent(FeedChangedEvent event){
        loadData(sp.getUserId());
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        adapter = new FeedAdapter(R.layout.item_feed);
        binding.list.setAdapter(adapter);
        //请求数据，调用接口
        userId=getArguments().getString(Constant.ID);

        loadData(userId);
    }


    private void loadData(String userId) {
        DefaultRepository.getInstance()
                        .feedSelf(userId)
                                .subscribe(new HttpObserver<ListResonse<Feed>>() {
                                    @Override
                                    public void onSucceeded(ListResonse<Feed> data) {
                                        adapter.setNewInstance(data.getData().getData());
                                    }
                                });
    }



    public static FeedMyFragment newInstance(String userId) {
        
        Bundle args = new Bundle();
        args.putString(Constant.ID,userId);
        FeedMyFragment fragment = new FeedMyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
}
