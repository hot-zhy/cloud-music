package com.zhy.model;

public class User extends Common{
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String icon;

    /**
     * 手机号
     */
    private String phone;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
