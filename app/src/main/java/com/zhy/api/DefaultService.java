package com.zhy.api;

import com.zhy.model.Base;
import com.zhy.model.BaseId;
import com.zhy.model.Feed;
import com.zhy.model.Session;
import com.zhy.model.Song;
import com.zhy.model.SongWrapper;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DefaultService {
    /**
     * 单曲
     * @param category
     * @param size
     * @return
     */
    @GET("v1/songs")
    Observable<ListResonse<Song>> songs(@Query(value="category") String category, @Query("size") int size);

    /**
     * 动态列表
     */
    @GET("v1/feeds")
    Observable<ListResonse<Feed>> feeds(@QueryMap Map<String,String> data);

    /**
     * 音乐详情
     * @param testHeader
     * @param id
     * @return
     */
    @GET("v1/songs/{id}")
    Observable<DetailResponse<Song>> songDetail(@Header("testHeader") String testHeader, @Path("id") String id);
    /**
     * 登录
     */
    @POST("v1/sessions")
    Observable<DetailResponse<Session>> login(@Body User data);
    /**
     * 发布动态,访问参数是Feed类型
     */
    @POST("v1/feeds")
    Observable<DetailResponse<BaseId>> createFeed(@Body Feed data);

    /**
     * 用户详情
     * @param data
     * @return
     */
    @GET("v1/users/{data}")
    Observable<DetailResponse<User>> userDetail(@Path("data") String data);

    /**
     * 注册,返回的用户id
     */
    @POST("v1/users")
    Observable<DetailResponse<BaseId>> register(@Body User data);

    /**
     * 上传单张文件
     * @param file
     * @param flavor
     * @return
     */
    @Multipart
    @POST("v1/r")
    Observable<DetailResponse<BaseId>> uploadFile(@Part MultipartBody.Part file, @Part("flavor")RequestBody flavor);
    /**
     * 上传多张文件
     * @param file
     * @param flavor
     * @return
     */
    @Multipart
    @POST("v1/r/multi")
    Observable<ListResonse<String>> uploadFiles(@Part List<MultipartBody.Part> file, @Part("flavor")RequestBody flavor);
}
