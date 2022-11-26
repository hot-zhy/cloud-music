package com.zhy.zhycloudmusic;

import androidx.annotation.NonNull;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;


import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zhy.config.Config;
import com.zhy.util.SuperDateUtil;
import com.zhy.zhycloudmusic.databinding.ActivityStartBinding;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


//声明当前页面具有动态获取权限
@RuntimePermissions
public class StartActivity extends BaseViewModeActivity<ActivityStartBinding> {

    @Override
    protected void initViews() {
        super.initViews();
//        设置沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        binding.copyright.setText(getString(R.string.copyright, SuperDateUtil.currentYear()));

//        进入应用检查权限
        checkPermissions();
    }

    @Override
    protected void initListeners(){
        super.initListeners();
        binding.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                testGet();
                testRetrofitGet();
            }

        });
    }

    private void testRetrofitGet() {
//        OkHttpClient okHttpClient=NetworkModule.providerOkHttpClient();
//        Retrofit retrofit=NetworkModule.provideRetrofit(okHttpClient);
//        DefaultService service=retrofit.create(DefaultService.class);
//        service.songs("zhy",10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResonse<Song>>() {
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ListResonse<Song> songWrapper) {
//
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


//        service.feeds().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListResonse<Feed>>() {
//            @Override
//            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@io.reactivex.rxjava3.annotations.NonNull ListResonse<Feed> songWrapper) {
//
//            }
//
//            @Override
//            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        service.songDetail("header","1")
//                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpObserver<DetailResponse<Song>>(){
//                    private static final String TAG = "tag";
//
//                    @Override
//                    public void onSucceeded(DetailResponse<Song> data) {
//                        Log.d(TAG,"onsucceeded:"+data.getData().getTitle());
//                    }
//
//                    @Override
//                    public void onNext(DetailResponse<Song> songDetailResponse) {
//                        super.onNext(songDetailResponse);
//                    }
//                });
//        SuperToast.show("zhy");
    }

    /**
     * 实现网络请求
     */
//    private void testGet(){
//        OkHttpClient client=new OkHttpClient();
//        String url=Config.ENDPOINT+"v1/songs";
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//            }
//        });
//    }

    private void checkPermissions() {
        StartActivityPermissionsDispatcher.onPermissionGrantedWithPermissionCheck(this);
    }


    /**
     * 如果用户授予了权限就进入主界面
     */
    @NeedsPermission({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    })
    void onPermissionGranted() {
        prepareNext();

    }

    /**
     * 若用户拒绝授予权限，则告知其为什么要用到这些权限
     */
    @OnShowRationale({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showRequestPermission(PermissionRequest request) {
        new AlertDialog.Builder(this).setMessage(R.string.permission_hint).setPositiveButton(R.string.allow, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.proceed();
            }
        }).setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                request.cancel();
            }
        }).show();
    }

    /**
     * 用户拒绝授予权限则退出应用
     */
    @OnPermissionDenied({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showDenied() {
        finish();
    }

    /**
     * 再次获取权限的提示
     */
    @OnNeverAskAgain({
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    })
    void showNeverAsk() {
        checkPermissions();
    }

    private void prepareNext() {
        binding.copyright.postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        },Config.SPLASH_DEFAULT_DELAY_TIME);
    }
    private void next() {
//        关闭当前页面并跳转到另一个页面
        startActivityAfterFinishThis(MainActivity.class);
    }

    /**
     * 不论用户是授权还是同意，系统都会调用该方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        将授权结果传递到框架
        StartActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}