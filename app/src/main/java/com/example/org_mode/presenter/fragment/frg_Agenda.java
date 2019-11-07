package com.example.org_mode.presenter.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.DayAdapter;
import com.example.org_mode.presenter.Adapter.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_Agenda extends Fragment {
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_agenda, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DayAdapter recycleAdapter = new DayAdapter(getContext());
        recycleAdapter.setData(getDates());

        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        return view;
    }

    List<String> getDates(){
        List<String> datas = new ArrayList<>();
        for(int i=0; i<7; i++){
            datas.add("1234567890");
        }
        return datas;
    }

}