package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.ScheduleViewHolder;
import com.example.org_mode.utils.ScheduleDate;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

import static com.example.org_mode.utils.DateUtils.getToday;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    @NonNull
    private List<ScheduleItem> datas;
    private Context mContext;

    public OnRecyclerViewItemClickListener listener;
    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public ScheduleAdapter(Context context){
        mContext = context;
    }

    public void setData(List<ScheduleItem> Data){
        this.datas = Data;
//        notifyDataSetChanged();
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schdule_item, parent, false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, final int position) {
        holder.contentText.setText(datas.get(position).getDescription());
        holder.fileText.setText(datas.get(position).getFileName());
        holder.Tags.setText(datas.get(position).getTags());
        ScheduleDate deadline = new ScheduleDate(datas.get(position).getDeadline());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(view, position, datas.get(position));
            }
        });

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datas.get(position).setTags("DONE");
                notifyItemChanged(position);
            }
        });
        if(deadline.isExpired(getToday())){
            holder.LateDays.setText(deadline.getInfoAboutExpiration(getToday()));
            Log.d("Date By ", deadline.getInfoAboutExpiration(getToday()));
            holder.SignalImage.setBackground(mContext.getResources().getDrawable(R.drawable.ic_error_black_24dp));
        }

        Log.d("Bug!!!!", "" + datas.size());
        Log.d("Position", "" +  position);
        Log.d("Info", datas.get(position).printItem());

        if(datas.get(position).getTags().equals("DONE")){
            holder.SignalImage.setBackground(mContext.getResources().getDrawable(R.drawable.ic_done_black_24dp));
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
