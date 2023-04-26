package com.lwm.healthrecuperationapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.NurseInfo;

import java.util.List;

public class NursingListAdapter extends RecyclerView.Adapter<NursingListAdapter.ViewHolder> {

    private Context mContext;
    private List<NurseInfo> mDatas;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;

    public NursingListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<NurseInfo> datas) {
        this.mDatas = datas;
    }

    @Override
    public NursingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nursing_list_item, parent, false);
        NursingListAdapter.ViewHolder viewHolder = new NursingListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NursingListAdapter.ViewHolder holder, int position) {
        NurseInfo nurseInfo = mDatas.get(position);
        holder.tvNurseId.setText(nurseInfo.getObjectId()); // 护工编号
        holder.tvNurseName.setText(nurseInfo.getUsername()); // 护工姓名
        holder.tvNursePhoneNumber.setText(nurseInfo.getMobile()); // 护工手机号码
        holder.tvNurseTypeOfCare.setText(nurseInfo.getNursingtype()); // 护工护理类型
        holder.tvNurseCreationTime.setText(nurseInfo.getCreatedAt()); // 创建时间
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

        private TextView tvNurseId; // 护工编号
        private TextView tvNurseName; // 护工姓名
        private TextView tvNursePhoneNumber; // 护工手机号码
        private TextView tvNurseTypeOfCare; // 护工护理类型
        private TextView tvNurseCreationTime; // 创建时间

        public ViewHolder(View view) {
            super(view);
            tvNurseId = (TextView) view.findViewById(R.id.tv_nurse_id);
            tvNurseName = (TextView) view.findViewById(R.id.tv_nurse_name);
            tvNursePhoneNumber = (TextView) view.findViewById(R.id.tv_nurse_phone_number);
            tvNurseTypeOfCare = (TextView) view.findViewById(R.id.tv_nurse_type_of_care);
            tvNurseCreationTime = (TextView) view.findViewById(R.id.tv_nurse_creation_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onclick(view);
                }
            });
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onclick(View view);
    }
}
