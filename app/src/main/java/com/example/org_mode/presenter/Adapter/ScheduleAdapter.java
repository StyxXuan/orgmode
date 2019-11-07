package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.ScheduleViewHolder;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    @NonNull
    private List<String> datas;
    private Context mContext;

    public ScheduleAdapter(Context context){
        mContext = context;
    }

    public void setData(List<String> Data){
        this.datas = Data;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schdule_item, parent, false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.contentText.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
