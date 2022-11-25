package com.zhy.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zhy.adapter.MusicPlayListAdapter;
import com.zhy.manager.MusicListManager;
import com.zhy.zhycloudmusic.R;
import com.zhy.zhycloudmusic.databinding.FragmentDialogMusicPlayListBinding;

/**
 * 播放列表
 */
public class MusicPlayListDialogFragment extends BaseViewModelBottomSheetDialogFragment<FragmentDialogMusicPlayListBinding> {
    private MusicPlayListAdapter adapter;

    @Override
    protected void initViews() {
        super.initViews();
//        设置固定尺寸
        binding.list.setHasFixedSize(true);
        //分割线
        DividerItemDecoration decoration=new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        binding.list.addItemDecoration(decoration);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //给recyclerView设置数据
        adapter = new MusicPlayListAdapter(R.layout.item_play_list,getMusicListManager());
        binding.list.setAdapter(adapter);
        adapter.setList(getMusicListManager().getDatum());
        showLoopModel();
        showCount();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
//                获取当前音乐播放
//                关闭弹窗
                dismiss();
                getMusicListManager().play(getMusicListManager().getDatum().get(position));
            }
        });
        //item中子控件点击
        //删除按钮点击
        adapter.addChildClickViewIds(R.id.delete);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(R.id.delete==view.getId()){
                    //删除按钮点击
                    removeItem(position);
                }
            }
        });
        //删除所有按钮点击
        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                getMusicListManager().deleteAll();
            }
        });
    }

    /**
     * 删除播放列表的某一首音乐
     * @param position
     */
    private void removeItem(int position) {
        adapter.removeAt(position);
        //从列表管理器中删除
        getMusicListManager().delete(position);
        showCount();
    }

    private void showCount() {
        binding.count.setText(String.format("(%d)",getMusicListManager().getDatum().size()));
    }

    /**
     * 显示循环模式
     */
    private void showLoopModel() {
        int model=getMusicListManager().getLoopModel();
        switch (model){
            case MusicListManager.MODEL_LOOP_LIST:
                binding.loopModel.setText("列表循环");
                break;
            case MusicListManager.MODEL_LOOP_RANDOM:
                binding.loopModel.setText("随机循环");
                break;
            default:
                binding.loopModel.setText("单曲循环");
                break;
        }
    }

    /**
     * 显示列表
     * @param fragmentManager
     */
    public static void show(FragmentManager fragmentManager) {
        MusicPlayListDialogFragment fragment = newInstance();
        fragment.show(fragmentManager,"MusicPlayListDialogFragment");
    }

    public static MusicPlayListDialogFragment newInstance() {

        Bundle args = new Bundle();

        MusicPlayListDialogFragment fragment = new MusicPlayListDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    /**
     * 获取播放列表管理器
     */
    protected MusicListManager getMusicListManager(){
        return MusicListManager.getInstance(getActivity().getApplicationContext());
    }
}