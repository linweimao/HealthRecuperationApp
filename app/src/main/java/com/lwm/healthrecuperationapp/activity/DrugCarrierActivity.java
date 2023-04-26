package com.lwm.healthrecuperationapp.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.DrugInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

// 服药提醒功能载体
public class DrugCarrierActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = DrugCarrierActivity.class.getSimpleName();

    private ImageView mImgDrugInfoReturn;
    private LinearLayout mLineScanDrugs;
    private LinearLayout mLineDrugList;

    @Override
    protected int initLayout() {
        return R.layout.activity_drug_carrier;
    }

    @Override
    protected void initView() {
        mImgDrugInfoReturn = (ImageView) findViewById(R.id.img_drug_info_return);
        mLineScanDrugs = (LinearLayout) findViewById(R.id.line_scan_drugs);
        mLineDrugList = (LinearLayout) findViewById(R.id.line_drug_list);
        mImgDrugInfoReturn.setOnClickListener(this);
        mLineScanDrugs.setOnClickListener(this);
        mLineDrugList.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_drug_info_return:  // 返回按钮
                finish();
                break;
            case R.id.line_scan_drugs:
                new IntentIntegrator(this).setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                        .setCaptureActivity(ScanActivity.class) // 设置打开摄像头的Activity
                        .setPrompt(getResources().getString(R.string.please_align_the_qr_code))// 设置提示语
                        .setCameraId(0) // 选择摄像头,可使用前置或者后置
                        .setBeepEnabled(true) // 是否开启声音,扫完码之后会"哔"的一声
                        .initiateScan();// 初始化扫码
                break;
            case R.id.line_drug_list:
                navigateTo(DrugListActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫码结果
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                // 扫码失败
                showToast(getResources().getString(R.string.scan_code_failed));
            } else {
                String result = intentResult.getContents().trim(); // 返回值
                Log.d(TAG, "drugbarcode: " + result);
                getScanCodeDrugInfo(result);
            }
        }
    }

    // 扫码检测药品信息是否已经录入
    private void getScanCodeDrugInfo(String result) {
        BmobQuery<DrugInfo> drugBarcodeQuery = new BmobQuery<DrugInfo>();
        drugBarcodeQuery.addWhereEqualTo("drugbarcode", result.trim());
        drugBarcodeQuery.findObjects(new FindListener<DrugInfo>() {
            @Override
            public void done(List<DrugInfo> list, BmobException e) {
                if (list.size() == 0 || list.get(0).getDrugbarcode().isEmpty() || "".equals(list.get(0).getDrugbarcode())) {
                    showToastLengthLong(getResources().getString(R.string.drug_inf_not_entered));
                } else {
                    Intent intent = new Intent(DrugCarrierActivity.this, DrugInfoActivity.class);
                    intent.putExtra("barcodeobjectid", list.get(0).getObjectId());
                    startActivity(intent);
                }
            }
        });
    }
}