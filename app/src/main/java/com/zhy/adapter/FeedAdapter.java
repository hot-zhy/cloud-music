package com.zhy.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.model.Feed;
import com.zhy.util.PreferenceUtil;
import com.zhy.util.SuperDateUtil;
import com.zhy.util.ImageUtil;
import com.zhy.zhycloudmusic.R;

/**
 * 动态适配器
 */
public class FeedAdapter extends BaseQuickAdapter<Feed, BaseViewHolder> {

    public FeedAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, Feed feed) {
        ImageUtil.showAvatar(holder.getView(R.id.icon),feed.getUser().getIcon());
        holder.setText(R.id.nickname,feed.getUser().getNickname());
        holder.setText(R.id.content,feed.getContent());
        holder.setText(R.id.date, SuperDateUtil.commonFormat(feed.getCreateAt()));

        if(feed.getUser().getId().equals(PreferenceUtil.getInstance(getContext()).getUserId())){
            holder.setVisible(R.id.delete,true);
        }else{
            holder.setVisible(R.id.delete,false);
        }
    }
}
