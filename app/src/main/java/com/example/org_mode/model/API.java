package com.example.org_mode.model;

import android.util.Log;

import static com.example.org_mode.model.FileResolution.FlushAll;
import static com.example.org_mode.model.UserDefinedTagsResolution.FlushTags;

public class API {
    public static void initAll(){

    }

    public static void flushAll(String FilePath, String SettingPath){
        Log.d("Flush", "FlushAll");
        FlushAll(FilePath);
        FlushTags(SettingPath);
    }
}
