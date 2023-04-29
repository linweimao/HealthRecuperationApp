package com.lwm.healthrecuperationapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.NursingListAdapter;
import com.lwm.healthrecuperationapp.entity.NurseInfo;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NursingListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NursingListActivity.class.getSimpleName();
    private ImageView mImgNursingListReturn;
    private RefreshLayout mRefreshLayout;
    private RecyclerView mRvNursingList;
    private LinearLayoutManager mLinearLayoutManager;
    private NursingListAdapter mNursingListAdapter;
    private List<NurseInfo> mNurseInfos;

    @Override
    protected int initLayout() {
        return R.layout.activity_nursing_list;
    }

    @Override
    protected void initView() {
        // 判断是否绑定了护工编号
        if (!"".equals(getStringFromSp("nurseid"))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NursingListActivity.this);
            builder.setTitle(getResources().getString(R.string.unbinding_prompt))
                    .setMessage(getResources().getString(R.string.nurseinfo_unbinding))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.nurseinfo_determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeByKey("nurseid");
                            dialog.dismiss();
                        }
                    }).setNegativeButton(getResources().getString(R.string.nurseinfo_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
        }
        mImgNursingListReturn = (ImageView) findViewById(R.id.img_nursing_list_return);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        mRvNursingList = (RecyclerView) findViewById(R.id.rv_nursing_list);
        mImgNursingListReturn.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(NursingListActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNursingList.setLayoutManager(mLinearLayoutManager);
        mNursingListAdapter = new NursingListAdapter(NursingListActivity.this);
        mRvNursingList.setAdapter(mNursingListAdapter);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mNursingListAdapter.setOnItemClick(new NursingListAdapter.OnItemClick() {
            @Override
            public void onclick(View view) {
                int position = mRvNursingList.getChildAdapterPosition(view);
                Intent nurseInfoIntent = new Intent(NursingListActivity.this, NurseInfoActivity.class);
                nurseInfoIntent.putExtra("objectId", mNurseInfos.get(position).getObjectId().trim());
                startActivity(nurseInfoIntent);
            }
        });
        // 刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getNurseInfoList();
            }
        });
    }

    @Override
    protected void initData() {
        getNurseInfoList();
    }

    // 获取护工列表信息
    private void getNurseInfoList() {
        BmobQuery<NurseInfo> query = new BmobQuery<NurseInfo>();
//        // 按照更新时间升序显示数据
//        query.order("updatedAt");
        // 按照更新时间降序显示数据
        query.order("-updatedAt");
        // 执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<NurseInfo>() {
            @Override
            public void done(List<NurseInfo> list, BmobException e) {
                mRefreshLayout.finishRefresh(true); // 将刷新动画关闭
                mNurseInfos = list;
                mNursingListAdapter.setDatas(mNurseInfos);
                mNursingListAdapter.notifyDataSetChanged(); // 通知 RecyclerView 刷新页面(刷新数据)
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_nursing_list_return: // 返回按钮
                finish();
                break;
        }
    }
}