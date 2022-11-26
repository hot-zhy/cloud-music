package com.zhy.fragment;

import com.zhy.util.PreferenceUtil;
import com.zhy.zhycloudmusic.BaseLogicActivity;
import com.zhy.zhycloudmusic.SimplePlayerActivity;

/**
 * 应用公共逻辑
 */
public abstract class BaseLogicFragment extends BaseCommonFragment{
    protected PreferenceUtil sp;

    @Override
    protected void initDatum() {
        super.initDatum();
        sp = PreferenceUtil.getInstance(getHostActivity());
    }

    public void startMusicPlayerActivity(){
        ((BaseLogicActivity)getHostActivity()).startMusicPlayerActivity();
    }
}
