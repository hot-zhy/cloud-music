package com.zhy.manager;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.zhy.model.Song;
import com.zhy.util.LiteORMUtil;
import com.zhy.util.PreferenceUtil;
import com.zhy.util.ResourceUtil.ResourceUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 列表管理器，为了再次进入应用时继续上一次的播放历史，恢复历史
 * 处理列表相关的逻辑
 */
public class MusicListManager implements MusicPlayerListener {
    public static final int MODEL_LOOP_LIST=0;
    public static final int MODEL_LOOP_ONE=1;
    public static  final int MODEL_LOOP_RANDOM=2;
    /**
     * 保持播放进度间隔
     */
    public static final long SAVE_PROGRESS_TIME=2000;

    private static MusicListManager instance;
    private final Context context;
    private final MusicPlayerManager musicPlayerManager;
    private final PreferenceUtil sp;
    private final LiteORMUtil orm;

    /**
     * 循环模式，默认情况时列表循环
     */
    private int model=MODEL_LOOP_LIST;
    //播放列表
    private List<Song> datum=new ArrayList<>();
    private boolean isPlay;
    //当前播放的音乐
    private Song data;
    //上次保存音乐进度的时间
    private long lastTime;

    /**
     * 私有化构造管理器，外界就不能创建了
     * @param context
     */
    private MusicListManager(Context context) {
        this.context=context;
        //创建musicplayermanager
        musicPlayerManager=MusicPlayerManager.getInstance(context);
        musicPlayerManager.addMusicPlayerListener(this);
        sp=PreferenceUtil.getInstance(context);

        orm =LiteORMUtil.getInstance(context);
        //初始化列表
        initPlayList();
    }

    /**
     * 从数据库恢复播放列表（初始化播放列表）
     */
    private void initPlayList() {
        /**
         * 查询播放列表
         */
        List<Song> datum=orm.queryPlayList();
        if(!datum.isEmpty()){
            this.datum.clear();
            this.datum.addAll(datum);
            /**
             * 获取最后播放音乐的id
             */
            String id=sp.getLastPlaySongId();
            if(StringUtils.isNotBlank(id)){
                /**
                 * 根据id找到该音乐
                 */
                for (Song it:datum){
                    if(it.getId().equals(id)){
                        data=it;
                        break;
                    }
                }
                if(data==null){
                    defaultPlaySong();
                }
            }else{
                /**
                 * 默认是第一首音乐
                 */
                defaultPlaySong();
            }
        }

    }

    private void defaultPlaySong() {
        data=datum.get(0);
    }

    //单例模式
    public static MusicListManager getInstance(Context context) {
        if(instance==null){
            instance=new MusicListManager(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * 把播放列表的数据传递进来,设置播放列表
     * @param datum
     */
    public void setDatum(List<Song> datum) {
        orm.deleteAllSong();
        //清空原来的列表
        this.datum.clear();
        //添加新的播放列表
        this.datum.addAll(datum);
        //将播放列表保存至数据库
        orm.saveAll(datum);
    }

    /**
     * 播放点击的音乐
     * @param data
     */
    public void play(Song data) {
        //标记是否播放
        isPlay=true;
        //保存当前播放的音乐
        this.data=data;
        //播放在线音乐
        String path= ResourceUtil.resourceUri(data.getUri());
        //开始播放
        musicPlayerManager.play(path,data);
        //保存最后一首播放音乐的id
        sp.setLastPlaySongId(data.getId());

        
    }

    public Song getData(){
        return data;
    }

    public List<Song> getDatum() {
        return datum;
    }

    public void pause() {
        musicPlayerManager.pause();
    }

    /**
     * 继续播放
     */
    public void resume() {
        if(isPlay){
//            调用过play方法，即可调用resume
            musicPlayerManager.resume();
        }else{
            //应用第一次打开时，第一次点击继续播放，而这时内部还没有准备播放，所以应该调用play
            play(data);
            /**
             * 判断是否需要继续播放
             */
            if(data.getProgress()>0){
                musicPlayerManager.seekTo(data.getProgress());
            }
        }
    }

    /**
     * 更改循环模式
     * @return
     */
    public int changeLoopModel() {
        model++;
        if (model > MODEL_LOOP_RANDOM) {
            model = MODEL_LOOP_LIST;
        }
        //单曲循环
        if (MODEL_LOOP_ONE==model) {
            musicPlayerManager.setLooping(true);
        } else {
            //其他模式关闭单曲循环
            musicPlayerManager.setLooping(false);
        }
        return model;
    }

    public int getLoopModel() {
        return model;
    }

    /**
     * 获取下一首音乐
     * @return
     */
    public Song next() {
        if(datum.size()==0){
            return null;
        }
        int index=0;
        switch (model){
            case MODEL_LOOP_RANDOM:
                index=new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index=datum.indexOf(data);
                //最后一首音乐
                if(index==datum.size()-1){
                    index=0;
                }else{
                    index++;
                }
                break;
        }
        return datum.get(index);
    }

    /**
     * 上一曲
     * @return
     */
    public Song previous() {
        if(datum.size()==0){
            return null;
        }
        int index=0;
        switch (model){
            case MODEL_LOOP_RANDOM:
                index=new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index=datum.indexOf(data);
                //最后一首音乐
                if(index==0){
                    index=datum.size()-1;
                }else{
                    index--;
                }
                break;
        }
        return datum.get(index);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(model==MODEL_LOOP_ONE){

        }else{
            Song data=next();
            if(data!=null){
                play(data);

            }
        }
    }

    @Override
    public void onProgress(Song data) {
        long currentTime=System.currentTimeMillis();
        /**
         * 每两秒钟保存音乐播放进度
         */
        if(currentTime-lastTime>=SAVE_PROGRESS_TIME){
            orm.save(data);
            lastTime=currentTime;
        }
    }

    /**
     * 拖拽音乐进度
     * @param data
     */
    public void seekTo(int data) {
        if(!musicPlayerManager.isPlaying()){
            resume();
        }
        musicPlayerManager.seekTo(data);
    }
}
