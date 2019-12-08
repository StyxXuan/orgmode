package com.example.org_mode.utils.interfaces;

import android.view.View;

import com.example.org_mode.utils.ScheduleItem;

public interface OnRecyclerViewItemClickListener {
    void onItemClickListener(View view, int position, ScheduleItem scheduleItem);

}
