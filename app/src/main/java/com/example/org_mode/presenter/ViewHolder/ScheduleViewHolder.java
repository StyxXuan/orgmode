package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.org_mode.R;


public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    public final TextView fileText;
    public final TextView contentText;
    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        fileText = itemView.findViewById(R.id.itemfile);
        contentText = itemView.findViewById(R.id.itemcontent);
    }
}
