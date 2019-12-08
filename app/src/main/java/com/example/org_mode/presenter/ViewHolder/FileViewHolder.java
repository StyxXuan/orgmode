package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;

public class FileViewHolder extends RecyclerView.ViewHolder {
    public final TextView fileName, EditDate;
    public final LinearLayout linearLayout;
    public final Button btnDelete;
    public FileViewHolder(@NonNull View itemView) {
        super(itemView);
        fileName = itemView.findViewById(R.id.fileName);
        EditDate = itemView.findViewById(R.id.EditDate);
        linearLayout = itemView.findViewById(R.id.file_frag_linearlayout);
        btnDelete = itemView.findViewById(R.id.btnDeleteFile);
    }
}
