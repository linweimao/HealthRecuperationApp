package com.lwm.healthrecuperationapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.DrugInfo;

import java.util.List;

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.ViewHolder> {

    private Context mContext;
    private List<DrugInfo> mDatas;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;

    public DrugListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<DrugInfo> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drug_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrugInfo drugInfo = mDatas.get(position);
        holder.mTvDrugBarcode.setText("：" + drugInfo.getDrugbarcode()); // 药品条码
        holder.mTvDrugName.setText("：" + drugInfo.getDrugname()); // 药品名称
        holder.mTvEnterpriseName.setText("：" + drugInfo.getEnterprisename()); // 企业名称
        holder.mTvBrandName.setText("：" + drugInfo.getBrandname()); // 品牌名称
        holder.mTvDrugSpecifications.setText("：" + drugInfo.getDrugspecifications()); // 药品规格
        holder.mTvDrugsUsageAndDosage.setText("：" + drugInfo.getDrugsusageanddosage()); // 药品用法用量
        holder.mTvDrugsEntryTime.setText("：" + drugInfo.getCreatedAt()); // 录入时间
        holder.mTvAdministrationTime.setText("：" + drugInfo.getAdministrationtime()); // 服药时间
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDrugBarcode; // 药品条码
        private TextView mTvDrugName; // 药品名称
        private TextView mTvEnterpriseName; // 企业名称
        private TextView mTvBrandName; // 品牌名称
        private TextView mTvDrugSpecifications; // 药品规格
        private TextView mTvDrugsUsageAndDosage; // 药品用法用量
        private TextView mTvDrugsEntryTime; // 录入时间
        private TextView mTvSetReminders; // 设置提醒
        private TextView mTvAdministrationTime; // 服药时间

        public ViewHolder(View view) {
            super(view);
            mTvDrugBarcode = (TextView) view.findViewById(R.id.tv_drug_barcode);
            mTvDrugName = (TextView) view.findViewById(R.id.tv_drug_name);
            mTvEnterpriseName = (TextView) view.findViewById(R.id.tv_enterprise_name);
            mTvBrandName = (TextView) view.findViewById(R.id.tv_brand_name);
            mTvDrugSpecifications = (TextView) view.findViewById(R.id.tv_drug_specifications);
            mTvDrugsUsageAndDosage = (TextView) view.findViewById(R.id.tv_drugs_usage_and_dosage);
            mTvDrugsEntryTime = (TextView) view.findViewById(R.id.tv_drugs_entry_time);
            mTvAdministrationTime = (TextView) view.findViewById(R.id.tv_administration_time);
            mTvSetReminders = (TextView) view.findViewById(R.id.tv_set_reminders);
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onclick(View view);
    }
}
