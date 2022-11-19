package com.zhy.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zhy.fragment.DiscoverFragment;
import com.zhy.fragment.FeedFragment;
import com.zhy.fragment.MyFragment;

/**
 * 主界面的adapter
 */
public class MainAdapter extends BaseFragmentStatePagerAdapter<Integer>{
    public MainAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回要显示的Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return FeedFragment.newInstance();
            case 2:
                return MyFragment.newInstance();
            default:
                return DiscoverFragment.newInstance();
        }
    }
}
