package com.zhy.model;

/**
 * 登录后返回的解析数据的对象
 */
public class Session extends BaseId{
    /**
     * 登录以后的session
     */
    private String session;

    public Session(String session) {
        this.session = session;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
