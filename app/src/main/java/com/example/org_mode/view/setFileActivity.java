package com.example.org_mode.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.FileAdapter;
import com.example.org_mode.presenter.Adapter.FileItemAdapter;
import com.example.org_mode.utils.ScheduleItem;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;
import com.example.org_mode.utils.interfaces.OnRecyclerViewItemClickListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ezy.ui.view.RoundButton;

import static com.example.org_mode.model.FileResolution.deleteItem;
import static com.example.org_mode.model.FileResolution.getFileNames;
import static com.example.org_mode.model.FileResolution.getItems;
import static com.example.org_mode.model.FileResolution.getItemsbyFileName;

public class setFileActivity extends AppCompatActivity {
    FileItemAdapter recycleAdapter;
    ScheduleItem toShow = null;
    List<ScheduleItem> FileItems;
    RecyclerView FileItemView;
    TextView TitleText;
    LinearLayout Back;
    TextView FileShow;
    TextView DoubleLeft, DoubleRight, DoubleUp, DoubleDown;
    TextView DeleteButton;
    TextView AddState, AddPriority, AddTag, AddSchedule, AddDeadline, AddShowon, AddChange;

    RoundButton Note, HeadLine, addButton;
    String FileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_file);

        DoubleLeft = findViewById(R.id.file_add_double_left);
        DoubleRight = findViewById(R.id.file_add_double_right);
        DoubleUp = findViewById(R.id.file_add_double_up);
        DoubleDown = findViewById(R.id.file_add_double_down);
        FileShow = findViewById(R.id.file_show);
        Note = findViewById(R.id.file_add_note);
        HeadLine = findViewById(R.id.file_add_Item);
        DeleteButton = findViewById(R.id.file_add_delete);
        addButton = findViewById(R.id.add_item);

        TitleText = findViewById(R.id.file_activity_fileName);
        FileItemView = findViewById(R.id.file_item_recyclerview);
        Back = findViewById(R.id.backLinearLayout);
        AddState = findViewById(R.id.fileadd_state);
        AddPriority = findViewById(R.id.fileadd_priority);
        AddTag = findViewById(R.id.fileadd_tag);
        AddSchedule = findViewById(R.id.fileadd_shcedule);
        AddDeadline = findViewById(R.id.fileadd_deadline);
        AddShowon = findViewById(R.id.fileadd_showon);
        AddChange = findViewById(R.id.fileadd_change);


        Intent intent = getIntent();
        FileName = intent.getStringExtra("SchdeuleItem");
        TitleText.setText(FileName);

        Log.d("Intent FileName", FileName);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleAdapter = new FileItemAdapter(this);
        FileItems = getItemsbyFileName(FileName);
        recycleAdapter.setDatas(FileItems);

        FileItemView.setLayoutManager(layoutManager);
        FileItemView.setAdapter(recycleAdapter);
        FileItemView.setItemAnimator(new DefaultItemAnimator());

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recycleAdapter.setItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position, ScheduleItem scheduleItem) {
                toShow = scheduleItem;
                FileShow.setText(toShow.getDescription());
            }
        });

        DoubleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                String State = toShow.getState();
                if(!State.equals("*")) {
                    for(int i=0; i<FileItems.size(); i++)
                    {
                        if(FileItems.get(i).printItem().equals(toShow.printItem())){
                            FileItems.remove(i);
                            toShow.setState(State.substring(1));
                            FileItems.add(i, toShow);
                        }
                    }
                }

                FileShow.setText("new state "  + toShow.getState());
                recycleAdapter.setDatas(FileItems);
            }
        });
        DoubleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                String State = toShow.getState();
                for(int i=0; i<FileItems.size(); i++)
                {
                    if(FileItems.get(i).printItem().equals(toShow.printItem())){
                        FileItems.remove(i);
                        toShow.setState(State + "*");
                        FileItems.add(i, toShow);
                    }
                }

                FileShow.setText("new state "  + toShow.getState());
                recycleAdapter.setDatas(FileItems);
            }
        });

        Note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getNote());
            }
        });

        HeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getDescription());
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                for(int i=0; i<FileItems.size(); i++)
                {
                    if(FileItems.get(i).printItem().equals(toShow.printItem())){
                        deleteItem(FileItems.get(i));
                        FileItems = getItemsbyFileName(FileName);
                        toShow = null;
                        recycleAdapter.setDatas(FileItems);
                    }
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(setFileActivity.this, AddActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        AddState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getTags());
            }
        });

        AddPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getPriority());
            }
        });
        AddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getLabel());
            }
        });
        AddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getScheduled());
            }
        });
        AddDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getDeadline());
            }
        });
        AddShowon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getShowOn());
            }
        });
        AddChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toShow == null)
                    return;

                FileShow.setText(toShow.getEditTime());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    FileItems = getItemsbyFileName(FileName);
                    recycleAdapter.setDatas(FileItems);
                }
        }
    }
}
