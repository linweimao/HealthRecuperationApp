package com.lwm.healthrecuperationapp.activity;

import android.view.View;
import android.widget.ImageView;

import com.lwm.healthrecuperationapp.R;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnImgReturn;

    @Override
    protected int initLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        mBtnImgReturn = (ImageView) findViewById(R.id.btn_img_return);
        mBtnImgReturn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_img_return: //  返回上一层
                finish();
                break;
        }
    }
}