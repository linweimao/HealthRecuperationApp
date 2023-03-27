package com.lwm.healthrecuperationapp.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lwm.healthrecuperationapp.MainActivity;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.api.Api;
import com.lwm.healthrecuperationapp.api.ApiConfig;
import com.lwm.healthrecuperationapp.api.RequestCallback;
import com.lwm.healthrecuperationapp.entity.LoginResponse;
import com.lwm.healthrecuperationapp.util.StringUtils;

import java.util.HashMap;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtAccount;
    private EditText mEtPwd;
    private ImageButton mBtnReturn;
    private Button mBtnLogin;
    private TextView mBtnRegister;
    private ImageView mImgPwdOpenClose;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mBtnReturn = (ImageButton) findViewById(R.id.btn_return);
        mEtAccount = (EditText) findViewById(R.id.et_account);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (TextView) findViewById(R.id.btn_register);
        mImgPwdOpenClose = (ImageView) findViewById(R.id.img_pwd_open_close);
        mBtnReturn.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mImgPwdOpenClose.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return: // 返回图标按钮
                navigateTo(MainActivity.class);
                finish();
                break;
            case R.id.btn_login: // 登录按钮
                String account = mEtAccount.getText().toString().trim();
                String pwd = mEtPwd.getText().toString().trim();
                if (StringUtils.isEmpty(account)) {
                    showToast(getString(R.string.account_hint));
                    return;
                }
                if (StringUtils.isEmpty(pwd)) {
                    showToast(getString(R.string.pwd_hint));
                    return;
                }
                saveStringToSp("account", account);
                login(account, pwd);
                break;
            case R.id.img_pwd_open_close: // 隐藏显示密码
                mImgPwdOpenClose.setSelected(!mImgPwdOpenClose.isSelected());
                if (mImgPwdOpenClose.isSelected()) {
                    mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());
                } else {
                    mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEtPwd.setSelection(mEtPwd.getText().toString().length());
                }
                break;
            case R.id.btn_register: // 去注册文字按钮
                navigateTo(RegisterActivity.class);
                break;
        }
    }

    // 采用封装 okhttp 的方式
    private void login(String account, String pwd) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.LOGIN, params).postRequest(this, new RequestCallback() {
            @Override
            public void onSuccess(String res) {
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0) {
                    String token = loginResponse.getToken();
                    saveStringToSp("token", token);
                    /**
                     * 跳转到 HomeActivity页面时，将之前的所有Activity清除掉
                     *    FLAG_ACTIVITY_CLEAR_TASK：将栈中的其它 Activity清除掉
                     *    FLAG_ACTIVITY_NEW_TASK：创建一个新的栈，在启动 Activity
                     */
                    navigateToWithFlag(HomeActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    showToastSync(getString(R.string.login_success));
                } else {
                    showToastSync(getString(R.string.login_fail));
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.getMessage());
            }
        });
    }
}