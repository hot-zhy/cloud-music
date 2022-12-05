package com.zhy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import com.zhy.superUI.reflect.toast.SuperToast;
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
        //有顶部菜单menu
        setHasOptionsMenu(true);
        binding.list.setHasFixedSize(true);

        /**
         * 线性布局管理器
         */
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



    }

    private void loadData() {
        DefaultRepository.getInstance()
                .songs()
                .subscribe(new HttpObserver<ListResonse<Song>>() {
                    @Override
                    public void onSucceeded(ListResonse<Song> data) {
                        adapter.setNewInstance(data.getData().getData());
//                        Log.d("TAG","onSucceeded:"+data.getData().getData().get(0).getName());
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


    /**
     * 创建toolbar上的更多按钮
     * @param menu
     * @return
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.discovery,menu);
    }


//    监听toolbar按钮点击事件

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add){
            SuperToast.show("点击了添加");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static DiscoverFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiscoverFragment fragment = new DiscoverFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
