package com.zhy.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * 图片适配器
 */
public class ImageAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public ImageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Object o) {

    }
}
