package com.zhy.api;

import com.zhy.model.Feed;
import com.zhy.model.Song;
import com.zhy.model.SongWrapper;
import com.zhy.model.response.DetailResponse;
import com.zhy.model.response.ListResonse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Observable<ListResonse<Feed>> feeds();

    /**
     * 音乐详情
     * @param testHeader
     * @param id
     * @return
     */
    @GET("v1/songs/{id}")
    Observable<DetailResponse<Song>> songDetail(@Header("testHeader") String testHeader, @Path("id") String id);
}
