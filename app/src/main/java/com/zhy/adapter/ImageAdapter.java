package com.zhy.adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;
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
    protected void convert(@NonNull BaseViewHolder holder, Object d) {
        ImageView iconView = holder.getView(R.id.icon);
        holder.setGone(R.id.close, true);

        if (d instanceof String) {
            String data = (String) d;
            ImageUtil.show(iconView, data);
        } else if (d instanceof Integer) {
            iconView.setImageResource((int) d);
        } else {
            //选择的图片
            LocalMedia data = (LocalMedia) d;
            ImageUtil.showLocalImage(iconView, data.getCompressPath());

            holder.setGone(R.id.close, false);
        }
    }
}
