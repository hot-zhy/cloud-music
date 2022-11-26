package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import com.zhy.manager.MusicListManager;
import com.zhy.util.PreferenceUtil;

/**
 * 特有逻辑
 */
public class BaseLogicActivity extends BaseCommonActivity {
    protected PreferenceUtil sp;

    @Override
    protected void initDatum() {
        super.initDatum();
        //初始化偏好配置初始化，子类可不用初始化，直接调用
        sp = PreferenceUtil.getInstance(getApplicationContext());
    }

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
