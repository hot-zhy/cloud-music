package com.zhy.util.ExceptionHandlerUtil;

import android.text.TextUtils;

import com.zhy.AppContext;
import com.zhy.model.response.BaseResonse;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.zhycloudmusic.R;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * 处理错误辅助类
 */
public class ExceptionHandlerUtil {
    /**
     * 网络错误处理
     * @param data
     * @param error
     * @param <T>
     */
    public static <T> void handlerRequest(T data,Throwable error){
        if(error!=null){
            handleException(error);
        }else{
//            业务错误
            if (data instanceof Response) {
                Response response = (Response) data;

                //获取响应码
                int code = response.code();

                //判断响应码
                if (code >= 200 && code <= 299) {
                    //网络请求正常
                } else {
                    handleHttpError(code);
                }
            } else {
                BaseResonse response = (BaseResonse) data;

                if (TextUtils.isEmpty(response.getMessage())) {
                    //没有错误提示信息
                    SuperToast.show(R.string.error_unknown);
                }else{
                    SuperToast.show(response.getMessage());
                }
            }
        }
    }

    /**
     * 处理异常
     * @param error
     */
    public static void handleException(Throwable error){
        if(error instanceof UnknownHostException){
            SuperToast.show(R.string.error_network_unknown_host);
        }else if(error instanceof ConnectException){
            SuperToast.show(R.string.network_error);
        }else if(error instanceof SocketTimeoutException){
            SuperToast.show(R.string.error_network_timeout);
        }else if(error instanceof HttpException){
            HttpException exception=(HttpException) error;
            int code=exception.code();
            handleHttpError(code);
        }else if(error instanceof IllegalArgumentException){
            SuperToast.show(R.string.error_parameter);
        }else{
            String message=error.getLocalizedMessage();
            if (StringUtils.isNotBlank(message)) {
                message = AppContext.getInstance().getString(R.string.error_unknown_format,message);
            }else{
                message = AppContext.getInstance().getString(R.string.error_unknown);
            }

            SuperToast.show(message);
        }
    }

    /**
     * 处理HTTP错误
     * @param code
     */
    private static void handleHttpError(int code) {
        if (code == 401) {
            SuperToast.show(R.string.error_network_not_auth);

            AppContext.getInstance().logout();
        } else if (code == 403) {
            SuperToast.show(R.string.error_network_not_permission);
        } else if (code == 404) {
            SuperToast.show(R.string.error_network_not_found);
        } else if (code >= 500) {
            SuperToast.show(R.string.error_network_server);
        } else {
            SuperToast.show(R.string.error_unknown);
        }
    }
}
