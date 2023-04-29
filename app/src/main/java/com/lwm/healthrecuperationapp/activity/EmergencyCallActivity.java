package com.lwm.healthrecuperationapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.util.CallPhoneUtil;
import com.lwm.healthrecuperationapp.util.StringUtils;

public class EmergencyCallActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnImgReturn;
    private LinearLayout mLinVideoCall;
    private LinearLayout mLinEmergencyCall;
    // 填写紧急联系人弹框
    private ImageView mSafetyDetailClose;
    private EditText mEtEmergencyContactOne, mEtEmergencyContactTwo;
    private TextView mBtnEmergencyContact;
    private AlertDialog.Builder mDialog;
    // 拨打紧急联系人弹框
    private TextView mTvEmergencyContactOne;
    private TextView mTvEmergencyContactTwo;
    private TextView mBtnResetEmergencyContacts;

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
        mDialog = new AlertDialog.Builder(EmergencyCallActivity.this);
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
                // emphonenum1：紧急联系人1
                // emphonenum2：紧急联系人2
                if (StringUtils.isEmpty(getStringFromSp("emphonenum1"))
                        && StringUtils.isEmpty(getStringFromSp("emphonenum2"))) {
                    showDialogEmergencyContactList();
                } else {
                    showDialogMakingEmergencyCalls();
                }
                break;
        }
    }

    // 填写紧急联系人弹框
    private void showDialogEmergencyContactList() {
        View dialogView = View.inflate(EmergencyCallActivity.this, R.layout.dialog_emergency_contact_list, null);
        AlertDialog emPhoneNumDialog = mDialog.setView(dialogView).setCancelable(true).create();
        emPhoneNumDialog.show();
        mSafetyDetailClose = (ImageView) dialogView.findViewById(R.id.safety_detail_close);
        mEtEmergencyContactOne = (EditText) dialogView.findViewById(R.id.et_emergency_contact_one);
        mEtEmergencyContactTwo = (EditText) dialogView.findViewById(R.id.et_emergency_contact_two);
        mBtnEmergencyContact = (TextView) dialogView.findViewById(R.id.btn_emergency_contact);
        // 关闭图标
        mSafetyDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emPhoneNumDialog.dismiss();
            }
        });
        // 确定按钮
        mBtnEmergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emergencyContactOne = mEtEmergencyContactOne.getText().toString().trim(); // 紧急联系人1
                String emergencyContactTwo = mEtEmergencyContactTwo.getText().toString().trim(); // 紧急联系人2
                if (StringUtils.isEmpty(emergencyContactOne)) {
                    showToast(getResources().getString(R.string.emergency_contact1));
                    return;
                }
                if (StringUtils.isEmpty(emergencyContactTwo)) {
                    showToast(getResources().getString(R.string.emergency_contact2));
                    return;
                }
                saveStringToSp("emphonenum1", emergencyContactOne); // 紧急联系人1
                saveStringToSp("emphonenum2", emergencyContactTwo); // 紧急联系人2
                emPhoneNumDialog.dismiss();
            }
        });
    }

    // 拨打紧急联系人弹框
    private void showDialogMakingEmergencyCalls() {
        View dialogView = View.inflate(EmergencyCallActivity.this, R.layout.dialog_making_emergency_calls, null);
        AlertDialog emPhoneNumDialog = mDialog.setView(dialogView).setCancelable(true).create();
        emPhoneNumDialog.show();
        mSafetyDetailClose = (ImageView) dialogView.findViewById(R.id.safety_detail_close);
        mTvEmergencyContactOne = (TextView) dialogView.findViewById(R.id.tv_emergency_contact_one);
        mTvEmergencyContactTwo = (TextView) dialogView.findViewById(R.id.tv_emergency_contact_two);
        mBtnResetEmergencyContacts = (TextView) dialogView.findViewById(R.id.btn_reset_emergency_contacts);
        mTvEmergencyContactOne.setText(getStringFromSp("emphonenum1"));
        mTvEmergencyContactTwo.setText(getStringFromSp("emphonenum2"));
        // 关闭图标
        mSafetyDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emPhoneNumDialog.dismiss();
            }
        });
        mTvEmergencyContactOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhoneUtil.setPhoneNumber(mTvEmergencyContactOne.getText().toString().trim());
                CallPhoneUtil.checkPermission(EmergencyCallActivity.this, Manifest.permission.CALL_PHONE);
            }
        });
        mTvEmergencyContactTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhoneUtil.setPhoneNumber(mTvEmergencyContactTwo.getText().toString().trim());
                CallPhoneUtil.checkPermission(EmergencyCallActivity.this, Manifest.permission.CALL_PHONE);
            }
        });
        mBtnResetEmergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeByKey("emphonenum1");
                removeByKey("emphonenum2");
                emPhoneNumDialog.dismiss();
                showDialogEmergencyContactList();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallPhoneUtil.call(this);
                } else {
                    showToast(getResources().getString(R.string.my_contact_us_unauthorized_temporarily));
                }
            default:
        }
    }
}