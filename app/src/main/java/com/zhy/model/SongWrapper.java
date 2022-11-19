package com.zhy.model;

/**
 * 解析音乐列表
 */
public class SongWrapper {
    /**
     * 状态码，=0表示成功
     */
    private int status;
    private Meta data;

    public Meta getData() {
        return data;
    }

    public void setData(Meta data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
