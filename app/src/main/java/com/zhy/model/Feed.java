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
    private User user;

    /**
     * 媒体资源
     * @return
     */
    private String media;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
