package com.zhy.util;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.zhy.config.Config;
import com.zhy.model.SearchHistory;
import com.zhy.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * liteorm数据库工具类
 */
public class LiteORMUtil {
    private static LiteORMUtil instance;
    private final Context context;
    private final LiteOrm orm;

    public LiteORMUtil(Context context) {
        this.context=context;
        //获取偏好设置工具类
        PreferenceUtil sp=PreferenceUtil.getInstance(context);
        //创建数据库实例
        String databaseName=String.format("zhy_%s.db",sp.getUserId());
        orm=LiteOrm.newSingleInstance(context,databaseName);
        //设置调试模式
        orm.setDebugged(Config.DEBUG);
    }

    public static LiteORMUtil getInstance(Context context){
        if(instance==null){
            instance=new LiteORMUtil(context.getApplicationContext());
        }
        return instance;
    }
    public static void destroy() {
        instance = null;
    }

    //region 搜索历史
    /**
     * 根据主键创建或更新历史
     * @param data
     */
    public void createOrUpdate(SearchHistory data){
        orm.save(data);
    }

    /**
     * 查询搜索历史，并按时间顺序倒序排序
     * @return
     */
    public List<SearchHistory> querySearchHistory(){
        QueryBuilder<SearchHistory> queryBuilder = new QueryBuilder<SearchHistory>(SearchHistory.class)
                .appendOrderDescBy("createAt");
        return orm.query(queryBuilder);
    }

    /**
     * 删除搜索历史
     * @param data
     */
    public void deleteSearchHistory(SearchHistory data){
        orm.delete(data);
    }
    //endregion

    //region 播放列表
    /**
     * 查询播放列表
     * @return
     */
    public List<Song> queryPlayList(){
        QueryBuilder<Song> queryBuilder = new QueryBuilder<>(Song.class);
        queryBuilder.appendOrderDescBy("createAt");
        return localConverts(orm.query(queryBuilder));

    }

    private List<Song> localConverts(ArrayList<Song> data) {
        for(Song it:data){
            it.localConvert();
        }
        return data;
    }

    /**
     * 删除某一首音乐
     * @param data
     */
    public void deleteSong(Song data){
        orm.delete(data);
    }

    /**
     * 删除所有
     */
    public void deleteAllSong(){
        orm.deleteAll(Song.class);
    }

    /**
     * 保存播放列表的所有音乐
     * @param data
     */
    public void saveAll(List<Song> data){
        convertLocal(data);
        orm.save(data);
    }

    private void convertLocal(List<Song> data) {
        for (Song it:data){
            it.convertLocal();
        }
    }

    /**
     * 保存一首音乐
     */
    public void save(Song data){
        orm.save(data);
    }
    //endregion

}
