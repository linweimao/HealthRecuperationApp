package com.lwm.healthrecuperationapp;

import android.view.View;
import android.widget.Button;

import com.lwm.healthrecuperationapp.activity.BaseActivity;
import com.lwm.healthrecuperationapp.activity.HomeActivity;
import com.lwm.healthrecuperationapp.activity.LoginActivity;
import com.lwm.healthrecuperationapp.activity.RegisterActivity;
import com.lwm.healthrecuperationapp.util.StringUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnLogin;
    private Button mBtnRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {
        // 免密登录(第二次启动应用直接进入程序)
        if (!StringUtils.isEmpty(getStringFromSp("token"))) {
            navigateTo(HomeActivity.class);
            finish();
        }
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 登录
            case R.id.btn_login:
                navigateTo(LoginActivity.class);
                break;
            // 注册
            case R.id.btn_register:
                navigateTo(RegisterActivity.class);
                break;
        }
    }
}