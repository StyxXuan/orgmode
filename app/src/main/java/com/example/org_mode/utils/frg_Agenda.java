package com.example.org_mode.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.org_mode.R;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_Agenda extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_agenda, container, false);
        return view;
    }
}