package com.zhy.zhycloudmusic;

import androidx.annotation.NonNull;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zhy.adapter.simplePlayerAdapter;
import com.zhy.manager.MusicListManager;
import com.zhy.manager.MusicPlayerListener;
import com.zhy.manager.MusicPlayerManager;
import com.zhy.model.Song;
import com.zhy.util.SuperDateUtil;
import com.zhy.zhycloudmusic.databinding.ActivitySimplePlayerBinding;

/**
 * 简单播放界面
 */
public class SimplePlayerActivity extends BaseTitleActivity<ActivitySimplePlayerBinding> implements MusicPlayerListener, SeekBar.OnSeekBarChangeListener {

    private MusicPlayerManager musicPlayerManager;
    private boolean isSeekTracking;
    private simplePlayerAdapter adapter;

    /**
     * 初始化数据，调用音乐播放管理器的play方法
     */
    @Override
    protected void initDatum() {
        super.initDatum();
        musicPlayerManager=MusicPlayerManager.getInstance(getHostActivity());
        adapter=new simplePlayerAdapter(android.R.layout.simple_list_item_1);
        //设置上去
        binding.list.setAdapter(adapter);
        //提供数据
        adapter.setNewInstance(getMusicListManager().getDatum());

//        //创建音乐播放管理器单例
//        musicPlayerManager=MusicPlayerManager.getInstance(getHostActivity());
//        String songUrl="http://course-music-dev.ixuea.com/assets/s1.mp3";
//        Song song=new Song();
//        song.setTitle("这是一个音乐标题");
//        song.setUri(songUrl);
//        musicPlayerManager.play(songUrl,song);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //item点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //获取点击的音乐
                Song data=(Song)adapter.getItem(position);
                //播放点击的这首音乐
                getMusicListManager().play(data);
            }
        });
        binding.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMusicListManager().play(getMusicListManager().previous());
            }
        });
        binding.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断当前是播放还是暂停
                if(musicPlayerManager.isPlaying()){
                    getMusicListManager().pause();
                }else{
                    getMusicListManager().resume();
                }
            }
        });
        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取下一首音乐
                Song data=getMusicListManager().next();
                getMusicListManager().play(data);
            }
        });
        binding.loopModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更改循环模式
                getMusicListManager().changeLoopModel();
                //显示循环模式
                showLoopModel();
            }
        });
        binding.progress.setOnSeekBarChangeListener(this);

    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        int model=getMusicListManager().getLoopModel();
        switch (model){
            case MusicListManager.MODEL_LOOP_LIST:
                binding.loopModel.setText("列表");
                break;
                case MusicListManager.MODEL_LOOP_RANDOM:
                    binding.loopModel.setText("随机");
                    break;
            default:
                binding.loopModel.setText("单曲");
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
        showMusicPLayStatus();
        showDuration();
        showProgress();
        //显示循环模式
        showLoopModel();
        //选中当前播放的音乐
        scrollPosition();
        //设置播放监听器
        musicPlayerManager.addMusicPlayerListener(this);
    }

    /**
     * 选中当前音乐
     */
    private void scrollPosition() {
        binding.list.post(() -> {
            //获取当前音乐的位置
            int index=getMusicListManager().getDatum().indexOf(getMusicListManager().getData());
            //滚动到这个位置
            binding.list.smoothScrollToPosition(index);
            //高亮
            adapter.setSelectedIndex(index);
        });
    }

    /**
     * 显示初始化数据
     */
    private void showInitData() {
        //获取当前播放的音乐以显示标题
        Song data= getMusicListManager().getData();
        //显示当前歌曲标题
        setTitle(data.getTitle());
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
     * 首次显示
     */

    private void showMusicPLayStatus() {
        if(musicPlayerManager.isPlaying()){
            showPauseStatus();
        }else{
            showPlayStatus();
        }
    }

    /**
     * 进入后台，移出掉音乐播放监听器
     * 界面不可见了
     */
    @Override
    protected void onPause() {
        super.onPause();
        musicPlayerManager.removeMusicPlayerListener(this);
    }

    /**
     * 播放监听器
     * 从暂停到播放
     * @param data
     */
    @Override
    public void onPaused(Song data) {
        showPlayStatus();
    }

    private void showPlayStatus() {
        binding.play.setText("播放");
    }

    /**
     * 从播放到暂停
     * @param data
     */
    @Override
    public void onPlaying(Song data) {
        showPauseStatus();
    }

    private void showPauseStatus() {
        binding.play.setText("暂停");
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

    private void showProgress() {
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
        //选中当前播放的音乐
        scrollPosition();
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
}