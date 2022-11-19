package com.zhy.model;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.Table;

/**
 * 单曲模型
 */
@Table("song")
public class Song extends Common{
    private String title;
    private String icon;
    private String uri;
    //不存数据库的加ignore注解
    @Ignore
    private int clicksCount;
    @Ignore
    private int commentsCount;
    @Ignore
    private User user;
    @Ignore
    private User singer;

    //播放后的值，总进度和当前进度
    private int duration;
    private int progress;

    /**
     * 嵌套模型，拆分字段，方便后续查询和展示,比如，根据歌手昵称进行查询
     */
    @Column("single_id")
    private String singerId;
    @Column("singer_nickname")
    private String singerNickname;
    @Column("singer_icon")
    private String singerIcon;


    /**
     * 辅助方法转化字段user
     */
    public void localConvert(){
        User user=new User();
        user.setId(singerId);
        user.setNickname(singerNickname);
        user.setIcon(singerIcon);
        singer=user;
    }
    public void convertLocal(){
        singerId=singer.getId();
        singerNickname=singer.getNickname();
        singerIcon=singer.getIcon();

    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getClicksCount() {
        return clicksCount;
    }

    public void setClicksCount(int clicksCount) {
        this.clicksCount = clicksCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSinger() {
        return singer;
    }

    public void setSinger(User singer) {
        this.singer = singer;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getSingerNickname() {
        return singerNickname;
    }

    public void setSingerNickname(String singerNickname) {
        this.singerNickname = singerNickname;
    }

    public String getSingerIcon() {
        return singerIcon;
    }

    public void setSingerIcon(String singerIcon) {
        this.singerIcon = singerIcon;
    }
}
