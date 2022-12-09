package com.zhy.zhycloudmusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewbinding.ViewBinding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.common.collect.Lists;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.zhy.Repository.DefaultRepository;
import com.zhy.adapter.ImageAdapter;
import com.zhy.api.HttpObserver;
import com.zhy.config.glide.GlideEngine;
import com.zhy.model.Base;
import com.zhy.model.BaseId;
import com.zhy.model.Feed;
import com.zhy.model.event.FeedChangedEvent;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;
import com.zhy.superUI.decoration.GridDividerItemDecoration;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.util.DensityUtil;
import com.zhy.util.PreferenceUtil;
import com.zhy.zhycloudmusic.databinding.ActivityPublishFeedBinding;
import com.zhy.zhycloudmusic.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

public class PublishFeedActivity extends BaseTitleActivity<ActivityPublishFeedBinding> {
    private String content;
    private ImageAdapter imageAdapter;

    @Override
    protected void initViews() {
        super.initViews();
        GridLayoutManager layoutManager=new GridLayoutManager(getHostActivity(),4);
        binding.list.setLayoutManager(layoutManager);
        //添加分割线
        GridDividerItemDecoration divider = new GridDividerItemDecoration(getHostActivity(), (int) DensityUtil.dip2px(getHostActivity(), 5));
        binding.list.addItemDecoration(divider);

    }

    @Override
    protected void initDatum() {
        super.initDatum();
        imageAdapter = new ImageAdapter(R.layout.item_image);
        binding.list.setAdapter(imageAdapter);
//        为adapter填数据
        setData(new ArrayList<>());
    }

    private void setData(List<Object> data) {
        if(data.size()<9){
//            添加一个加号
            data.add(R.drawable.add_fill);
        }
        imageAdapter.setNewInstance(data);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initListeners() {
        super.initListeners();
        //监听字数变化，统计字数并显示
        binding.content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                String result = getString(R.string.feed_count, editable.toString().trim().length());
                binding.count.setText(result);
            }
        });
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(imageAdapter.getItem(position) instanceof Integer){
                    //点击的是按钮
                    selectImage();
                }
            }
        });
        imageAdapter.addChildClickViewIds(R.id.close);
        imageAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                imageAdapter.removeAt(position);
            }
        });
    }

    private void selectImage() {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(9)
                .setMinSelectNum(1)
                .setImageSpanCount(3)
                .setSelectionMode(SelectModeConfig.MULTIPLE)
                .isPreviewImage(true)
                .isDisplayCamera(true)
                .setCameraImageFormat(PictureMimeType.JPEG)
                //压缩图片
                .setCompressEngine(new CompressFileEngine() {
                    @Override
                    public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
                        Luban.with(context).load(source).ignoreBy(100)
                                .setCompressListener(new OnNewCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(String source, File compressFile) {
                                        if (call != null) {
                                            call.onCallback(source, compressFile.getAbsolutePath());
                                        }
                                    }

                                    @Override
                                    public void onError(String source, Throwable e) {
                                        if (call != null) {
                                            call.onCallback(source, null);
                                        }
                                    }
                                }).launch();
                    }
                })
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        setData(Lists.newArrayList(result));
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

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
//        获取选中的图片
        List<LocalMedia> selectedImages=getSelectedImages();
        if(selectedImages.size()>0){
            //用户选择了图片
            //上传图片
            uploadImages(selectedImages);
        }else{
//            没有图片，直接发布动态
            saveFeed(null);
        }

    }

    private void uploadImages(List<LocalMedia> selectedImages) {
        ArrayList<MultipartBody.Part> bodyFiles = new ArrayList<>();
        for(LocalMedia it:selectedImages){
            File file = new File(it.getCompressPath());
            //创建成文件表单项
            RequestBody fileBody = RequestBody.Companion.create(file, MediaType.parse("image/*"));
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
            bodyFiles.add(multipartBody);

        }
        RequestBody flavorBody = RequestBody.Companion.create("dev", MediaType.parse("multipart/form-data"));
//        调用接口
        DefaultRepository.getInstance().uploadFiles(bodyFiles,flavorBody)
                .subscribe(new HttpObserver<ListResonse<String>>() {
                    @Override
                    public void onSucceeded(ListResonse<String> data) {
                        saveFeed(data.getData().getData());
                    }
                });

    }

    private List<LocalMedia> getSelectedImages() {
        //获取用户选择的图片列表
        List<Object> data=imageAdapter.getData();
        List<LocalMedia> datum=new ArrayList<>();
        for(Object o:data){
            if(o instanceof LocalMedia){
                datum.add((LocalMedia) o);
            }
        }
        return datum;
    }

    /**
     * 点击发布按钮后发布动态
     */
    private void saveFeed(List<String> medias) {
        String id;
        id=sp.getUserId();
//        System.out.println(sp.getUserId());
//        调用网络接口发布动态
//        Feed feed=new Feed();0
//        feed.setContent(content);
//        feed.setMedia(StringUtils.join(medias,","));
        if(StringUtils.join(medias,",")==null){
            DefaultRepository.getInstance()
                    .createFeed(content,"", id)
                    .subscribe(new HttpObserver<DetailResponse<BaseId>>() {
                        @Override
                        public void onSucceeded(DetailResponse<BaseId> data) {
                            EventBus.getDefault().post(new FeedChangedEvent());
                            //发布动态成功
                            finish();
                        }
                    });
        }else{
            DefaultRepository.getInstance()
                    .createFeed(content,StringUtils.join(medias,","), id)
                    .subscribe(new HttpObserver<DetailResponse<BaseId>>() {
                        @Override
                        public void onSucceeded(DetailResponse<BaseId> data) {
                            EventBus.getDefault().post(new FeedChangedEvent());
                            //发布动态成功
                            finish();
                        }
                    });

        }

    }
}