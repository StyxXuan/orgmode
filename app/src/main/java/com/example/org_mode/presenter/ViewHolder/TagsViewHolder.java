package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;

public class TagsViewHolder extends RecyclerView.ViewHolder {
    public final TextView tagText;
    public final LinearLayout tagLayout;
    public TagsViewHolder(@NonNull View itemView) {
        super(itemView);
        tagLayout = itemView.findViewById(R.id.tag_layout);
        tagText = itemView.findViewById(R.id.tag_text);
    }
}
