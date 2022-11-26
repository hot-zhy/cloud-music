package com.zhy.util;

import android.content.Context;

/**
 * Android尺寸相关工具栏
 */
public class DensityUtil {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     *
     * @param context
     * @param data
     * @return
     */
    public static float dip2px(Context context, float data) {
        //获取手机的缩放
        float scale = context.getResources().getDisplayMetrics().density;

        //px=缩放*dp
        return (int) (data * scale + 0.5f);
    }
}
