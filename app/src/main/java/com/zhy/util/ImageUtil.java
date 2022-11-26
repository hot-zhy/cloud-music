package com.zhy.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.util.ResourceUtil.ResourceUtil;
import com.zhy.zhycloudmusic.R;

public class ImageUtil {

    /**
     * 显示相对路径的图片
     * @param view
     * @param data
     */
    public static void show(ImageView view, String data) {
        if(TextUtils.isEmpty(data)){
            //出错时显示默认图片
            view.setImageResource(R.drawable.placeholder_error);
            return;
        }
        if(data.contains("/files/Music")){
            showLocalImage(view,data);
            return;
        }
//        将图片相对路径转化为绝对路径
        data= ResourceUtil.resourceUri(data);
//        显示绝对路径
        showFull(view,data);
    }

    public static void showLocalImage(ImageView view, String data) {
    }

    /**
     * 显示绝对路径图片
     * @param view
     * @param data
     */
    public static void showFull(ImageView view, String data) {
        RequestOptions options=getCommonRequestOptions();
        Glide.with(view.getContext())
                .load(data)
                .apply(options)
                .into(view);
    }
    private static RequestOptions getCommonRequestOptions(){
        RequestOptions options=new RequestOptions();
//        占位图片
        options.error(R.drawable.placeholder_error);
        return options;
    }

    /**
     * 显示头像
     * @param view
     * @param data
     */
    public static void showAvatar(ImageView view, String data) {
        if(TextUtils.isEmpty(data)){
            //出错时显示默认图片
            view.setImageResource(R.drawable.default_avatar);
            return;
        }
        if(!data.startsWith("http")){
            //相对路径
            //将图片相对路径转化为绝对路径
            data= ResourceUtil.resourceUri(data);
        }
//        显示绝对路径
        showFull(view,data);
    }

}
