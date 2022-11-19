package com.zhy.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用FragmentPagerAdapter
 */
public abstract class BaseFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter {
    protected final List<T> datum = new ArrayList<>();
    protected final Context context;

    public BaseFragmentStatePagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    /**
     * 有多少个
     *
     * @return
     */
    @Override
    public int getCount() {
        return datum.size();
    }

    /**
     * 返回当前位置数据
     *
     * @param position
     * @return
     */
    public T getData(int position) {
        return datum.get(position);
    }

    /**
     * 设置数据
     *
     * @param datum
     */
    public void setDatum(List<T> datum) {
        this.datum.clear();

        if (datum != null && datum.size() > 0) {
            this.datum.addAll(datum);
        }

        //通知数据改变了
        notifyDataSetChanged();
    }
}
