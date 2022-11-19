package com.zhy.fragment;

import com.zhy.zhycloudmusic.BaseLogicActivity;
import com.zhy.zhycloudmusic.SimplePlayerActivity;

/**
 * 应用公共逻辑
 */
public abstract class BaseLogicFragment extends BaseCommonFragment{
    public void startMusicPlayerActivity(){
        ((BaseLogicActivity)getHostActivity()).startMusicPlayerActivity();
    }
}
