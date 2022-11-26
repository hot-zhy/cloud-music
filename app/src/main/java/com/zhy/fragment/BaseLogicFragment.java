package com.zhy.fragment;

import com.zhy.util.PreferenceUtil;
import com.zhy.zhycloudmusic.BaseLogicActivity;
import com.zhy.zhycloudmusic.SimplePlayerActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 应用公共逻辑
 */
public abstract class BaseLogicFragment extends BaseCommonFragment{
    protected PreferenceUtil sp;

    @Override
    protected void initDatum() {
        super.initDatum();
        if(isRegisterEventBus()){
            EventBus.getDefault().register(this);
        }
        sp = PreferenceUtil.getInstance(getHostActivity());
    }

    protected boolean isRegisterEventBus(){
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegisterEventBus()){
            EventBus.getDefault().unregister(this);
        }
    }

    public void startMusicPlayerActivity(){
        ((BaseLogicActivity)getHostActivity()).startMusicPlayerActivity();
    }
}
