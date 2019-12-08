package com.example.org_mode.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.org_mode.R;

public class setPriorityActivity extends AppCompatActivity {

    LinearLayout tNone, tA, tB, tC, tD, tE, tF;
    TextView tC1, tC2, tC3, tC4, tC5, tC6, tC7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_priority);
        init();
        listener();
    }
    private void init(){
        tNone = (LinearLayout) findViewById(R.id.set_none2);
        tA = (LinearLayout) findViewById(R.id.set_a);
        tB = (LinearLayout) findViewById(R.id.set_b);
        tC = (LinearLayout) findViewById(R.id.set_c);
        tD = (LinearLayout) findViewById(R.id.set_d);
        tE = (LinearLayout) findViewById(R.id.set_e);
        tF = (LinearLayout) findViewById(R.id.set_f);
        tC1 = (TextView)findViewById(R.id.pri_check1);
        tC2 = (TextView)findViewById(R.id.pri_check2);
        tC3 = (TextView)findViewById(R.id.pri_check3);
        tC4 = (TextView)findViewById(R.id.pri_check4);
        tC5 = (TextView)findViewById(R.id.pri_check5);
        tC6 = (TextView)findViewById(R.id.pri_check6);
        tC7 = (TextView)findViewById(R.id.pri_check7);
        if(AddActivity.currentPriority != 0){
            switch (AddActivity.currentPriority){
                case 1:
                    tC1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tC2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tC3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tC4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    tC5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    tC6.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    tC7.setVisibility(View.VISIBLE);
                    break;

            }
        }

    }
    private void listener(){
        tNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 1;
                tC1.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 2;
                tC2.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#A");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 3;
                tC3.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#B");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 4;
                tC4.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#C");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 5;
                tC5.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#D");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 6;
                tC6.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#E");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        tF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGone();
                AddActivity.currentPriority = 7;
                tC7.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.putExtra("priority", "#F");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void setGone(){
        tC1.setVisibility(View.INVISIBLE);
        tC2.setVisibility(View.INVISIBLE);
        tC3.setVisibility(View.INVISIBLE);
        tC4.setVisibility(View.INVISIBLE);
        tC5.setVisibility(View.INVISIBLE);
        tC6.setVisibility(View.INVISIBLE);
        tC7.setVisibility(View.INVISIBLE);

    }
}
