package com.example.org_mode.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.org_mode.R;
import com.example.org_mode.utils.StringFormatUtil;

public class TextOfFileActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_file);

        Intent intent = getIntent();
        String FileContent = intent.getStringExtra("FileContent");

        StringFormatUtil spanStr = new StringFormatUtil(this, FileContent,
                "TODO", R.color.light_blue).fillColor();

        if (spanStr != null)
            editText.setText(spanStr.getResult());

        else
            editText.setText(FileContent);

    }

}
