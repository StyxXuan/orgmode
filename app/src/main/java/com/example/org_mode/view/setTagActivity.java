package com.example.org_mode.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.DayAdapter;
import com.example.org_mode.presenter.Adapter.TagAdapter;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;
import com.example.org_mode.utils.interfaces.OnRecyclerViewTagClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.org_mode.model.FileResolution.getMatchedDates;
import static com.example.org_mode.model.UserDefinedTagsResolution.getAllUserDifinedTags;
import static com.example.org_mode.model.UserDefinedTagsResolution.getMatchedUserDifinedTags;
import static com.example.org_mode.utils.DateUtils.convertDate;
import static com.example.org_mode.utils.DateUtils.convertScheduleDate;

public class setTagActivity extends AppCompatActivity {
    RecyclerView tagRecyclerView;
    EditText editText;
    TextView DONE;
    TagAdapter recycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tag);
        tagRecyclerView = findViewById(R.id.Tags_RecyclerView);
        editText = findViewById(R.id.search);
        DONE = findViewById(R.id.state_cancel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleAdapter = new TagAdapter(this);
        Log.d("tagSize", " " +getAllUserDifinedTags().size());
        recycleAdapter.setTag(getAllUserDifinedTags());
        tagRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        tagRecyclerView.setAdapter(recycleAdapter);
        tagRecyclerView.setItemAnimator( new DefaultItemAnimator());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!editText.getText().toString().equals("")){
                    if(getMatchedUserDifinedTags(s.toString()).size() == 0){
                        List<String> newTag = new ArrayList<String>();
                        newTag.add("Add Tag " + s.toString());
                        recycleAdapter.setTag(newTag);
                    }else {
                        recycleAdapter.setTag(getMatchedUserDifinedTags(s.toString()));
                        Log.i("s", s.toString());
                    }
                }else {
                    recycleAdapter.setTag(getAllUserDifinedTags());

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!editText.getText().toString().equals("")){
                    if(getMatchedUserDifinedTags(s.toString()).size() == 0){
                        List<String> newTag = new ArrayList<String>();
                        newTag.add("Add Tag " + s.toString());
                        recycleAdapter.setTag(newTag);
                    }else {
                        recycleAdapter.setTag(getMatchedUserDifinedTags(s.toString()));
                        Log.i("s", s.toString());
                    }
                }else {
                    recycleAdapter.setTag(getAllUserDifinedTags());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!editText.getText().toString().equals("")){
                    if(getMatchedUserDifinedTags(s.toString()).size() == 0){
                        List<String> newTag = new ArrayList<String>();
                        newTag.add("Add Tag " + s.toString());
                        recycleAdapter.setTag(newTag);
                    }else {
                        recycleAdapter.setTag(getMatchedUserDifinedTags(s.toString()));
                        Log.i("s", s.toString());
                    }
                }else {
                    recycleAdapter.setTag(getAllUserDifinedTags());
                }
            }
        });

        recycleAdapter.setItemClickListener(new OnRecyclerViewTagClickListener() {
            @Override
            public void onItemClickListener(View view, String Tag) {
                AddActivity.currentState = 1;
                Intent intent = new Intent();
                intent.putExtra("tag", Tag);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        DONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.currentState = 2;
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}
