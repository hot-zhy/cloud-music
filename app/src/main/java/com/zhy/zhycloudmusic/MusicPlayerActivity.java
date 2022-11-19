package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zhy.manager.MusicListManager;
import com.zhy.manager.MusicPlayerListener;
import com.zhy.manager.MusicPlayerManager;
import com.zhy.model.Song;
import com.zhy.super_ja.SuperDateUtil;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.databinding.ActivityMusicPlayerBinding;

/**
 * 黑胶唱片界面
 */
public class MusicPlayerActivity extends BaseTitleActivity<ActivityMusicPlayerBinding> implements SeekBar.OnSeekBarChangeListener, MusicPlayerListener {
    private MusicPlayerManager musicPlayerManager;
    private boolean isSeekTracking;

    @Override
    protected void initViews() {
        super.initViews();
//        设置沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        musicPlayerManager=MusicPlayerManager.getInstance(getHostActivity());
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        /**
         * 循环模式点击事件
         */
        binding.loopModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更改循环模式
                getMusicListManager().changeLoopModel();
                //显示循环模式
                showLoopModel();
            }
        });
        /**
         * 上一曲点击事件
         */
        binding.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMusicListManager().play(getMusicListManager().previous());
            }
        });
        /**
         * 播放按钮点击事件
         */
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(musicPlayerManager.isPlaying()){
                    getMusicListManager().pause();
                }else{
                    getMusicListManager().resume();
                }
            }
        });
        /**
         * 下一曲点击事件
         */
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Song data=getMusicListManager().next();
                getMusicListManager().play(data);
            }
        });
        /**
         * 播放列表按钮点击事件
         */
        binding.listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        设置拖拽进度空间的监听器
        binding.progress.setOnSeekBarChangeListener(this);
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        int model=getMusicListManager().getLoopModel();
        switch (model){
            case MusicListManager.MODEL_LOOP_LIST:
                binding.loopModel.setImageResource(R.drawable.music_repeat_list);
                break;
            case MusicListManager.MODEL_LOOP_RANDOM:
                binding.loopModel.setImageResource(R.drawable.music_repeat_random);
                break;
            default:
                binding.loopModel.setImageResource(R.drawable.music_repeat_one);
                break;
        }
    }

    /**
     * 初始化时会执行，切换到后台再回来也能执行
     * 界面显示了
     */
    @Override
    protected void onResume() {
        super.onResume();
        //显示初始化数据
        showInitData();
        //显示音乐播放状态
        showMusicPLayStatus();
        //显示音乐总时长
        showDuration();
        //显示音乐进度
        showProgress();
        //显示循环模式
        showLoopModel();
        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);
    }

    /**
     * 显示初始化数据
     */
    private void showInitData() {
        //获取当前播放的音乐以显示标题
        Song data= getMusicListManager().getData();
        //显示当前歌曲标题
        setTitle(data.getTitle());
        /**
         * 显示歌手信息
         */
        toolbar.setSubtitle(data.getSinger().getNickname());
        /**
         * 显示背景
         */
        ImageUtil.show(binding.record.binding.icon,data.getIcon());
    }


    /**
     * 首次显示播放状态
     */

    private void showMusicPLayStatus() {
        if(musicPlayerManager.isPlaying()){
            showPauseStatus();
        }else{
            showPlayStatus();
        }
    }
    /**
     * 显示播放状态
     */
    private void showPlayStatus() {
        binding.play.setImageResource(R.drawable.music_play);
    }

    /**
     * 显示暂停状态
     */
    private void showPauseStatus() {
        binding.play.setImageResource(R.drawable.music_pause);
    }
    /**
     * 显示音乐总时长
     */
    private void showDuration() {
        int duration=getMusicListManager().getData().getDuration();
        binding.end.setText(SuperDateUtil.ms2ms(duration));
        binding.progress.setMax(duration);
    }
    /**
     * 显示音乐播放进度
     */
    private void showProgress() {
        binding.record.incrementRotate();
        //如果当前在拖拽，不显示当前进度
        if(isSeekTracking){
            return;
        }
        //音乐的播放进度
        int progress=getMusicListManager().getData().getProgress();
//        格式化进度
        binding.start.setText(SuperDateUtil.ms2ms(progress));
        //将进度设置到播放条上去
        binding.progress.setProgress(progress);
    }


    //region 进度条监听器

    /**
     * 进度改变了
     * @param seekBar
     * @param progress   当前进度
     * @param fromUser  是否是用户拖拽的
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            getMusicListManager().seekTo(progress);
        }
    }

    /**
     * 开始拖拽进度时
     * @param seekBar
     */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeekTracking=true;
    }


    /**
     * 停止拖拽
     * @param seekBar
     */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeekTracking=false;
    }

    //endregion


    //region 播放监听器
    /**
     * 播放监听器
     * 从暂停到播放
     * @param data
     */
    @Override
    public void onPaused(Song data) {
        showPlayStatus();
    }
    /**
     * 从播放到暂停
     * @param data
     */
    @Override
    public void onPlaying(Song data) {
        showPauseStatus();
    }
    /**
     * 音乐播放中
     * @param data
     */
    @Override
    public void onProgress(Song data) {
        //显示音乐进度
        showProgress();
    }
    /**
     * 显示总进度
     * @param mp
     * @param data
     */
    @Override
    public void onPrepared(MediaPlayer mp, Song data) {
        //显示初始化数据
        showInitData();
        //显示总时长
        showDuration();
    }
    //endregion

}