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

    /**
     * 密码
     * @return
     */
    private String password;
    private int gender;
    private String birthday;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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


    public Object getGenderFormat() {
        switch (gender){
            case 10:
                return "男";
            case 20:
                return "女";
            default:
                return "保密";
        }
    }
}
