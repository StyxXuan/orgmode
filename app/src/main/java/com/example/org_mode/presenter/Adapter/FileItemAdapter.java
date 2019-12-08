package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.FileItemViewHolder;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FileItemAdapter extends RecyclerView.Adapter<FileItemViewHolder> {

    private List<ScheduleItem> datas;
    private List<ScheduleItem> dataShow;
    private Context mContext;

    public FileItemAdapter(Context context){mContext = context; }

    private OnRecyclerViewItemClickListener listener;

    public void setItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public void setDatas(List<ScheduleItem> datas) {
        this.datas = datas;
        this.dataShow = new ArrayList<ScheduleItem>();
        for(int i=0; i<datas.size(); i++){
            if(datas.get(i).getState().equals("*")){
                dataShow.add(new ScheduleItem(datas.get(i)));
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schdule_in_file_item, parent, false);
        return new FileItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileItemViewHolder holder, final int position) {
        String tag = dataShow.get(position).getTags();
        holder.Tag.setText(tag);

        if(tag.equals("TODO"))
            holder.Tag.setTextColor(android.graphics.Color.parseColor("#FFA500"));

        holder.Title.setText(dataShow.get(position).getDescription());

        holder.Note.setText("");
        holder.Note.setVisibility(View.GONE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(holder.layout.getLayoutParams());

        int leftMargin = 50 * dataShow.get(position).getState().length() - 50;
        lp.setMargins(leftMargin, 0, 0, 0);

        holder.layout.setLayoutParams(lp);
        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickListener(view, position, dataShow.get(position));
            }
        });

        holder.signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("State", holder.state.toString());
                holder.state = !holder.state;
                if(holder.state){
                    holder.signal.setBackground(mContext.getResources().getDrawable(R.drawable.ic_expand_more_black_24dp));
                    holder.Note.setText(dataShow.get(position).ItemToShow());
                    holder.Note.setVisibility(View.VISIBLE);
                    List<Integer> childs = getChild(dataShow.get(position));
                    for(int i=0; i<childs.size(); i++){
                        Log.d("add", "" + position +1 + i);
                        dataShow.add(position + 1 + i, new ScheduleItem(datas.get(childs.get(i))));
                        notifyItemInserted(position + 1 + i);
                    }
                }else {
                    holder.Note.setText("");
                    holder.signal.setBackground(mContext.getResources().getDrawable(R.drawable.ic_arrow_left));
                    holder.Note.setVisibility(View.GONE);
                    for(int i=position + 1; i<dataShow.size(); i++){
                        if(!dataShow.get(i).getState().contains(dataShow.get(position).getState()) ||
                                dataShow.get(i).getState().equals(dataShow.get(position).getState()))
                            break;

                        dataShow.remove(i);
                        Log.d("rm", "" + position);

                        notifyItemRemoved(i);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataShow.size();
    }

    private List<Integer> getChild(ScheduleItem item) {
        List<Integer> itemChilds = new ArrayList<Integer>();
        int Pos = -1;
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).printItem().equals(item.printItem())){
                Pos = i;
            }
        }

        for (int i = Pos + 1; i < datas.size(); i++) {
            if(datas.get(i).getState().equals(item.getState()))
                break;

            if(datas.get(i).getState().equals(item.getState() + "*"))
                itemChilds.add(i);
        }

        return itemChilds;
    }
}
