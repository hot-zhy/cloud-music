package com.zhy.zhycloudmusic;

import androidx.viewbinding.ViewBinding;

import com.zhy.AppContext;
import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.manager.MyActivityManager;
import com.zhy.model.Session;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.util.PreferenceUtil;

/**
 * 通用的登录逻辑
 * @param <VB>
 */
public class BaseLoginActivity<VB extends ViewBinding> extends BaseTitleActivity<VB>{
    protected void login(String password,String phone){
        //调用登录接口
        DefaultRepository.getInstance()
                .login(password,phone)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    /**
                     * 登录成功
                     * @param data
                     */
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        onLogin(data.getData());
                    }
                });
    }

    /**
     * 保存登录信息
     * @param data
     */
    private void onLogin(Session data) {
        //保存userId到偏好配置
        sp.setUserId(data.getId());
        sp.setSession(data.getToken());
//        将登录事件代理到AppContext中
//        其他的功能可能也需要用户登录
        AppContext.getInstance().onLogin(data);
//        登陆完成后，关闭所有界面
        MyActivityManager.getInstance().finishAllLogin();
    }
}
