package com.example.org_mode.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.org_mode.R;
import com.example.org_mode.presenter.fragment.frg_Agenda;
import com.example.org_mode.presenter.fragment.frg_Files;
import com.example.org_mode.presenter.fragment.frg_Settings;
import com.example.org_mode.presenter.fragment.frg_Todo;
import com.example.org_mode.utils.AutoReceiver;
import com.ycbjie.notificationlib.NotificationUtils;

import java.util.Date;

import static com.example.org_mode.model.API.flushAll;
import static com.example.org_mode.model.FileResolution.InitFileResolution;
import static com.example.org_mode.model.UserDefinedTagsResolution.verifyStoragePermissions;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iAgenda, iTodo, iFiles, iSettings;

    private frg_Agenda fAgenda;
    private frg_Todo fTodo;
    private frg_Files fFiles;
    private frg_Settings fSettings;

    private ImageButton bAdd;
    private RelativeLayout lAgenda, lTodo, lFiles, lSettings;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);

        fragmentManager = getSupportFragmentManager();
        init();
        setChoiceFragment(0);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.agenda:
                setChoiceFragment(0);
                break;
            case R.id.todo:
                setChoiceFragment(1);
                break;
            case R.id.files:
                setChoiceFragment(2);
                break;
            case R.id.settings:
                setChoiceFragment(3);
                break;
            default:
                break;
        }
    }

    private void init(){
        iAgenda = (ImageView)findViewById(R.id.imgAgenda);
        iTodo = (ImageView)findViewById(R.id.imgTodo);
        iFiles = (ImageView)findViewById(R.id.imgFiles);
        iSettings = (ImageView)findViewById(R.id.imgSettings);

        lAgenda = (RelativeLayout)findViewById(R.id.agenda);
        lTodo = (RelativeLayout) findViewById(R.id.todo);
        lFiles = (RelativeLayout) findViewById(R.id.files);
        lSettings = (RelativeLayout)findViewById(R.id.settings);

        lAgenda.setOnClickListener(MainActivity.this);
        lSettings.setOnClickListener(MainActivity.this);
        lTodo.setOnClickListener(MainActivity.this);
        lFiles.setOnClickListener(MainActivity.this);

        bAdd = (ImageButton) findViewById(R.id.add);
        bAdd.bringToFront();
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        InitFileResolution(this, this.getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/", this.getExternalFilesDir("").getAbsolutePath() +
                "/settings/");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        flushAll(this.getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/", this.getExternalFilesDir("").getAbsolutePath() +
                "/settings/");
    }

    private void setChoiceFragment(int choice){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChoice();
        hideFragments(fragmentTransaction);
        switch (choice){
            case 0:
                iAgenda.setImageResource(R.mipmap.agenda_b);
                if(fAgenda == null) {
                    fAgenda = new frg_Agenda();
                    fragmentTransaction.add(R.id.content, fAgenda);
                }
                else
                    fragmentTransaction.show(fAgenda);
                break;
            case 1:
                iTodo.setImageResource(R.mipmap.todo_b);
                if(fTodo == null) {
                    fTodo= new frg_Todo();
                    fragmentTransaction.add(R.id.content, fTodo);
                }
                else
                    fragmentTransaction.show(fTodo);
                break;
            case 2:
                iFiles.setImageResource(R.mipmap.files_b);
                if(fFiles == null) {
                    fFiles= new frg_Files();
                    fragmentTransaction.add(R.id.content, fFiles);
                }
                else
                    fragmentTransaction.show(fFiles);
                break;
            case 3:
                iSettings.setImageResource(R.mipmap.settings_b);
                if(fSettings == null) {
                    fSettings = new frg_Settings();
                    fragmentTransaction.add(R.id.content, fSettings);
                }
                else
                    fragmentTransaction.show(fSettings);
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
        bAdd.setVisibility(View.VISIBLE);
        bAdd.bringToFront();
    }

    private void clearChoice(){
        iAgenda.setImageResource(R.mipmap.agenda_w);
        iTodo.setImageResource(R.mipmap.todo_w);
        iFiles.setImageResource(R.mipmap.files_w);
        iSettings.setImageResource(R.mipmap.settings_w);
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fAgenda != null) {
            fragmentTransaction.hide(fAgenda);
        }
        if (fTodo != null) {
            fragmentTransaction.hide(fTodo);
        }
        if (fFiles!= null) {
            fragmentTransaction.hide(fFiles);
        }
        if (fSettings!= null) {
            fragmentTransaction.hide(fSettings);
        }
    }
}

