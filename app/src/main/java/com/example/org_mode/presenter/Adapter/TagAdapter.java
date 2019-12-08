package com.example.org_mode.presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.ViewHolder.TagsViewHolder;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;
import com.example.org_mode.utils.interfaces.OnRecyclerViewTagClickListener;
import com.example.org_mode.view.setTagActivity;

import java.util.List;

import static com.example.org_mode.model.UserDefinedTagsResolution.insertUserDifinedTags;

public class TagAdapter extends RecyclerView.Adapter<TagsViewHolder> {
    List<String> Tag;
    Context mContext;
    public OnRecyclerViewTagClickListener listener;
    public void setItemClickListener(OnRecyclerViewTagClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public TagAdapter(Context context) {
        mContext = context;
    }

    public void setTag(List<String>tag){
        Tag = tag;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_item, parent, false);
        TagsViewHolder viewHolder = new TagsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, final int position) {
        holder.tagText.setText(Tag.get(position));
        holder.tagLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTag = Tag.get(position);
                if(newTag.contains("Add Tag")){
                    newTag = newTag.split(" ")[2];
                    insertUserDifinedTags(newTag);
                }
                listener.onItemClickListener(view, newTag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Tag.size();
    }
}
