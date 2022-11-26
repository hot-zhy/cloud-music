package com.zhy;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zhy.manager.MusicListManager;
import com.zhy.manager.MyActivityManager;
import com.zhy.model.Session;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.util.LiteORMUtil;
import com.zhy.util.PreferenceUtil;

import org.greenrobot.eventbus.EventBus;

public class AppContext extends Application implements Application.ActivityLifecycleCallbacks {
    private static  AppContext instance;
    private MyActivityManager myActivityManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        SuperToast.init(getApplicationContext());
        myActivityManager = MyActivityManager.getInstance();
        //注册界面声明周期监听器
        registerActivityLifecycleCallbacks(this);
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

    //region activity生命周期
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        myActivityManager.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        myActivityManager.remove(activity);
    }
    //endregion
}
