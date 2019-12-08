package com.example.org_mode.presenter.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.DayAdapter;
import com.example.org_mode.view.MainActivity;

import java.util.Date;
import java.util.List;

import static com.example.org_mode.model.FileResolution.getMatchedDates;
import static com.example.org_mode.utils.DateUtils.convertDate;
import static com.example.org_mode.utils.DateUtils.convertScheduleDate;
import static com.example.org_mode.utils.DateUtils.getLastWeek;
import static com.example.org_mode.utils.DateUtils.getNextWeek;
import static com.example.org_mode.utils.DateUtils.getWeek;
import static com.example.org_mode.utils.DateUtils.getWeekByDate;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_Agenda extends Fragment {
    List<Date> dates;
    ImageView left, right, schedule;
    TextView today;
    RecyclerView recyclerView;
    EditText searchBar;
    ImageButton addBtn;
    @SuppressLint("WrongConstant")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_agenda, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        left = view.findViewById(R.id.left);
        right = view.findViewById(R.id.right);
        today = view.findViewById(R.id.today);
        schedule = view.findViewById(R.id.date);
        searchBar = view.findViewById(R.id.search);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DayAdapter recycleAdapter = new DayAdapter(getContext());

        getToday();

        recycleAdapter.setData(convertDate(dates));

        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastWeek();
                DayAdapter recycleAdapter = new DayAdapter(getContext());
                recycleAdapter.setData(convertDate(dates));
                recyclerView.setAdapter(recycleAdapter);
            }

        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        left.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp));
                        return true;
                    case MotionEvent.ACTION_UP:
                        left.setBackground(getResources().getDrawable(R.drawable.ic_arrow_left));
                        lastWeek();
                        searchBar.setText("");
                        DayAdapter recycleAdapter = new DayAdapter(getContext());
                        recycleAdapter.setData(convertDate(dates));
                        recyclerView.setAdapter(recycleAdapter);
                        return true;
                }
                return false;
            }
        });


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextWeek();
                DayAdapter recycleAdapter = new DayAdapter(getContext());
                recycleAdapter.setData(convertDate(dates));
                recyclerView.setAdapter(recycleAdapter);
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        right.setBackground(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));
                        return true;
                    case MotionEvent.ACTION_UP:
                        right.setBackground(getResources().getDrawable(R.drawable.ic_arrow_right));
                        nextWeek();
                        searchBar.setText("");
                        DayAdapter recycleAdapter = new DayAdapter(getContext());
                        recycleAdapter.setData(convertDate(dates));
                        recyclerView.setAdapter(recycleAdapter);
                        return true;
                }
                return false;
            }
        });

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToday();
                DayAdapter recycleAdapter = new DayAdapter(getContext());
                recycleAdapter.setData(convertDate(dates));
                recyclerView.setAdapter(recycleAdapter);
            }
        });


        schedule.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        schedule.setBackground(getResources().getDrawable(R.drawable.ic_jump_schdule2));
                        return true;
                    case MotionEvent.ACTION_UP:
                        schedule.setBackground(getResources().getDrawable(R.drawable.ic_jump_schedule));
                        return true;
                }
                return false;
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!searchBar.getText().toString().equals("")){
                    dates = convertScheduleDate(getMatchedDates(s.toString()));
                    ReBindAdapter(s.toString());
                }else{
                    ReBindAdapter();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!searchBar.getText().toString().equals("")){
                    dates = convertScheduleDate(getMatchedDates(s.toString()));
                    ReBindAdapter(s.toString());
                }else{
                    ReBindAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!searchBar.getText().toString().equals("")){
                    dates = convertScheduleDate(getMatchedDates(s.toString()));
                    ReBindAdapter(s.toString());
                }else{
                    ReBindAdapter();
                }

            }
         });

        addBtn.bringToFront();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addBtn.bringToFront();
    }

    @Override
    public void onResume() {
        super.onResume();
        getToday();
        ReBindAdapter();
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            addBtn =  mainActivity.findViewById(R.id.add);
        }
    }

    private void ReBindAdapter(){
        DayAdapter recycleAdapter = new DayAdapter(getContext());

        recycleAdapter.setData(convertDate(dates));
        recyclerView.setAdapter(recycleAdapter);
    }

    private void ReBindAdapter(String s){
        DayAdapter recycleAdapter = new DayAdapter(getContext());

        recycleAdapter.setSearchString(s);
        recycleAdapter.setData(convertDate(dates));
        recyclerView.setAdapter(recycleAdapter);
    }

    void getToday() {dates = getWeek(); }

    void nextWeek(){
        dates = getNextWeek(dates);
    }

    void lastWeek(){
        dates = getLastWeek(dates);
    }

    void jumpTo(Date date) { dates = getWeekByDate(date); }
}