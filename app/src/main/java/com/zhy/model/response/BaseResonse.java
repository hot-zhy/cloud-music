package com.zhy.model.response;

public class BaseResonse {
    private int status;
//    出错的提示信息
    private String message;

    public boolean isSucceeded(){
        return status==0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
