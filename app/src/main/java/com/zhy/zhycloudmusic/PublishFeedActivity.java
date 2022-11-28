package com.zhy.zhycloudmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.BaseId;
import com.zhy.model.Feed;
import com.zhy.model.event.FeedChangedEvent;
import com.zhy.model.response.DetailResponse;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.zhycloudmusic.databinding.ActivityPublishFeedBinding;
import com.zhy.zhycloudmusic.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

public class PublishFeedActivity extends BaseTitleActivity<ActivityPublishFeedBinding> {
    private String content;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.publish:
                sendClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendClick() {
        //获取用户在输入框填写的动态文本
        content = binding.content.getText().toString().trim();
        if(StringUtils.isBlank(content)){
            SuperToast.show("您还没有填写哦~");
            return;
        }
        saveFeed(content);
    }

    /**
     * 点击发布按钮后发布动态
     * @param content
     */
    private void saveFeed(String content) {
//        调用网络接口发布动态
        Feed feed=new Feed();
        feed.setContent(content);
        DefaultRepository.getInstance().createFeed(feed).subscribe(new HttpObserver<DetailResponse<BaseId>>() {
            @Override
            public void onSucceeded(DetailResponse<BaseId> data) {
                EventBus.getDefault().post(new FeedChangedEvent());
                //发布动态成功
                finish();
            }
        });
    }
}