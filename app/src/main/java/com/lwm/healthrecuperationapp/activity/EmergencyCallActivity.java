package com.lwm.healthrecuperationapp.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwm.healthrecuperationapp.R;

public class EmergencyCallActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnImgReturn;
    private LinearLayout mLinVideoCall;
    private LinearLayout mLinEmergencyCall;

    @Override
    protected int initLayout() {
        return R.layout.activity_emergency_call;
    }

    @Override
    protected void initView() {
        mBtnImgReturn = (ImageView) findViewById(R.id.btn_img_return);
        mLinVideoCall = (LinearLayout) findViewById(R.id.lin_video_call);
        mLinEmergencyCall = (LinearLayout) findViewById(R.id.lin_emergency_call);
        mBtnImgReturn.setOnClickListener(this);
        mLinVideoCall.setOnClickListener(this);
        mLinEmergencyCall.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_img_return: // 返回按钮
                finish();
                break;
            case R.id.lin_video_call: // 视频通话
                navigateTo(VideoChatActivity.class);
                break;
            case R.id.lin_emergency_call: // 紧急呼叫

                break;
        }
    }
}