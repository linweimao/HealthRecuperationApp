package com.lwm.healthrecuperationapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.NurseInfo;
import com.lwm.healthrecuperationapp.util.CallPhoneUtil;
import com.lwm.healthrecuperationapp.util.DialogUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class NurseInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImgNurseInfoReturn;
    private TextView mTvName, mTvSex, mTvAge, mTvPhoneNumber, mTvAddress, mTvTypeOfCare, mTvNursingYears;

    @Override
    protected int initLayout() {
        return R.layout.activity_nurse_info;
    }

    @Override
    protected void initView() {
        mImgNurseInfoReturn = (ImageView) findViewById(R.id.img_nurse_info_return);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvSex = (TextView) findViewById(R.id.tv_sex);
        mTvAge = (TextView) findViewById(R.id.tv_age);
        mTvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);
        mTvAddress = (TextView) findViewById(R.id.tv_address);
        mTvTypeOfCare = (TextView) findViewById(R.id.tv_type_of_care);
        mTvNursingYears = (TextView) findViewById(R.id.tv_nursing_years);
        mImgNurseInfoReturn.setOnClickListener(this);
        mTvPhoneNumber.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getNurseInfo(getIntent().getStringExtra("objectId"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_nurse_info_return: // 返回按钮
                finish();
                break;
            case R.id.tv_phone_number:
                String phoneNumber = mTvPhoneNumber.getText().toString().trim();
                View dialogView = View.inflate(NurseInfoActivity.this, R.layout.dialog_callphone_view, null);
                DialogUtil.getInstance().createDialog(NurseInfoActivity.this, dialogView,
                        phoneNumber, new DialogUtil.DialogOnClick() {
                            @Override
                            public void PositiveOnClick() {
                                CallPhoneUtil.setPhoneNumber(phoneNumber);
                                CallPhoneUtil.checkPermission(NurseInfoActivity.this, Manifest.permission.CALL_PHONE);
                            }

                            @Override
                            public void NegativeOnClick() {
                            }
                        });
                break;
        }
    }

    // 查询护工信息
    private void getNurseInfo(String objectId) {
        BmobQuery<NurseInfo> query = new BmobQuery<NurseInfo>();
        query.getObject(objectId, new QueryListener<NurseInfo>() {
            @Override
            public void done(NurseInfo nurseInfo, BmobException e) {
                if (e == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvName.setText(nurseInfo.getUsername());
                            mTvSex.setText(nurseInfo.getSex());
                            mTvAge.setText(String.valueOf(nurseInfo.getAge()));
                            mTvPhoneNumber.setText(nurseInfo.getMobile());
                            mTvAddress.setText(nurseInfo.getAddress());
                            mTvTypeOfCare.setText(nurseInfo.getNursingtype());
                            mTvNursingYears.setText(String.valueOf(nurseInfo.getNursingyears()));
                        }
                    });
                } else {
                    mTvName.setText("");
                    mTvSex.setText("");
                    mTvAge.setText("");
                    mTvPhoneNumber.setText("");
                    mTvAddress.setText("");
                    mTvTypeOfCare.setText("");
                    mTvNursingYears.setText("");
                    showToast(getResources().getString(R.string.query_failed) + ": " + e.getMessage());
                }
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