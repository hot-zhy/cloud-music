package com.zhy.model;

/**
 * 登录后返回的解析数据的对象
 */
public class Session extends BaseId{
    /**
     * 登录以后的session
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Session(String token) {
        this.token = token;
    }
}
