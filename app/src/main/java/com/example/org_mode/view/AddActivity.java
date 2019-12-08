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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.org_mode.model.API.flushAll;
import static com.example.org_mode.model.FileResolution.insertItem;

public class AddActivity extends AppCompatActivity {

    private TextView tCancel, tSave, tState, tPriority;
    private ScheduleItem scheduleItem = new ScheduleItem();
    public static int currentState = 0, currentPriority = 0;

    private Date chosenTime = new Date();
    private TimePickerView pvTime;
    private TextView tSchedule, tDeadline, tShowon, ttag, tFileName;
    private LinearLayout lSchedule, lDeadline, lShowon;

    private LinearLayout lState, lPriority, lTag, lSave;

    private String headline, state, priority, tag, schedule, deadline, showon, fileName = "inbox.org";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        listener();
    }

    private void init(){
        tCancel = (TextView)findViewById(R.id.add_cancel);
        tSave = (TextView)findViewById(R.id.add_save);
        tState = (TextView)findViewById(R.id.set_state);
        tPriority = (TextView)findViewById(R.id.set_priority);
        tSchedule = (TextView)findViewById(R.id.chosen_date1);
        tDeadline = (TextView)findViewById(R.id.chosen_date2);
        tShowon = (TextView)findViewById(R.id.chosen_date3);
        ttag = findViewById(R.id.set_tag);
        tFileName = (TextView)findViewById(R.id.save_to);

        lState = (LinearLayout)findViewById(R.id.s_state);
        lPriority = (LinearLayout)findViewById(R.id.s_priority);
        lSchedule = (LinearLayout)findViewById(R.id.s_schedule);
        lDeadline = (LinearLayout)findViewById(R.id.s_deadline);
        lShowon= (LinearLayout)findViewById(R.id.s_showon);
        lTag = findViewById(R.id.s_tag);
        lSave= (LinearLayout)findViewById(R.id.s_save);
        tFileName.setText("inbox.org");

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
                EditText hl = (EditText)findViewById(R.id.headline);
                headline = hl.getText().toString();
                if(headline.equals("")){
                    Toast toast = Toast.makeText(AddActivity.this, "You must provide a headline!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 0);  //设置显示位置
                    toast.show();
                }
                else{
                    scheduleItem.setFileName(fileName);
                    scheduleItem.setTags(tag);
                    scheduleItem.setPriority(priority);
                    scheduleItem.setState("*");
                    scheduleItem.setDeadline(deadline);
                    scheduleItem.setShowon(showon);
                    scheduleItem.setScheduled(schedule);
                    scheduleItem.setDescription(headline);
                    scheduleItem.setLabel(state);
                    Log.i("to insert", headline + " " + tag + " " + priority + " " + state + " " + schedule + " " + deadline + " " + showon );
                    insertItem(scheduleItem);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
        lState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, setStateActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        lPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, setPriorityActivity.class);
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
        lTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, setTagActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        lSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, SelectFileActivity.class);
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
                    String returnData = data.getStringExtra("tag");
                    state = returnData;
                    ttag.setText(returnData);
                }
                break;

            case 4:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("FileName");
                    fileName = returnData;
                    tFileName.setText(returnData);
                }
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        flushAll(this.getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/", this.getExternalFilesDir("").getAbsolutePath() +
                "/settings/");    }

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
