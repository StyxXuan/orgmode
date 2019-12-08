package com.example.org_mode.presenter.ViewHolder;

import android.media.Image;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.org_mode.R;


public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    public final LinearLayout linearLayout;
    public final TextView fileText;
    public final TextView contentText;
    public final TextView Tags;
    public final TextView LateDays;
    public final ImageView SignalImage;
    public final Button btnDone;
    public ScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.schedule_item_linearLayout);
        fileText = itemView.findViewById(R.id.itemfile);
        contentText = itemView.findViewById(R.id.itemcontent);
        Tags = itemView.findViewById(R.id.Tags);
        LateDays = itemView.findViewById(R.id.LateDays);
        SignalImage = itemView.findViewById(R.id.SignalImage);
        btnDone = itemView.findViewById(R.id.btnTop);
    }
}
