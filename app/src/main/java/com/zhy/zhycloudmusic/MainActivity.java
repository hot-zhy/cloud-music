package com.zhy.zhycloudmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.zhy.adapter.MainAdapter;
import com.zhy.adapter.OnPageChangeListenerAdapter;
import com.zhy.fragment.DiscoverFragment;
import com.zhy.fragment.MyFragment;
import com.zhy.model.ui.TabEntity;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.zhycloudmusic.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseTitleActivity<ActivityMainBinding> {
    //底部指示器的文本和图标
    private static final int[] indicatorTitles=new int[]{
            R.string.discovery,
            R.string.feed,
            R.string.me
    };
    private static final int[] indicatorIcons=new int[]{
            R.drawable.discovery,
            R.drawable.feed,
            R.drawable.me
    };
    private static final int[] indicatorSelectedIcons=new int[]{
            R.drawable.discovery_selected,
            R.drawable.feed_selected,
            R.drawable.me_selected
    };
    private MainAdapter adapter;

    protected boolean isShowBackMenu(){
        return false;
    }
    @Override
    protected void initViews() {
        super.initViews();
        //缓存页面数量
        binding.list.setOffscreenPageLimit(3);
//        指示器
        ArrayList<CustomTabEntity> tabs=new ArrayList<>();
        for (int i = 0; i < indicatorIcons.length; i++) {
            tabs.add(new TabEntity(
                    getString(indicatorTitles[i]),
                    indicatorSelectedIcons[i],
                    indicatorIcons[i]
            ));
        }
        binding.indicator.setTabData(tabs);

        //动态tab显示消息提醒
        binding.indicator.showDot(0);
    }


    /**
     * 初始化数据
     */
    @Override
    protected void initDatum() {
        super.initDatum();
        //创建adapter
        adapter = new MainAdapter(this, getSupportFragmentManager());

        //设置到控件
        binding.list.setAdapter(adapter);

        adapter.setDatum(Arrays.asList(
                0, 1, 2
        ));
    }

    /**
     * 设置滑动联动效果
     */
    @Override
    protected void initListeners() {
        super.initListeners();
        binding.indicator.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                binding.list.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
//        实现手部滑动时，底部toolbar也变化
        binding.list.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            /**
             * 滚动完成
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                binding.indicator.setCurrentTab(position);
            }
        });
//        音乐迷你控制器点击事件，点击进入播放界面
        binding.musicControl.setOnClickListener(v -> startMusicPlayerActivity());
    }


    @Override
    protected void onResume() {
        super.onResume();
//        播放列表没有的时候隐藏这个迷你音乐
        if(getMusicListManager().getDatum().size()>0){
            binding.musicControl.onResume();
            binding.musicControl.setVisibility(View.VISIBLE);
        }else{
            binding.musicControl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.musicControl.onPause();
    }
}