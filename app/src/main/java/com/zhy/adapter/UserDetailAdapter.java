package com.zhy.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zhy.fragment.FeedFragment;
import com.zhy.fragment.FeedMyFragment;
import com.zhy.fragment.UserDetailAboutFragment;
import com.zhy.model.Feed;
import com.zhy.zhycloudmusic.R;

public class UserDetailAdapter extends BaseFragmentStatePagerAdapter<Integer>{
    private static final int[] titles={
            R.string.feed,
            R.string.me
    };
    private final String userId;

    public UserDetailAdapter(Context context, @NonNull FragmentManager fm, String userId) {
        super(context, fm);
        this.userId=userId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                //只返回本用户的FeedFragment
                return FeedMyFragment.newInstance(userId);
            default:
                //返回关于
                return UserDetailAboutFragment.newInstance(userId);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titles[position]);

    }
}
