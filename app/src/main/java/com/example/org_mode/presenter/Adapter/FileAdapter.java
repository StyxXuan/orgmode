package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.DayViewHolder;
import com.example.org_mode.presenter.ViewHolder.FileViewHolder;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;
import com.example.org_mode.view.setFileActivity;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.org_mode.model.FileResolution.deleteFile;
import static com.example.org_mode.model.FileResolution.getFileNames;
import static com.example.org_mode.utils.DateUtils.getEditTime;

public class FileAdapter extends RecyclerView.Adapter<FileViewHolder> {
    private Context mContext;
    private List<String> mFiles;
    private OnRecyclerViewClickListener listener;

    public void setItemClickListener(OnRecyclerViewClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public FileAdapter(Context context){this.mContext = context; }

    public void setmFiles(List<String> files){
        mFiles = files;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        FileViewHolder viewHolder = new FileViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, final int position) {
        holder.fileName.setText(mFiles.get(position));
        holder.EditDate.setText(getEditTime(mFiles.get(position)));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(view, position);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile(mContext.getExternalFilesDir("").getAbsolutePath() +
                        "/OrgFiles/" + mFiles.get(position));
                mFiles.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

}
