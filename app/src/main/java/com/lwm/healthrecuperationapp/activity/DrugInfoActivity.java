package com.lwm.healthrecuperationapp.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.DrugInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

// 药品信息
public class DrugInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = DrugInfoActivity.class.getSimpleName();

    private ImageView mImgDrugInfoReturn;
    private TextView mTvDrugBarcode, mTvDrugName, mTvEnterpriseName, mTvBrandName, mTvDrugSpecifications, mTvDrugsUsageAndDosage, mTvAdministrationTime, mTvAddMedication;

    @Override
    protected int initLayout() {
        return R.layout.activity_drug_info;
    }

    @Override
    protected void initView() {
        mImgDrugInfoReturn = (ImageView) findViewById(R.id.img_drug_info_return);
        mTvDrugBarcode = (TextView) findViewById(R.id.tv_drug_barcode); // 药品条码
        mTvDrugName = (TextView) findViewById(R.id.tv_drug_name); // 药品名称
        mTvEnterpriseName = (TextView) findViewById(R.id.tv_enterprise_name); // 企业名称
        mTvBrandName = (TextView) findViewById(R.id.tv_brand_name); // 品牌名称
        mTvDrugSpecifications = (TextView) findViewById(R.id.tv_drug_specifications); // 药品规格
        mTvDrugsUsageAndDosage = (TextView) findViewById(R.id.tv_drugs_usage_and_dosage); // 用法用量
        mTvAdministrationTime = (TextView) findViewById(R.id.tv_administration_time); // 服药时间
        mTvAddMedication = (TextView) findViewById(R.id.tv_add_medication); // 添加药品按钮
        mImgDrugInfoReturn.setOnClickListener(this);
        mTvAddMedication.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getDrugInfo(getIntent().getStringExtra("barcodeobjectid"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_drug_info_return:  // 返回按钮
                finish();
                break;
            case R.id.tv_add_medication: // 添加药品

                break;
        }
    }

    // 查询药品信息
    private void getDrugInfo(String objectid) {
        BmobQuery<DrugInfo> query = new BmobQuery<DrugInfo>();
        query.getObject(objectid, new QueryListener<DrugInfo>() {
            @Override
            public void done(DrugInfo drugInfo, BmobException e) {
                if (e == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTvDrugBarcode.setText(drugInfo.getDrugbarcode()); // 药品条码
                            mTvDrugName.setText(drugInfo.getDrugname()); // 药品名称
                            mTvEnterpriseName.setText(drugInfo.getEnterprisename()); // 企业名称
                            mTvBrandName.setText(drugInfo.getBrandname()); // 品牌名称
                            mTvDrugSpecifications.setText(drugInfo.getDrugspecifications()); // 药品规格
                            mTvDrugsUsageAndDosage.setText(drugInfo.getDrugsusageanddosage()); // 药品用法用量
                            mTvAdministrationTime.setText(drugInfo.getAdministrationtime()); // 服药时间
                        }
                    });
                } else {
                    mTvDrugBarcode.setText("");
                    mTvDrugName.setText("");
                    mTvEnterpriseName.setText("");
                    mTvBrandName.setText("");
                    mTvDrugSpecifications.setText("");
                    mTvDrugsUsageAndDosage.setText("");
                    mTvAdministrationTime.setText("");
                    Log.d(TAG, getResources().getString(R.string.query_failed) + ": " + e.getMessage());
                    showToast(getResources().getString(R.string.query_failed) + ": " + e.getMessage());
                }
            }
        });
    }
}