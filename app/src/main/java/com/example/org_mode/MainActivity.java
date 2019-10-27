package com.example.org_mode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.org_mode.utils.frg_Agenda;
import com.example.org_mode.utils.frg_Files;
import com.example.org_mode.utils.frg_Settings;
import com.example.org_mode.utils.frg_Todo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iAgenda, iTodo, iFiles, iSettings;

    private frg_Agenda fAgenda;
    private frg_Todo fTodo;
    private frg_Files fFiles;
    private frg_Settings fSettings;

    private FrameLayout frameLayout;
    private RelativeLayout lAgenda, lTodo, lFiles, lSettings;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

