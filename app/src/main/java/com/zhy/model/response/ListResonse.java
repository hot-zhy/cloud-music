package com.zhy.model.response;

import com.zhy.model.Meta;

public class ListResonse<T> extends BaseResonse{
    private Meta<T> data;

    public Meta<T> getData() {
        return data;
    }

    public void setData(Meta<T> data) {
        this.data = data;
    }
}
