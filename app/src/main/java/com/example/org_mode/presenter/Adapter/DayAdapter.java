package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.DayViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {
    @NonNull

    private Context mContext;
    private List<String> datas;

    public DayAdapter(Context context){
        mContext = context;
    }

    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false);
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.DayDate.setText(datas.get(position));
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(mContext);
        scheduleAdapter.setData(datas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);

        holder.recyclerView.setAdapter(scheduleAdapter);
        holder.recyclerView.setItemAnimator( new DefaultItemAnimator());
        holder.recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<String> Data){
        this.datas = Data;
    }

    public List<String> dataFortest(){
        List<String> Data = new ArrayList<String>();
        for(int i=0; i<3; i++){
            Data.add("afes");
        }
        return Data;
    }
}
