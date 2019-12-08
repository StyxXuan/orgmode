package com.example.org_mode.presenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.DayAdapter;
import com.example.org_mode.presenter.Adapter.FileAdapter;
import com.example.org_mode.utils.DialogUtil;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;
import com.example.org_mode.view.AddActivity;
import com.example.org_mode.view.MainActivity;
import com.example.org_mode.view.setFileActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.org_mode.model.FileResolution.CreateFile;
import static com.example.org_mode.model.FileResolution.filterFileByName;
import static com.example.org_mode.model.FileResolution.getFileNames;
import static com.example.org_mode.model.FileResolution.getFiles;
import static com.example.org_mode.utils.DateUtils.convertDate;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_SelectFile extends Fragment {
    RecyclerView recyclerViewPined, recyclerViewAll;
    TextView tLeft, tMeLeft, tMeRight, tMedium;
    EditText eText;
    List<String> FileNames;

    public static String fileName;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_select_file, container, false);
        EventBus.getDefault().register(this);

        recyclerViewAll = view.findViewById(R.id.frg_Select_AllRecyclerView);

        tLeft = view.findViewById(R.id.title_left);
        tLeft.setBackground(getResources().getDrawable(R.drawable.ic_add));
        tMeRight = view.findViewById(R.id.title_meright);
        tMeRight.setVisibility(View.INVISIBLE);
        tMedium = view.findViewById(R.id.title_medium);
        tMedium.setText("Files");
        tMeLeft = view.findViewById(R.id.title_meleft);
        tMeLeft.setVisibility(View.INVISIBLE);
        eText = view.findViewById(R.id.search);
        eText.setHint("Filter Files By Name");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final FileAdapter recycleAdapter = new FileAdapter(getContext());
        FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/");
        recycleAdapter.setmFiles(FileNames);

        recyclerViewAll.setLayoutManager(layoutManager);
        recyclerViewAll.setAdapter(recycleAdapter);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());

        tLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });
        recycleAdapter.setItemClickListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("FileName", getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                        "/OrgFiles/").get(position));

                EventBus.getDefault().post(bundle);
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        eText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!eText.getText().toString().equals("")){
                    FileNames = filterFileByName(getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/"), eText.getText().toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                reBindAdapter();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!eText.getText().toString().equals("")){
                    FileNames = filterFileByName(getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/"), eText.getText().toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                reBindAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!eText.getText().toString().equals("")){
                    FileNames = filterFileByName(getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/"), eText.getText().toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                reBindAdapter();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reBindAdapter();
    }



    private void reBindAdapter(){
        FileAdapter recycleAdapter = new FileAdapter(getContext());
        FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/");
        recycleAdapter.setmFiles(FileNames);
        recyclerViewAll.setAdapter(recycleAdapter);
    }

    private void dialog(){
        DialogUtil dialogUtil = new DialogUtil(this.getActivity(), this.getContext());
        dialogUtil.dialog("New File","Enter the name of the file to create. The extension .org will be added automatically.","Cancel","Create");
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener(){
            @Override
            public void onItemCancelClick() {
            }

            @Override
            public void onItemConfirmClick() {
                if(fileName != null && !fileName.equals(""))
                    CreateFile(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/" + fileName + ".org");

                reBindAdapter();
                System.out.println(fileName);
            }
        });
    }
}