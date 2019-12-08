package com.example.org_mode.presenter.fragment;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.org_mode.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_Settings extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_settings, container, false);

        return view;
    }
}