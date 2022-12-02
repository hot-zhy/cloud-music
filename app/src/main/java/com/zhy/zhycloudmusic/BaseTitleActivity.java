package com.zhy.zhycloudmusic;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

/**
 * 通用标题界面
 * @param <VB>
 */
public class BaseTitleActivity<VB extends ViewBinding> extends BaseViewModeActivity<VB>{
    protected Toolbar toolbar;
    @Override
    protected void initViews() {
        super.initViews();
        toolbar = findViewById(R.id.toolbar);
//        初始化toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
//        showBackMenu();
//        是否显示返回按钮
        if(isShowBackMenu()){
            showBackMenu();
        }
    }

    protected boolean isShowBackMenu() {
        return true;
    }

    /**
     * 显示返回按钮
     */
    protected void showBackMenu(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 监听返回按钮
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                backClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void backClick() {
        onBackPressed();
    }

}
