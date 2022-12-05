package com.zhy.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhy.manager.MusicListManager;
import com.zhy.manager.MusicPlayerListener;
import com.zhy.manager.MusicPlayerManager;
import com.zhy.model.Song;
import com.zhy.util.Constant;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.databinding.ViewSmallMusicControlBinding;

/**
 * 迷你音乐控制器view
 */
public class SmallMusicControlView extends LinearLayout implements MusicPlayerListener {
//    旋转角度
    private float recordRotation;
    private ViewSmallMusicControlBinding binding;
    private MusicPlayerManager musicPlayerManager;

    public SmallMusicControlView(Context context) {
        super(context);
        init();
    }


    public SmallMusicControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmallMusicControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SmallMusicControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        binding = ViewSmallMusicControlBinding.inflate(LayoutInflater.from(getContext()),
                this, true);
        initDatum();
    }

    private void initDatum() {
        musicPlayerManager = MusicPlayerManager.getInstance(getContext());
    }


    /**
     * 主界面可见了
     */
    public void onResume() {
//        显示初始化数据
        showInitData();
//        显示播放进度
        showProgress();
//        设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);
    }

    private void showProgress() {
//        旋转
        incrementRotate();
    }

    private void incrementRotate() {
//        判断旋转角度边界
        if(recordRotation>360){
            recordRotation=0;
        }
        recordRotation+= Constant.ROTATION_PER;
//        旋转
        binding.content.setRotation(recordRotation);

    }

    private void showInitData() {
//        获取当前播放的音乐
        Song data= MusicListManager.getInstance(getContext()).getData();
        if(data==null){
            return;
        }
        ImageUtil.show(binding.icon,data.getPic());
    }

    public void onPause() {
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    //音乐监听接口实现，音乐准备播放了就初始化数据
    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        showInitData();
    }

    //音乐在播放中就显示进度，旋转
    @Override
    public void onProgress(Song data) {
        showProgress();
    }
}
