package com.lwm.healthrecuperationapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.activity.ContactUsActivity;
import com.lwm.healthrecuperationapp.activity.LoginActivity;
import com.lwm.healthrecuperationapp.activity.MyCollectActivity;
import com.lwm.healthrecuperationapp.activity.SetActivity;
import com.lwm.healthrecuperationapp.util.CallPhoneUtil;
import com.lwm.healthrecuperationapp.util.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

public class MyFragment extends BaseFragment {

    // 填写紧急联系人弹框
    private ImageView mSafetyDetailClose;
    private EditText mEtEmergencyContactOne, mEtEmergencyContactTwo;
    private TextView mBtnEmergencyContact;
    private AlertDialog.Builder mDialog;
    private RelativeLayout mRlAddEmergencyContact;
    private RelativeLayout mRlEmergencyContact;
    private View mDividerEmergencyContact;
    // 拨打紧急联系人弹框
    private TextView mTvEmergencyContactOne;
    private TextView mTvEmergencyContactTwo;
    private TextView mBtnResetEmergencyContacts;
    private String mName; // 用户名
    private TextView mTvName;

    @BindView(R.id.img_header)
    ImageView imgHeader;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView() {
        mRlAddEmergencyContact = mRootView.findViewById(R.id.rl_add_emergency_contact); // 添加紧急联系人
        mRlEmergencyContact = mRootView.findViewById(R.id.rl_emergency_contact); // 紧急联系人
        mDividerEmergencyContact = mRootView.findViewById(R.id.divider_emergency_contact);
        mTvName = mRootView.findViewById(R.id.tv_name);
        if (StringUtils.isEmpty(getStringFromSp("emphonenum1"))
                && StringUtils.isEmpty(getStringFromSp("emphonenum2"))) {
            mRlAddEmergencyContact.setVisibility(View.VISIBLE);
            mRlEmergencyContact.setVisibility(View.GONE);
            mDividerEmergencyContact.setVisibility(View.GONE);
        } else {
            mRlAddEmergencyContact.setVisibility(View.GONE);
            mRlEmergencyContact.setVisibility(View.VISIBLE);
            mDividerEmergencyContact.setVisibility(View.VISIBLE);
        }
        mDialog = new AlertDialog.Builder(getActivity());
        getName();
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_header, R.id.rl_collect, R.id.rl_skin, R.id.rl_emergency_contact,
            R.id.rl_contact_us, R.id.rl_setting, R.id.rl_logout, R.id.btn_add_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_header: // 头像
                break;
            case R.id.btn_add_now: // 添加紧急联系人
                showDialogEmergencyContactList();
                break;
            case R.id.rl_collect: // 我的收藏
                navigateTo(MyCollectActivity.class);
                break;
            case R.id.rl_skin: // 换肤
                String skin = getStringFromSp("skin");
                if (skin.equals("night")) {
                    // 恢复应用默认皮肤
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    saveStringToSp("skin", "default");
                } else {
                    /**
                     * "night": 皮肤包名称 与 res-night文件的 night文件名保持一致
                     * SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN: 皮肤包加载策略(_night命名在后面)
                     */
                    SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
                    saveStringToSp("skin", "night");
                }
                break;
            case R.id.rl_emergency_contact: // 紧急联系人
                showDialogMakingEmergencyCalls();
                break;
            case R.id.rl_contact_us: // 联系我们
                navigateTo(ContactUsActivity.class);
                break;
            case R.id.rl_setting: // 设置
                navigateTo(SetActivity.class);
                break;
            case R.id.rl_logout: // 退出登录
                removeByKey("token");
                navigateToWithFlag(LoginActivity.class,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }
    }

    // 填写紧急联系人弹框
    private void showDialogEmergencyContactList() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_emergency_contact_list, null);
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
                mRlAddEmergencyContact.setVisibility(View.VISIBLE);
                mRlEmergencyContact.setVisibility(View.GONE);
                mDividerEmergencyContact.setVisibility(View.GONE);
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
                mRlAddEmergencyContact.setVisibility(View.GONE);
                mRlEmergencyContact.setVisibility(View.VISIBLE);
                mDividerEmergencyContact.setVisibility(View.VISIBLE);
            }
        });
    }


    // 拨打紧急联系人弹框
    private void showDialogMakingEmergencyCalls() {
        View dialogView = View.inflate(getActivity(), R.layout.dialog_making_emergency_calls, null);
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
                CallPhoneUtil.checkPermission(getActivity(), Manifest.permission.CALL_PHONE);
            }
        });
        mTvEmergencyContactTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallPhoneUtil.setPhoneNumber(mTvEmergencyContactTwo.getText().toString().trim());
                CallPhoneUtil.checkPermission(getActivity(), Manifest.permission.CALL_PHONE);
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
                    CallPhoneUtil.call(getActivity());
                } else {
                    showToast(getResources().getString(R.string.my_contact_us_unauthorized_temporarily));
                }
            default:
        }
    }

    // 通过 Sp获取用户名
    private void getName() {
        mName = getStringFromSp("account");
        if (mName.isEmpty()) {
            mTvName.setText("");
        } else {
            mTvName.setText(mName);
        }
    }
}