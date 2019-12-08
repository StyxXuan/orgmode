package com.example.org_mode.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.org_mode.R;
import com.example.org_mode.utils.ScheduleItem;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.org_mode.model.FileResolution.ReplaceScheduleItem;
import static com.example.org_mode.model.FileResolution.deleteItem;
import static com.example.org_mode.model.FileResolution.getItemByInfo;
import static com.example.org_mode.model.FileResolution.insertItem;
import static com.example.org_mode.utils.DateUtils.getPresentTime;

public class EditActivity extends AppCompatActivity {

    private TextView tCancel, tSave, tState, tPriority;
    public static int currentState = 0, currentPriority = 0;

    private Date chosenTime = new Date();
    private TimePickerView pvTime;
    private TextView tSchedule, tDeadline, tShowon, tFileName, ttag;
    private LinearLayout lSchedule, lDeadline, lShowon, lSave, lTag;

    private LinearLayout lState, lPriority;
    private EditText hl;
    public ScheduleItem scheduleItem;
    public ScheduleItem newItem;
    private String headline = "", state = "", priority = "",
            tag = "", schedule = "", deadline = "",
            showon = "", fileName = "inbox.org";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        scheduleItem = getItemByInfo(getIntent().getStringExtra("SchduleItem"));
        newItem = new ScheduleItem();
        init(scheduleItem);
        listener();
    }

    private void init(ScheduleItem scheduleItem){
        hl = (EditText)findViewById(R.id.headline);
        tCancel = (TextView)findViewById(R.id.add_cancel);
        tSave = (TextView)findViewById(R.id.add_save);
        tState = (TextView)findViewById(R.id.set_state);
        tPriority = (TextView)findViewById(R.id.set_priority);
        tSchedule = (TextView)findViewById(R.id.chosen_date1);
        tDeadline = (TextView)findViewById(R.id.chosen_date2);
        tShowon = (TextView)findViewById(R.id.chosen_date3);
        tFileName = (TextView)findViewById(R.id.save_to);
        ttag = findViewById(R.id.set_tag);

        lState = (LinearLayout)findViewById(R.id.s_state);
        lPriority = (LinearLayout)findViewById(R.id.s_priority);
        lSchedule = (LinearLayout)findViewById(R.id.s_schedule);
        lDeadline = (LinearLayout)findViewById(R.id.s_deadline);
        lShowon= (LinearLayout)findViewById(R.id.s_showon);
        lSave= (LinearLayout)findViewById(R.id.s_save);
        lTag = findViewById(R.id.s_tag);

        tFileName.setText(scheduleItem.getFileName());
        tState.setText(scheduleItem.getTags());
        tPriority.setText(scheduleItem.getPriority());
        tSchedule.setText(scheduleItem.getScheduled());
        tDeadline.setText(scheduleItem.getDeadline());
        tShowon.setText(scheduleItem.getShowOn());
        hl.setText(scheduleItem.getDescription());
        ttag.setText(scheduleItem.getLabel());
        initTimePicker();
    }

    private void listener(){
        tCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headline = hl.getText().toString();
                tag = tState.getText().toString();
                priority = tPriority.getText().toString();
                fileName = tFileName.getText().toString();
                deadline = tDeadline.getText().toString();
                showon = tShowon.getText().toString();
                schedule = tSchedule.getText().toString();
                state = ttag.getText().toString();

                if(headline.equals("")){
                    Toast toast = Toast.makeText(EditActivity.this, "You must provide a headline!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 0);  //设置显示位置
                    toast.show();
                }
                else{
                    newItem.setFileName(fileName);
                    newItem.setTags(tag);
                    newItem.setPriority(priority);
                    newItem.setState("*");
                    newItem.setLabel(state);
                    newItem.setDeadline(deadline);
                    newItem.setShowon(showon);
                    newItem.setScheduled(schedule);
                    newItem.setDescription(headline);
                    newItem.setEditTime("[" + getPresentTime() + "]");
                    Log.i("old item: ",scheduleItem.printItem());
                    Log.i("new item", newItem.printItem());

                    ReplaceScheduleItem(scheduleItem, newItem);
                    finish();
                }

            }
        });
        lState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EditActivity.this, setStateActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        lPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EditActivity.this, setPriorityActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        lSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(chosenTime);
                    pvTime.setDate(calendar);
                    pvTime.show(view);
                }
            }
        });
        lDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(chosenTime);
                    pvTime.setDate(calendar);
                    pvTime.show(view);
                }
            }
        });
        lShowon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(chosenTime);
                    pvTime.setDate(calendar);
                    pvTime.show(view);
                }
            }
        });
        lSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EditActivity.this, SelectFileActivity.class);
                startActivityForResult(intent, 3);
            }
        });
        lTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(EditActivity.this, setTagActivity.class);
                startActivityForResult(intent, 4);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("state");
                    //
                    tag = returnData;
                    //
                    tState.setText(returnData);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("priority");
                    priority = returnData;
                    tPriority.setText(returnData);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("FileName");
                    fileName = returnData;
                    tFileName.setText(returnData);
                }
                break;
            case 4:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("tag");
                    state = returnData;
                    ttag.setText(state);
                }
                break;
        }
    }

    private void initTimePicker() {

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                TextView textView;
                switch (v.getId()){
                    case R.id.s_schedule:
                        chosenTime = date;
                        schedule =  getTime(date);
                        textView = tSchedule;
                        break;
                    case R.id.s_deadline:
                        chosenTime = date;
                        deadline = getTime(date);
                        textView = tDeadline;
                        break;
                    case R.id.s_showon:
                        chosenTime = date;
                        showon = getTime(date);
                        textView = tShowon;
                        break;
                    default:
                        textView = null;

                }

                textView.setText(getTime(date));
            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {

                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .build();


        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd E");
        return format.format(date);
    }
}
