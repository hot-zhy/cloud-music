package com.zhy.manager;

import android.media.MediaPlayer;

import com.zhy.model.Song;

/**
 * 为了改变状态
 * 监听播放器内部的状态改变，并将这个状态改变分发出去，外界来监听
 * 播放器监听接口
 */
public interface MusicPlayerListener {
    /**
     * 音乐已经暂停了
     */
    default void onPaused(Song data){

    }
    /**
     * 已经播放了
     */
    default  void onPlaying(Song data){

    }
    /**
     * 播放器准备完毕了
     */
    default void onPrepared(MediaPlayer mp,Song data){

    }
    /**
     * 播放进度回调
     */
    default  void onProgress(Song data){

    }
    /**
     * 播放完了进行回调
     */
    default void onCompletion(MediaPlayer mp){

    }
    /**
     * 播放失败了
     */
    default void onError(Exception exception,Song data){

    }


}
