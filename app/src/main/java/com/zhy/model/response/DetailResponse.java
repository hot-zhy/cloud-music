package com.zhy.model.response;

public class DetailResponse<T> extends BaseResonse {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
