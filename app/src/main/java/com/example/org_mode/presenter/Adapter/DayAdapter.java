package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.DayViewHolder;
import com.example.org_mode.utils.ScheduleDate;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;
import com.example.org_mode.view.EditActivity;
import com.example.org_mode.view.MainActivity;
import com.example.org_mode.view.setFileActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.org_mode.model.FileResolution.getExpiredItems;
import static com.example.org_mode.model.FileResolution.getFileNames;
import static com.example.org_mode.model.FileResolution.getItembyDate;
import static com.example.org_mode.utils.DateUtils.convertDate;
import static com.example.org_mode.utils.DateUtils.getToday;
import static com.example.org_mode.utils.DateUtils.isToday;

public class DayAdapter extends RecyclerView.Adapter<DayViewHolder> {
    @NonNull

    public Context mContext;
    public List<ScheduleDate> datas;
    public String SearchString = "";

    public DayAdapter(Context context){
        mContext = context;
    }

    public void setSearchString(String searchString) {
        SearchString = searchString;
        notifyDataSetChanged();
    }

    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_item, parent, false);
        return new DayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String Text = "";
        List<ScheduleItem> items = getItembyDate(datas.get(position), SearchString);

        if(isToday(convertDate(datas.get(position)))){
            Text = "Today," + datas.get(position).getText();
            holder.DayDate.setTextColor(mContext.getResources().getColor(R.color.light_blue));
            items.removeAll(getExpiredItems(getToday()));
            items.addAll(getExpiredItems(getToday()));
        }else{
            Text = datas.get(position).getText();
            holder.DayDate.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        holder.DayDate.setText(Text);
        if(!items.isEmpty()) {
            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(mContext);
            scheduleAdapter.setData(items);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(linearLayoutManager);

            holder.recyclerView.setAdapter(scheduleAdapter);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setVisibility(View.VISIBLE);
            scheduleAdapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position, ScheduleItem scheduleItem) {
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra("SchduleItem", scheduleItem.printItem());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<ScheduleDate> Data){
        this.datas = Data;
        notifyDataSetChanged();
    }

    public List<String> dataFortest(){
        List<String> Data = new ArrayList<String>();
        for(int i=0; i<3; i++){
            Data.add("afes");
        }
        return Data;
    }
}
