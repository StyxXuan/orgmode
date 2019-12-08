package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;

public class TodoViewHolder extends RecyclerView.ViewHolder{
    public final TextView FileNameText;
    public final RecyclerView recyclerView;
    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        FileNameText = itemView.findViewById(R.id.TodoText);
        recyclerView = itemView.findViewById(R.id.TodoItemRecyVIew);
    }
}
