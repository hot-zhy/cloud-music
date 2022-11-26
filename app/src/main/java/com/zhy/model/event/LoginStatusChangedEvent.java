package com.zhy.model.event;

public class LoginStatusChangedEvent {
    private boolean isLogin;
    public LoginStatusChangedEvent(boolean isLogin){
        this.isLogin=isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
