package com.lwm.healthrecuperationapp.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.DrugListAdapter;
import com.lwm.healthrecuperationapp.entity.DrugInfo;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

// 药品列表
public class DrugListActivity extends BaseActivity {

    private static final String TAG = DrugListActivity.class.getSimpleName();

    private ImageView mImgDrugListReturn;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRvDrugList;
    private LinearLayoutManager mLinearLayoutManager;

    private DrugListAdapter mDrugListAdapter;
    private List<DrugInfo> mDrugInfos;

    @Override
    protected int initLayout() {
        return R.layout.activity_drug_list;
    }

    @Override
    protected void initView() {
        mImgDrugListReturn = (ImageView) findViewById(R.id.img_drug_list_return);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mRvDrugList = (RecyclerView) findViewById(R.id.rv_drug_list);
        mLinearLayoutManager = new LinearLayoutManager(DrugListActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvDrugList.setLayoutManager(mLinearLayoutManager);
        mDrugListAdapter = new DrugListAdapter(DrugListActivity.this);
        mRvDrugList.setAdapter(mDrugListAdapter);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mImgDrugListReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDrugListAdapter.setOnItemClick(new DrugListAdapter.OnItemClick() {
            @Override
            public void onclick(View view) {

            }
        });
        // 刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getDrugInfoList();
            }
        });

    }

    @Override
    protected void initData() {
        getDrugInfoList();
    }

    // 获取药品信息
    private void getDrugInfoList() {
        BmobQuery<DrugInfo> query = new BmobQuery<DrugInfo>();
        // 按照创建时间升序显示数据
        query.order("createdAt");
//        // 按照创建时间降序显示数据
//        query.order("-createdAt");
        query.addWhereEqualTo("nurseid", getStringFromSp("nurseid"));
        // 执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<DrugInfo>() {
            @Override
            public void done(List<DrugInfo> list, BmobException e) {
                if (e == null) {
                    mRefreshLayout.finishRefresh(true); // 将刷新动画关闭
                    mDrugInfos = list;
                    mDrugListAdapter.setDatas(mDrugInfos);
                    mDrugListAdapter.notifyDataSetChanged(); // 通知 RecyclerView 刷新页面(刷新数据)
                } else {
                    Log.d(TAG + " " + getResources().getString(R.string.query_failed), e.getMessage());
                    showToast(getResources().getString(R.string.query_failed));
                }
            }
        });
    }
}