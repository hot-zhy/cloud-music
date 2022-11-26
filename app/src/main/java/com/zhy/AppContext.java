package com.zhy;

import android.app.Application;

import com.zhy.manager.MusicListManager;
import com.zhy.model.Session;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.util.LiteORMUtil;
import com.zhy.util.PreferenceUtil;

public class AppContext extends Application {
    private static  AppContext instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        SuperToast.init(getApplicationContext());
    }

    public static AppContext getInstance() {
        return instance;
    }

    public void logout() {
        logoutSlience();
    }

    private void logoutSlience() {
        PreferenceUtil.getInstance(getApplicationContext()).logout();
        //退出以后要销毁播放列表和数据库的实例,在创建时是新的实例，新的实例数据之间有隔离
        MusicListManager.destroy();
        LiteORMUtil.destroy();
    }

    public void onLogin(Session data) {

    }
}
