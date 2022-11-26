package com.zhy.Repository;

import com.zhy.api.DefaultService;
import com.zhy.api.NetworkModule;
import com.zhy.model.Session;
import com.zhy.model.Song;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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

    public Observable<DetailResponse<Session>> login(User data){
        return service.login(data);
    }
    /**
     * 用户详情
     */
    public Observable<DetailResponse<User>> userDetail(String data) {
        return service.userDetail(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
