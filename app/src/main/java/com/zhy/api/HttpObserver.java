package com.zhy.api;

import com.zhy.model.response.BaseResonse;
import com.zhy.util.ExceptionHandlerUtil.ExceptionHandlerUtil;

import io.reactivex.rxjava3.core.Observer;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 自动处理网络错误
 *
 * @param <T>
 */
public abstract class HttpObserver<T> extends ObserverAdapter<T> {

    /**
     * 请求成功
     *
     * @param data
     */
    public abstract void onSucceeded(T data);

    /**
     * 请求失败
     *
     * @param data
     * @param e
     * @return
     */
    public boolean onFailed(T data, Throwable e) {
        return false;
    }

    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (isSucceeded(t)) {
            onSucceeded(t);
        } else {
//            请求出错
            handleRequest(t, null);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        handleRequest(null,e);
    }

    private void handleRequest(T data, Throwable error) {
        if (onFailed(data, error)) {

        } else {
            ExceptionHandlerUtil.handlerRequest(data, error);
        }
    }


    /**
     * 判断网络请求是否成功
     *
     * @param t
     * @return
     */
    private boolean isSucceeded(T t) {
        if (t instanceof Retrofit) {
            Response response = (Response) t;
            if (response.code() >= 200 && response.code() <= 299) {
                return true;
            }
        } else if (t instanceof BaseResonse) {
            BaseResonse baseResonse = (BaseResonse) t;
            return baseResonse.isSucceeded();
        }
        return false;
    }
}
