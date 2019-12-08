package com.example.org_mode.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.org_mode.R;

public class setStateActivity extends AppCompatActivity {

    private TextView tCancel, tCheck1, tCheck2, tCheck3;
    private LinearLayout tNone, tTodo, tDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_state);
        init();
        listner();
    }

    private void init(){
        tNone = (LinearLayout) findViewById(R.id.set_none);
        tTodo = (LinearLayout) findViewById(R.id.set_todo);
        tDone = (LinearLayout) findViewById(R.id.set_done);
        tCancel = (TextView)findViewById(R.id.state_cancel);
        tCheck1 = (TextView)findViewById(R.id.set_check1);
        tCheck2 = (TextView)findViewById(R.id.set_check2);
        tCheck3 = (TextView)findViewById(R.id.set_check3);
        if(AddActivity.currentState != 0)
            switch (AddActivity.currentState){
                case 1:
                    tCheck1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tCheck2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tCheck3.setVisibility(View.VISIBLE);
                    break;
            }
    }
    private void listner() {
        tTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.currentState = 2;
                tCheck2.setVisibility(View.VISIBLE);
                setOtherGone(tCheck1, tCheck3);
                Intent intent = new Intent();
                intent.putExtra("state", "TODO");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.currentState = 1;
                tCheck1.setVisibility(View.VISIBLE);
                setOtherGone(tCheck3, tCheck2);
                Intent intent = new Intent();
                intent.putExtra("state", "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity.currentState = 3;
                tCheck3.setVisibility(View.VISIBLE);
                setOtherGone(tCheck1, tCheck2);
                Intent intent = new Intent();
                intent.putExtra("state", "Done");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setOtherGone(TextView o1, TextView o2){
        o1.setVisibility(View.INVISIBLE);
        o2.setVisibility(View.INVISIBLE);
    }
}
