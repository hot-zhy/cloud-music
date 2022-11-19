package com.zhy.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.model.Song;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.R;

/**
 * 发现界面单曲适配器
 */
public class DiscoverAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    public DiscoverAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Song data) {
        ImageUtil.show(holder.getView(R.id.icon),data.getIcon());
        holder.setText(R.id.title,data.getTitle());
        holder.setText(R.id.info,String.format("%s-%s",data.getSinger().getNickname(),"专辑"));
    }
}
