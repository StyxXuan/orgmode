package com.example.org_mode.presenter.fragment;

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

        Button button = view.findViewById(R.id.insertFile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(getContext().getFilesDir().getAbsolutePath() +
                        "/OrgFiles/");
                if(!file.exists()){
                    file.mkdir();
                }

                File newFile = new File(file, "1.org");
                try {
                    newFile.createNewFile();
                    String Content = "* TODO [#A] Write a novel\n" +
                            "DEADLINE: <2018-11-20 Tue> \n" +
                            "SCHEDULED: <2019-10-09 Wed>\n" +
                            "NOTE: I'm Fine\n" +
                            "* TODO [#A] Write a novel\n" +
                            "DEADLINE: <2018-11-20 Tue> \n" +
                            "SCHEDULED: <2019-10-09 Wed>\n" +
                            "NOTE: I'm Fine";
                    FileWriter fileWriter = new FileWriter(newFile);
                    fileWriter.write(Content);
                    fileWriter.flush();
                    fileWriter.close();
//
                }catch (IOException e){

                }
                Log.i("FileName: ", file.getPath());
            }
        });

        return view;
    }
}