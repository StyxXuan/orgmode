package com.example.org_mode.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.org_mode.R;
import com.example.org_mode.presenter.Adapter.FileAdapter;
import com.example.org_mode.utils.DialogUtil;
import com.example.org_mode.utils.interfaces.OnRecyclerViewClickListener;

import org.greenrobot.eventbus.EventBus;

import static com.example.org_mode.model.FileResolution.CreateFile;
import static com.example.org_mode.model.FileResolution.getFileNames;

public class SelectFileActivity extends AppCompatActivity {
    RecyclerView recyclerViewPined, recyclerViewAll;
    TextView tLeft, tMeLeft, tMeRight, tMedium;
    EditText eText;
    public static String fileName;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_select_file);

        recyclerViewAll = findViewById(R.id.frg_Select_AllRecyclerView);

        tLeft = findViewById(R.id.title_left);
        tLeft.setBackground(getResources().getDrawable(R.drawable.ic_add));
        tMeRight = findViewById(R.id.title_meright);
        tMeRight.setVisibility(View.INVISIBLE);
        tMedium = findViewById(R.id.title_medium);
        tMedium.setText("Files");
        tMeLeft = findViewById(R.id.title_meleft);
        tMeLeft.setVisibility(View.INVISIBLE);
        eText = findViewById(R.id.search);
        eText.setHint("Filter Files By Name");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final FileAdapter recycleAdapter = new FileAdapter(this);
        recycleAdapter.setmFiles(getFileNames(this.getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/"));

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
                Intent intent = new Intent();
                intent.putExtra("FileName", getFileNames(getExternalFilesDir("").getAbsolutePath() +
                        "/OrgFiles/").get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        reBindAdapter();
    }

    private void reBindAdapter(){
        FileAdapter recycleAdapter = new FileAdapter(this);

        recycleAdapter.setmFiles(getFileNames(this.getExternalFilesDir("").getAbsolutePath() +
                "/OrgFiles/"));
        recyclerViewAll.setAdapter(recycleAdapter);
        recycleAdapter.setItemClickListener(new OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("FileName", getFileNames(getExternalFilesDir("").getAbsolutePath() +
                        "/OrgFiles/").get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void dialog(){
        DialogUtil dialogUtil = new DialogUtil(SelectFileActivity.this, this);
        dialogUtil.dialog("New File","Enter the name of the file to create. The extension .org will be added automatically.","Cancel","Create");
        dialogUtil.setOnItemClickListener(new DialogUtil.OnItemClickListener(){
            @Override
            public void onItemCancelClick() {
            }

            @Override
            public void onItemConfirmClick() {
                if(fileName != null && !fileName.equals(""))
                    CreateFile(getExternalFilesDir("").getAbsolutePath() +
                            "/OrgFiles/" + fileName + ".org");

                reBindAdapter();
                System.out.println(fileName);
            }
        });
    }
}
