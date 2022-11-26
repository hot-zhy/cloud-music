package com.zhy.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.model.Feed;
import com.zhy.superUI.decoration.GridDividerItemDecoration;
import com.zhy.util.DensityUtil;
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
        if(feed.getMedias()!=null&&feed.getMedias().size()>0){
            //有图片，显示图片控件
            holder.setGone(R.id.list,false);
            //获取图片列表控件
            RecyclerView listView = holder.getView(R.id.list);
            //计算图片显示多少列
            int spanCount=1;
            if(feed.getMedias().size()>4){
                spanCount=3;
            }else if(feed.getMedias().size()>1){
                spanCount=2;
            }
            //为RecyclerView设置布局管理器,九宫格
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
//            将布局管理器设置到控件上
            listView.setLayoutManager(layoutManager);
//            添加分割线
            if(listView.getItemDecorationCount()>0){
                listView.removeItemDecorationAt(0);
            }
            GridDividerItemDecoration divider = new GridDividerItemDecoration(getContext(), (int) DensityUtil.dip2px(getContext(), 5));
            listView.addItemDecoration(divider);
//            列表控件提供数据所需适配器
            new ImageAdapter()
        }


        if(feed.getUser().getId().equals(PreferenceUtil.getInstance(getContext()).getUserId())){
            holder.setVisible(R.id.delete,true);
        }else{
            holder.setVisible(R.id.delete,false);
        }
    }
}
