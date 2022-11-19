package com.zhy.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.zhy.model.Song;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 单例设计模式
 */

/**
 * 音乐播放管理器
 */
public class MusicPlayerManager implements MediaPlayer.OnCompletionListener {
    private static final int MESSAGE_PROGRESS = 100;
    private static final long DEFAULT_PUBLISH_PROGRESS_TIME=19;
    private static MusicPlayerManager instance;
    private final Context context;
    private final MediaPlayer player;
    private String uri;
    private Song data;
    /**
     * 播放监听器列表
     */
    private CopyOnWriteArrayList<MusicPlayerListener> listeners=new CopyOnWriteArrayList<>();
    private TimerTask timerTask;
    private Timer timer;
    private Handler handler=new Handler(Looper.getMainLooper()){
        /**
         * 主线程切换到子线程
          */
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_PROGRESS:
                    //播放进度回调
                    //将进度设置到音乐对象
                    data.setProgress(player.getCurrentPosition());
                    //回调监听
                    for (MusicPlayerListener it:listeners){
                        it.onProgress(data);

                    }
                    break;
            }
        }
    };

    /**
     * 构造方法私有化
     * 不希望外界创建它，实例化它
     * @param context
     */
    private MusicPlayerManager(Context context){
        this.context=context;
        //初始化播放器
        player =new MediaPlayer();
        //设置播放器准备完毕监听器
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //获取音乐的总时长
                //将总进度保存到音乐对象
                data.setDuration(mediaPlayer.getDuration());
                for(MusicPlayerListener it:listeners){
                    it.onPrepared(mediaPlayer,data);
                }
            }
        });
        //设置播放完毕监听器
        player.setOnCompletionListener(this);
    }

    /**
     * 返回单例
     * @param context
     * @return
     */
    public static MusicPlayerManager getInstance(Context context){
        if(instance==null){
            //创建音乐播放管理器单例
            //全局context
            instance=new MusicPlayerManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 真正用来播放音乐的方法，使用系统播放器来播放
     * @param uri
     * @param data
     */
    public void play(String uri, Song data) {
        //保存信息
        this.uri=uri;
        this.data=data;
        //释放播放器
        player.reset();
        playNow();

    }

    private void playNow() {
        try {
            if (uri.startsWith("content://")) {
                //内容提供者格式

                //本地音乐
                //uri示例：content://media/external/audio/media/23
                player.setDataSource(context, Uri.parse(uri));
            } else {
                //设置数据源   player =new MediaPlayer();
                player.setDataSource(uri);
            }

            //同步准备播放
            player.prepare();

            //开始播放
            player.start();
            /**
             * 回调监听器
             */
            publishPlayingStatus();

            startPublishProgress();
        } catch (IOException e) {
            //TODO 播放错误处理

        }

    }

    /**
     * 回调外部播放状态，发布外界播放中状态
     */
    private void publishPlayingStatus() {
        for(MusicPlayerListener it:listeners){
            it.onPlaying(data);
        }
    }

    /**
     * 判断是播放还是暂停
     * @return
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }

    /**
     * 暂停
     */
    public void pause() {
        if(isPlaying()){
            player.pause();
            for (MusicPlayerListener it:listeners){
                //外界即可收到监听器的传递的状态改变
                //接口中有这个函数，simplPlayActivity中有对这个接口的实现，当调用这个接口时，就知道此时的状态改变了
                it.onPaused(data);
            }
            stopPublishProgress();
        }
    }

    private void stopPublishProgress() {
        //停止定时器任务
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
        //停止定时器
        if(timer!=null){
            timer.cancel();
        }
    }

    /**
     * 继续播放
     */
    public void resume() {
        //如果没有播放才继续播放
        if(!isPlaying()){
            resumeNow();
        }
    }

    private void resumeNow() {
        player.start();
        //回调监听器
        publishPlayingStatus();

        startPublishProgress();
    }

    /**
     * 添加音乐持续播放监听器
     */
    private void startPublishProgress() {
        if(isEmptyListeners()){
            //没有进度回调就不启动
            return;
        }
        /**
         * 没有播放就不用启动
         */
        if(!isPlaying()){
            return;
        }
        if(timerTask!=null){
            //已经启动了
            return;
        }
        timerTask=new TimerTask(){


            @Override
            public void run() {
                //如果没有监听器了就停止定时器
                if(isEmptyListeners()){
                    stopPublishProgress();
                    return;
                }

                handler.obtainMessage(MESSAGE_PROGRESS).sendToTarget();

            }
        };

        //创建一个定时器
        timer=new Timer();

        //启动一个持续的任务,每16ms执行一次,是一帧的时间
        timer.schedule(timerTask,0,DEFAULT_PUBLISH_PROGRESS_TIME);

    }

    /**
     * 是否没有进度监听器
     * @return
     */
    private boolean isEmptyListeners() {
        return listeners.size()==0;
    }


    /**
     * 添加音乐播放监听器
     */
    public void addMusicPlayerListener(MusicPlayerListener listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
            startPublishProgress();
        }
    }
    /**
     * 移出音乐播放监听器
     */
    public void removeMusicPlayerListener(MusicPlayerListener listener){
        listeners.remove(listener);
    }


    /**
     * 跳转到指定位置播放
     * @param data
     */
    public void seekTo(int data) {
        //跳转到指定位置播放
        player.seekTo(data);
    }

    /**
     * 设置单曲循环
     * @param data
     */
    public void setLooping(boolean data) {
        player.setLooping(data);
    }

    /**
     * 播放完毕了
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        for(MusicPlayerListener it:listeners){
            it.onCompletion(mediaPlayer);
        }
    }
}
