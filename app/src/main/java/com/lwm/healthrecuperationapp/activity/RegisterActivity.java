package com.lwm.healthrecuperationapp.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.MainActivity;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.api.Api;
import com.lwm.healthrecuperationapp.api.ApiConfig;
import com.lwm.healthrecuperationapp.api.RequestCallback;
import com.lwm.healthrecuperationapp.util.StringUtils;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtAccount;
    private EditText mEtPwd;
    private Button mBtnRegister;
    private ImageView mImgPwdOpenClose;
    private ImageButton mBtnReturn;
    private TextView mBtnLogIn;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    protected void initView() {
        mBtnReturn = (ImageButton) findViewById(R.id.btn_return);
        mEtAccount = (EditText) findViewById(R.id.et_account);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mImgPwdOpenClose = (ImageView) findViewById(R.id.img_pwd_open_close);
        mBtnLogIn = (TextView) findViewById(R.id.btn_log_in);
        mBtnReturn.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mImgPwdOpenClose.setOnClickListener(this);
        mBtnLogIn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_return: // 返回图标按钮
                finish();
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
            case R.id.btn_register: // 注册按钮
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
                register(account, pwd);
                break;
            case R.id.btn_log_in: // 去登录文字按钮
                navigateTo(LoginActivity.class);
                finish();
                break;
        }
    }

    // 采用封装 okhttp 的方式
    private void register(String account, String pwd) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.REGISTER, params).postRequest(this, new RequestCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(res);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("onFailure", e.getMessage());
            }
        });
    }
}