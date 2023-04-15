package com.lwm.healthrecuperationapp.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lwm.healthrecuperationapp.R;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnImgReturn;
    private RelativeLayout mRlAboutUs;

    @Override
    protected int initLayout() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        mBtnImgReturn = (ImageView) findViewById(R.id.btn_img_return);
        mRlAboutUs = (RelativeLayout) findViewById(R.id.rl_about_us);
        mBtnImgReturn.setOnClickListener(this);
        mRlAboutUs.setOnClickListener(this);
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
            case R.id.rl_about_us:
                navigateTo(AboutUsActivity.class);
                break;
        }
    }
}