package com.zhy;

import android.app.Application;

import com.zhy.superUI.reflect.toast.SuperToast;

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

    }
}
