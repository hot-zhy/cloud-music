package com.zhy.manager;

import android.app.Activity;

import com.zhy.zhycloudmusic.LoginActivity;
import com.zhy.zhycloudmusic.RegisterActivity;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 界面管理器，保存当前应用所有开启的界面
 * 当关闭多个界面的时候，可以通过它来找到需要关闭的多个界面
 */
public class MyActivityManager {
    private static MyActivityManager instance;

    public static MyActivityManager getInstance() {
        if(instance==null){
            instance=new MyActivityManager();
        }
        return instance;
    }
//    定义列表保存所有界面
//    Set中不能保存重复元素
//    Set是无序的
    private static Set<Activity> activities=new LinkedHashSet<>();

    /**
     * 添加界面
     * @param data
     */
    public void add(Activity data) {
        activities.add(data);
    }

    public void remove(Activity data) {
        activities.remove(data);
    }

    public void finishAllLogin() {
        LinkedList<Activity> list = new LinkedList<>(MyActivityManager.activities);
        //倒着遍历关闭
        Iterator<Activity> iterator = list.descendingIterator();
        while(iterator.hasNext()){
            Activity activity=iterator.next();
            if(activity instanceof LoginActivity||activity instanceof RegisterActivity){
                activity.finish();
            }
        }
    }
}
