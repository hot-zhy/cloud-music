package com.zhy.adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.R;

/**
 * 图片适配器
 */
public class ImageAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public ImageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Object o) {

        ImageView iconView = holder.getView(R.id.icon);
        if(o instanceof String){
            String data=(String) o;
            ImageUtil.show(iconView,data);
        }
    }
}
