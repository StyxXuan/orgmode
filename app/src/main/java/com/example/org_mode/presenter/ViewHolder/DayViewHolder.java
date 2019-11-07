package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;

public class DayViewHolder extends RecyclerView.ViewHolder {
    public final TextView DayDate;
    public final RecyclerView recyclerView;
    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        DayDate = itemView.findViewById(R.id.daydate);
        recyclerView = itemView.findViewById(R.id.ItemRecyclerView);
    }
}
