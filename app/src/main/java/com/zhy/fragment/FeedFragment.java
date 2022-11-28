package com.zhy.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.FeedAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.model.Feed;
import com.zhy.model.Meta;
import com.zhy.model.event.FeedChangedEvent;
import com.zhy.model.event.LoginStatusChangedEvent;
import com.zhy.model.response.ListResonse;
import com.zhy.util.Constant;
import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.LoginActivity;
import com.zhy.zhycloudmusic.PublishFeedActivity;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;
import com.zhy.zhycloudmusic.databinding.FragmentFeedBinding;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 *
 * 动态界面
 */
public class FeedFragment extends BaseViewModelFragment<FragmentFeedBinding> {
    private FeedAdapter adapter;
    private boolean isRefresh;
    private Meta pageMeta;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

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

    /**
     * 监听上拉加载和下拉刷新
     */
    @Override
    protected void initListeners() {
        super.initListeners();
//        下拉刷新监听器
        binding.refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
//        上拉加载更多监听器
        binding.refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadmore();
            }
        });
        binding.primary.setOnClickListener(v -> {
           if(sp.isLogin()){
               startActivity(PublishFeedActivity.class);
           }else{
               startActivity(LoginActivity.class);
           }
        });
    }

    /**
     * 上拉加载更多
     */
    private void loadmore() {
        HashMap<String,String> param = new HashMap<>();
        //添加分页参数
        param.put(Constant.PAGE,String.valueOf(Meta.nextPage(pageMeta)));
        DefaultRepository.getInstance()
                .feeds(param)
                .subscribe(new HttpObserver<ListResonse<Feed>>() {
                    @Override
                    public void onSucceeded(ListResonse<Feed> data) {
                        pageMeta=data.getData();
//                        结束刷新
                        binding.refresh.finishRefresh(2000,true,false);
                        binding.refresh.finishLoadMore(2000,true,pageMeta.getNext()==null);
                        if(isRefresh){
                            isRefresh=false;
//                            下拉刷新
                            adapter.setNewInstance(data.getData().getData());
                        }else{
                            adapter.addData(data.getData().getData());
                        }
                    }
                });
    }

    /**
     * 下拉刷新，使用获得第一页的数据
     */
    private void loadData() {
        //下拉刷新
        isRefresh=true;
        //使用获取第一页数据
        pageMeta=null;
        loadmore();
    }


    /**
     * 登录状态改变了,重新加载一次数据
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginStatusChangedEvent(LoginStatusChangedEvent event){
        loadData();
    }
    /**
     * 动态改变了
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FeedChangedEvent(FeedChangedEvent event){
        loadData();
    }


    public static FeedFragment newInstance() {

        Bundle args = new Bundle();

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
