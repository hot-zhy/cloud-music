package com.zhy.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 动态模型
 */
public class Feed extends Common{
    /**
     * 动态内容
     */
    private String content;

    /**
     * 用户
     * @return
     */
    private Integer userId;
    private String nickname;
    private String icon;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 媒体资源
     * @return
     */
    private String media;


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMedias() {
        //返回列表
        if(StringUtils.isNotBlank(media)){
            return Arrays.asList(media.split(","));
        }
        return null;
    }
}
