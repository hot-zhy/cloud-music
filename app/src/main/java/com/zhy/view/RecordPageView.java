package com.zhy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zhy.util.Constant;
import com.zhy.zhycloudmusic.databinding.ViewRecordPageBinding;

/**
 * 黑椒唱片页面View
 */
public class RecordPageView extends LinearLayout {
    public ViewRecordPageBinding binding;
    private float recordRotation;

    public RecordPageView(Context context) {
        super(context);
        init();
    }

    public RecordPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecordPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RecordPageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        binding = ViewRecordPageBinding.inflate(LayoutInflater.from(getContext()), this, true);
        initViews();
        initDatum();
    }

    private void initDatum() {
    }

    private void initViews() {
    }

    public void incrementRotate() {
        if(recordRotation>360){
            recordRotation=0;
        }
        recordRotation+= Constant.ROTATION_PER;
        binding.icon.setRotation(recordRotation);
    }
}
