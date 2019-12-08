package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.TodoViewHolder;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;
import com.example.org_mode.view.EditActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.org_mode.model.FileResolution.getItemsbyFileName;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private Context mContext;
    private Boolean isShowAll;
    private String SearchString = "";

    List<String> FileName;

    public void setSearchString(String searchString) {
        SearchString = searchString;
    }

    public TodoAdapter(Context context){
        mContext = context;
        isShowAll = true;
    }

    public TodoAdapter(Context context, Boolean show){
        mContext = context;
        isShowAll = show;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.FileNameText.setText(FileName.get(position));
        List<ScheduleItem> items = getItemsbyFileName(FileName.get(position), SearchString);
        items = SelectOnItems(items);

        if(!items.isEmpty()) {
            if(holder == null){
                Log.d("Holder", "isNUll");
            }
            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(mContext);
            scheduleAdapter.setData(items);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.recyclerView.setLayoutManager(linearLayoutManager);
            scheduleAdapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position, ScheduleItem scheduleItem) {
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra("SchduleItem", scheduleItem.printItem());
                    Log.d("clicked", "EditActivity");
                    Log.d("onClick", scheduleItem.printItem());
                    mContext.startActivity(intent);
                }
            });

            holder.recyclerView.setAdapter(scheduleAdapter);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return FileName.size();
    }

    public void setFileName(List<String> fileName){
        this.FileName = fileName;
        notifyDataSetChanged();
        Log.d("FileSize", Integer.toString(FileName.size()));
    }

    List<ScheduleItem> SelectOnItems(List<ScheduleItem> items){
        if(isShowAll){
            return items;
        }else{
            List<ScheduleItem> Items = new ArrayList<ScheduleItem>();
            for(int i=0; i<items.size(); i++){
                if(items.get(i).getTags().equals("TODO"))
                    Items.add(items.get(i));
            }

            return Items;
        }
    }
}
