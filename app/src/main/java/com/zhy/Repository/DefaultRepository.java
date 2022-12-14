package com.zhy.Repository;

import com.zhy.api.DefaultService;
import com.zhy.api.NetworkModule;
import com.zhy.model.Base;
import com.zhy.model.BaseId;
import com.zhy.model.Feed;
import com.zhy.model.Session;
import com.zhy.model.Song;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.internal.operators.observable.ObservableError;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Part;

/**
 * 定义API
 */
public class DefaultRepository {
    private static DefaultRepository instance;
    private final DefaultService service;

    //对象私有化
    private DefaultRepository(){
        OkHttpClient okHttpClient= NetworkModule.providerOkHttpClient();
        Retrofit retrofit=NetworkModule.provideRetrofit(okHttpClient);
        service = retrofit.create(DefaultService.class);
    }


    /**
     * 返回当前对象的单一实例
     * @return
     */
    public synchronized static DefaultRepository getInstance(){
        //只创建一次
        if(instance==null){
            instance=new DefaultRepository();
        }
        return instance;
    }

    /**
     * 音乐列表
     * @return
     */
    public Observable<ListResonse<Song>> songs(){
        return service.songs("1",10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DetailResponse<Session>> login(String password, String phone){
        return service.login(password,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 用户详情
     */
    public Observable<DetailResponse<User>> userDetail(String id) {
        return service.userDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 注册
     */
    public Observable<DetailResponse<BaseId>> register(@Body String nickname, @Body String password, @Body String phone) {
        return service.register(nickname,password,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 动态列表
     */
    public Observable<ListResonse<Feed>> feeds(Map<String,String> data){
        return service.feeds(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 发布动态
     */
    public Observable<DetailResponse<BaseId>> createFeed(String content,String media,String id){
        return service.createFeed(content,media,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 上传单张图片
     */
    public  Observable<DetailResponse<BaseId>> uploadFile(MultipartBody.Part file, RequestBody flavor){
        return service.uploadFile(file,flavor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 上传多张图片
     */
    public Observable<ListResonse<String>> uploadFiles(List<MultipartBody.Part> file,RequestBody flavor){
        return service.uploadFiles(file,flavor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 获取本人列表
     */
    public Observable<ListResonse<Feed>> feedSelf(String id){
        return service.feedSelf(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
