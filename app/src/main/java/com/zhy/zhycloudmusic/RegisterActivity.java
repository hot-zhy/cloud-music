package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.zhy.Repository.DefaultRepository;
import com.zhy.api.HttpObserver;
import com.zhy.model.BaseId;
import com.zhy.model.User;
import com.zhy.model.response.DetailResponse;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.super_ja.RegularUtil;
import com.zhy.zhycloudmusic.databinding.ActivityRegisterBinding;
import com.zhy.zhycloudmusic.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * 注册界面,注册完后自动登录
 */
public class RegisterActivity extends BaseLoginActivity<ActivityRegisterBinding> {
    @Override
    protected void initListeners() {
        super.initListeners();
        //点击注册
        binding.primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //校验参数
                String nickname = binding.nickname.getText().toString().trim();
                if(StringUtils.isBlank(nickname)){
                    SuperToast.show(R.string.enter_nickname);
                    return;
                }
                if(!StringUtil.isNickName(nickname)){
                    //在输入框处直接显示错误
                    binding.nicknameInput.setError(getString(R.string.error_nickname_format));
                    return;
                }
                //手机号
                String phone = binding.phone.getText().toString().trim();
                if (StringUtils.isBlank(phone)) {
                    SuperToast.show(R.string.enter_phone);
                    return;
                }

                //手机号格式
                if (!RegularUtil.isPhone(phone)) {
                    SuperToast.show(R.string.error_phone_format);
                    return;
                }

                //密码
                String password = binding.password.getText().toString().trim();
                if (StringUtils.isBlank(password)) {
                    SuperToast.show(R.string.enter_password);
                    return;
                }

                //密码格式
                if (!StringUtil.isPassword(password)) {
                    SuperToast.show(R.string.error_password_format);
                    return;
                }

                //确认密码
                String confirmPassword = binding.confirmPassword.getText().toString().trim();
                if (StringUtils.isBlank(confirmPassword)) {
                    SuperToast.show(R.string.enter_confirm_password);
                    return;
                }

                //确认密码格式
                if (!StringUtil.isPassword(confirmPassword)) {
                    SuperToast.show(R.string.error_confirm_password_format);
                    return;
                }

                //判断密码和确认密码是否一样
                if (!password.equals(confirmPassword)) {
                    SuperToast.show(R.string.error_confirm_password);
                    return;
                }
                User param=new User();
                param.setNickname(nickname);
                param.setPhone(phone);
                param.setPassword(password);
                registerClick(param);
            }
        });
    }

    private void registerClick(User data) {
        //调用注册接口
        DefaultRepository.getInstance()
                .register(data)
                .subscribe(new HttpObserver<DetailResponse<BaseId>>() {
                    @Override
                    public void onSucceeded(DetailResponse<BaseId> d) {
                        //注册成功后登录
                        login(data);
                    }
                });
    }
}