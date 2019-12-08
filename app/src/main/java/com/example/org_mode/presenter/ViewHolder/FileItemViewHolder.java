package com.example.org_mode.presenter.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;

import org.w3c.dom.Text;

public class FileItemViewHolder extends RecyclerView.ViewHolder {
    public final ImageView signal;
    public final TextView Tag;
    public final TextView Title;
    public final TextView Note;
    public final LinearLayout layout;
    public Boolean state;
    public FileItemViewHolder(@NonNull View itemView) {
        super(itemView);
        signal = itemView.findViewById(R.id.file_schedule_item_signal);
        Tag = itemView.findViewById(R.id.file_schedule_item_tag);
        Title = itemView.findViewById(R.id.file_schedule_item_title);
        Note = itemView.findViewById(R.id.text_note);
        layout = itemView.findViewById(R.id.shcule_in_file_layout);
        state = false;
    }
}
