package com.zhy.model.event;

/**
 * 播放列表改变了的事件
 */
public class MusicPlayListChangedEvent {
    //删除的音乐索引
    private int position=-1;

    public MusicPlayListChangedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
