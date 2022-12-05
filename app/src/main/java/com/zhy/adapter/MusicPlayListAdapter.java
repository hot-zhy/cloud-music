package com.zhy.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.manager.MusicListManager;
import com.zhy.model.Song;
import com.zhy.zhycloudmusic.R;

/**
 * 播放列表adapter
 */
public class MusicPlayListAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    private final MusicListManager musicListManager;

    public MusicPlayListAdapter(int layoutResId, MusicListManager musicListManager) {
        super(layoutResId);
        this.musicListManager=musicListManager;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder holder, Song data) {
        //标题显示数据
        String title=String.format("%s - %s",data.getName(),data.getSinger());
        //设置数据
        holder.setText(R.id.title,title);
        //处理选中
        if(data.getId().equals(musicListManager.getData().getId())){
            holder.setTextColorRes(R.id.title,R.color.primary);
        }else{
            holder.setTextColor(R.id.title,getContext().getColor(R.color.black32));
        }
    }
}
