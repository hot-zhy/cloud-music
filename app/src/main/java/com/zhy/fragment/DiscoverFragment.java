package com.zhy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.DiscoverAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.config.Config;
import com.zhy.manager.MusicListManager;
import com.zhy.model.SearchHistory;
import com.zhy.model.Song;
import com.zhy.model.response.ListResonse;
import com.zhy.util.LiteORMUtil;
import com.zhy.zhycloudmusic.BaseViewModeActivity;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.SimplePlayerActivity;
import com.zhy.zhycloudmusic.databinding.FragmentDiscoverBinding;

import java.util.List;

/**
 * 发现界面
 */
public class DiscoverFragment extends BaseViewModelFragment<FragmentDiscoverBinding> {
    private DiscoverAdapter adapter;

    @Override
    protected void initViews() {
        super.initViews();
        binding.list.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getHostActivity());
        binding.list.setLayoutManager(layoutManager);
        //分割线
        DividerItemDecoration decoration=new DividerItemDecoration(binding.list.getContext(), RecyclerView.VERTICAL);
        binding.list.addItemDecoration(decoration);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //创建适配器
        adapter = new DiscoverAdapter(R.layout.item_song);
        binding.list.setAdapter(adapter);
//        获取数据
        loadData();
//        if(Config.DEBUG){
//            //测试数据
//            LiteORMUtil orm = LiteORMUtil.getInstance(getHostActivity());
//            //创建对象
//            SearchHistory searchHistory = new SearchHistory();
//            //赋值
//            searchHistory.setContent("我是赵晗瑜");
//            searchHistory.setCreatedAt(System.currentTimeMillis());
//            orm.createOrUpdate(searchHistory);
//            //赋值
//            searchHistory.setContent("人生苦短");
//            searchHistory.setCreatedAt(System.currentTimeMillis());
//            orm.createOrUpdate(searchHistory);
//            //查询所有
//            List<SearchHistory> results = orm.querySearchHistory();
//            Log.d("TAG","initDatum: "+results.size());
//            //删除
//            orm.deleteSearchHistory(searchHistory);
//            //查询所有
//            results = orm.querySearchHistory();
//            Log.d("TAG","initDatum: "+results.size());
//        }

    }

    private void loadData() {
        DefaultRepository.getInstance()
                .songs()
                .subscribe(new HttpObserver<ListResonse<Song>>() {
                    @Override
                    public void onSucceeded(ListResonse<Song> data) {
                        adapter.setNewInstance(data.getData().getData());
                    }
                });
    }


    /**
     * 监听点击一首单曲,跳转到简单播放界面
     */
    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //获取当前点击的音乐
                Song data=(Song)adapter.getItem(position);
                //点击一首音乐就把所有的音乐添加到播放列表
                MusicListManager.getInstance(getHostActivity()).setDatum((List<Song>) adapter.getData());
                //播放当前点击的音乐
                MusicListManager.getInstance(getHostActivity()).play(data);

                startMusicPlayerActivity();
            }

        });
    }


    public static DiscoverFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
