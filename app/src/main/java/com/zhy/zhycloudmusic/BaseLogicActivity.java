package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import com.zhy.manager.MusicListManager;

/**
 * 特有逻辑
 */
public class BaseLogicActivity extends BaseCommonActivity {
//    进入音乐播放界面
    public void startMusicPlayerActivity(){
//        简单播放界面
//        startActivity(SimplePlayerActivity.class);
//        黑胶唱片界面
        startActivity(MusicPlayerActivity.class);
    }
//    获取播放列表管理器
    protected MusicListManager getMusicListManager(){
        return MusicListManager.getInstance(getHostActivity());
    }
}
