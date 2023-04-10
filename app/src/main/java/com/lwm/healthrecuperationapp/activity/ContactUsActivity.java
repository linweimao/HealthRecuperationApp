package com.lwm.healthrecuperationapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.util.CallPhoneUtil;
import com.lwm.healthrecuperationapp.util.DialogUtil;

public class ContactUsActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBtnImgReturn;
    private TextView mTvContactUsPhoneNumber;
    private String mDialogTitle, mDialogMessage, mPhoneNumber;
    private String mDialogPositivebtn, mDialogNegativebtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void initView() {
        mBtnImgReturn = (ImageView) findViewById(R.id.btn_img_return);
        mTvContactUsPhoneNumber = (TextView) findViewById(R.id.tv_contact_us_phone_number);
        mBtnImgReturn.setOnClickListener(this);
        mTvContactUsPhoneNumber.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mDialogTitle = getResources().getString(R.string.my_contact_us_dialog_title); // 弹框标题
        mDialogMessage = getResources().getString(R.string.my_contact_us_dialog_message); // 弹框内容
        mPhoneNumber = mTvContactUsPhoneNumber.getText().toString().trim(); // 手机号码
        mDialogPositivebtn = getResources().getString(R.string.my_contact_us_dialog_positivebtn); // 弹框确认按钮
        mDialogNegativebtn = getResources().getString(R.string.my_contact_us_dialog_negativebtn); // 弹框取消按钮
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_img_return: //  返回上一层
                finish();
                break;
            case R.id.tv_contact_us_phone_number: // 号码文字按钮
                View dialogView = View.inflate(ContactUsActivity.this, R.layout.dialog_callphone_view, null);
                DialogUtil.getInstance().createDialog(ContactUsActivity.this, dialogView,
                        mPhoneNumber, new DialogUtil.DialogOnClick() {
                            @Override
                            public void PositiveOnClick() {
                                CallPhoneUtil.setPhoneNumber(mPhoneNumber);
                                CallPhoneUtil.checkPermission(ContactUsActivity.this, Manifest.permission.CALL_PHONE);
                            }

                            @Override
                            public void NegativeOnClick() {

                            }
                        });
                break;
        }
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