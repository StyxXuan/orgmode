package com.example.org_mode.presenter.fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
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
import com.example.org_mode.presenter.Adapter.TodoAdapter;
import com.example.org_mode.view.MainActivity;

import java.util.List;

import ezy.ui.view.RoundButton;

import static com.example.org_mode.model.FileResolution.getFileNames;
import static com.example.org_mode.model.FileResolution.getMatchedDates;
import static com.example.org_mode.model.FileResolution.getMatchedFiles;
import static com.example.org_mode.utils.DateUtils.convertDate;
import static com.example.org_mode.utils.DateUtils.convertScheduleDate;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class frg_Todo extends Fragment {
    List<String> FileNames;
    RecyclerView recyclerView;
    RoundButton ShowAll, TODO;
    TextView tLeft, tMeLeft, tMeRight, tMedium;
    EditText searchBar;
    TodoAdapter recycleAdapter;
    ImageButton addBtn;

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_todo, container, false);
        recyclerView = view.findViewById(R.id.TodoRecyView);

        tMeRight = view.findViewById(R.id.title_meright);
        tMeRight.setVisibility(View.INVISIBLE);
        tMedium = view.findViewById(R.id.title_medium);
        tMedium.setText("TODO");
        tMeLeft = view.findViewById(R.id.title_meleft);
        tMeLeft.setVisibility(View.INVISIBLE);

        searchBar = view.findViewById(R.id.search);
        ShowAll = view.findViewById(R.id.ShowAll);
        TODO = view.findViewById(R.id.TODO);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycleAdapter = new TodoAdapter(getContext());
        FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/");
        recycleAdapter.setFileName(FileNames);

        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setItemAnimator( new DefaultItemAnimator());

        RoundDrawable blueOne = new RoundDrawable(true);
        RoundDrawable whiteOne = new RoundDrawable(true);

        ShowAll.setSelected(true);
        TODO.setSelected(false);

        ShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleAdapter = new TodoAdapter(getContext());
                recycleAdapter.setFileName(FileNames);
                recyclerView.setAdapter(recycleAdapter);

                if(!ShowAll.isSelected()){
                    ShowAll.setSelected(true);
                    ShowAll.setTextColor(getResources().getColor(R.color.white));
                    TODO.setTextColor(getResources().getColor(R.color.light_blue));
                    TODO.setSelected(false);
                }

            }
        });

        TODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycleAdapter = new TodoAdapter(getContext(), Boolean.FALSE);
                recycleAdapter.setFileName(FileNames);
                recyclerView.setAdapter(recycleAdapter);
                if(!TODO.isSelected()){
                    TODO.setSelected(true);
                    TODO.setTextColor(getResources().getColor(R.color.white));
                    ShowAll.setTextColor(getResources().getColor(R.color.light_blue));
                    ShowAll.setSelected(false);
                }
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!searchBar.getText().toString().equals("")){
                    FileNames = getMatchedFiles(s.toString());
                    Log.i("s", s.toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                ReBindAdapter();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(!searchBar.getText().toString().equals("")){
                    FileNames = getMatchedFiles(s.toString());
                    Log.i("s", s.toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                ReBindAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!searchBar.getText().toString().equals("")){
                    FileNames = getMatchedFiles(s.toString());
                    Log.i("s", s.toString());
                }else{
                    FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/");
                }
                ReBindAdapter();
            }
        });

        return view;
    }

    private void ReBindAdapter(){
        recycleAdapter = new TodoAdapter(getContext());
        recycleAdapter.setFileName(FileNames);
        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FileNames = getFileNames(getContext().getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/");
        ReBindAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        addBtn.bringToFront();
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

    private static class RoundDrawable extends GradientDrawable {
        private boolean mIsStadium = false;

        private ColorStateList mSolidColors;
        private int mFillColor;

        public RoundDrawable(boolean isStadium) {
            mIsStadium = isStadium;
        }

        public void setSolidColors(ColorStateList colors) {
            mSolidColors = colors;
            setColor(colors.getDefaultColor());
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            if (mIsStadium) {
                RectF rect = new RectF(getBounds());
                setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
            }
        }

        @Override
        public void setColor(int argb) {
            mFillColor = argb;
            super.setColor(argb);
        }

        @Override
        protected boolean onStateChange(int[] stateSet) {
            if (mSolidColors != null) {
                final int newColor = mSolidColors.getColorForState(stateSet, 0);
                if (mFillColor != newColor) {
                    setColor(newColor);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isStateful() {
            return super.isStateful() || (mSolidColors != null && mSolidColors.isStateful());
        }
    }
}
