package com.lwm.healthrecuperationapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lwm.healthrecuperationapp.R;
import com.lwm.healthrecuperationapp.entity.NewsInfo;

import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {

    private Context mContext;
    private List<NewsInfo> mDatas;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;

    public HealthAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<NewsInfo> datas) {
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public HealthAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_health_layout, parent, false);
        HealthAdapter.ViewHolder viewHolder = new HealthAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HealthAdapter.ViewHolder holder, int position) {
        NewsInfo newsInfo = mDatas.get(position);
        holder.tvHealthTitle.setText(newsInfo.getTitle());
        holder.tvHealthAuthor.setText(String.format(
                mContext.getResources().getString(R.string.health_author), newsInfo.getAuthor_name()));
        holder.tvHealthTime.setText(String.format(
                mContext.getResources().getString(R.string.health_time), newsInfo.getDate()));
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

        private TextView tvHealthTitle; // 标题
        private TextView tvHealthAuthor; // 作者
        private TextView tvHealthTime; // 时间

        public ViewHolder(View view) {
            super(view);
            tvHealthTitle = (TextView) view.findViewById(R.id.tv_health_title);
            tvHealthAuthor = (TextView) view.findViewById(R.id.tv_health_author);
            tvHealthTime = (TextView) view.findViewById(R.id.tv_health_time);
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