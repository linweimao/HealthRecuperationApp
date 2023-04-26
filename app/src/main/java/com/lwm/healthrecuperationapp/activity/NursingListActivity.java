package com.lwm.healthrecuperationapp.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.adapter.NursingListAdapter;
import com.lwm.healthrecuperationapp.entity.NurseInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NursingListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = NursingListActivity.class.getSimpleName();
    private ImageView mImgNursingListReturn;
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
        mImgNursingListReturn = (ImageView) findViewById(R.id.img_nursing_list_return);
        mRvNursingList = (RecyclerView) findViewById(R.id.rv_nursing_list);
        mImgNursingListReturn.setOnClickListener(this);
        mLinearLayoutManager = new LinearLayoutManager(NursingListActivity.this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvNursingList.setLayoutManager(mLinearLayoutManager);
        mNursingListAdapter = new NursingListAdapter(NursingListActivity.this);
        mRvNursingList.setAdapter(mNursingListAdapter);
        mNursingListAdapter.setOnItemClick(new NursingListAdapter.OnItemClick() {
            @Override
            public void onclick(View view) {
                int position = mRvNursingList.getChildAdapterPosition(view);
                Intent nurseInfoIntent = new Intent(NursingListActivity.this, NurseInfoActivity.class);
                nurseInfoIntent.putExtra("objectId", mNurseInfos.get(position).getObjectId().trim());
                startActivity(nurseInfoIntent);
            }
        });
    }

    @Override
    protected void initData() {
        BmobQuery<NurseInfo> query = new BmobQuery<NurseInfo>();
//        // 按照更新时间升序显示数据
//        query.order("updatedAt");
        // 按照更新时间降序显示数据
        query.order("-updatedAt");
        // 执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<NurseInfo>() {
            @Override
            public void done(List<NurseInfo> list, BmobException e) {
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