package com.zhy.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.model.Song;
import com.zhy.zhycloudmusic.R;

/**
 * 简单播放界面的列表适配器
 * 显示数据
 */
public class simplePlayerAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {
    //当前选中的列表索引/
    //默认选中第0条音乐
    private int selectedIndex=-1;

    public simplePlayerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Song song) {
        //展示列表中的标题
        holder.setText(android.R.id.text1,song.getName());
        if(selectedIndex==holder.getAdapterPosition()){
            holder.setTextColorRes(android.R.id.text1, R.color.primary);
        }else{
            holder.setTextColorRes(android.R.id.text1,R.color.black32);
        }
    }

    /**
     * 外界setindex
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        if(this.selectedIndex!=-1){
            //刷新上一行
            notifyItemChanged(this.selectedIndex);

        }
        this.selectedIndex = selectedIndex;
        notifyItemChanged(this.selectedIndex);
    }
}
