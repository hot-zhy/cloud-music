package com.zhy.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zhy.fragment.FeedFragment;
import com.zhy.model.Feed;

public class UserDetailAdapter extends BaseFragmentStatePagerAdapter<Integer>{
    public UserDetailAdapter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FeedFragment.newInstance();
        }
        return null;
    }
}
