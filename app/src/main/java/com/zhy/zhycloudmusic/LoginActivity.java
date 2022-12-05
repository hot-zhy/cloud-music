package com.zhy.zhycloudmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.zhy.model.User;
import com.zhy.superUI.reflect.toast.SuperToast;
import com.zhy.super_ja.RegularUtil;
import com.zhy.zhycloudmusic.databinding.ActivityLoginBinding;
import com.zhy.zhycloudmusic.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseLoginActivity<ActivityLoginBinding> {
    @Override
    protected void initListeners() {
        super.initListeners();
        //登录按钮点击事件
        binding.primary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = binding.username.getText().toString().trim();
                if(StringUtils.isBlank(phone)){
                    SuperToast.show(R.string.enter_phone);
                    return;
                }
                if(!RegularUtil.isPhone(phone)){
                     SuperToast.show(R.string.error_phone_format);
                     return;
                }
                //获取密码
                String password = binding.password.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    SuperToast.show(R.string.enter_password);
                    return;
                }
                if(!StringUtil.isPassword(password)){
                    SuperToast.show(R.string.error_password_format);
                    return;
                }

                //登录BaseLoginActivity调用接口
                login(password,phone);
            }
        });
        //注册按钮点击事件
        binding.register.setOnClickListener(v -> startActivity(RegisterActivity.class));

    }
}