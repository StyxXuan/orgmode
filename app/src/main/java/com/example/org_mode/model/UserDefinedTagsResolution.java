package com.example.org_mode.model;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.org_mode.model.FileResolution.getContent;

public class UserDefinedTagsResolution {
    static List<String> UserDefinedTags;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static List<String>  getAllUserDifinedTags(){
        return UserDefinedTags;
    }

    public static void insertUserDifinedTags(String newTag){
        UserDefinedTags.add(newTag);
    }

    public static List<String>  getMatchedUserDifinedTags(String s){
        List<String> tags = new ArrayList<String>();
        for(int i=0; i<UserDefinedTags.size(); i++){
            if(UserDefinedTags.get(i).contains(s))
                tags.add(UserDefinedTags.get(i));

        }
        return tags;
    }

    public static List<String> getUserDefinedTags(String s){
        List<String>UDT = new ArrayList<String>();
        Pattern p = Pattern.compile( s );
        for(int i=0; i<UserDefinedTags.size(); i++){
            int count = 0;
            Matcher m = p.matcher(UserDefinedTags.get(i));
            while( m.find() )
                count++;

            if(count > 0)
                UDT.add(UserDefinedTags.get(i));

        }

        return UDT;
    }

    static public void InitTags(String FilePath){
        UserDefinedTags = new ArrayList<String>();
//        createTags();
        File file = new File(FilePath);
        if(!file.exists()){
            file.mkdir();
        }
        File newFile = new File(file, "TagSettings.org");

        try {
            if(!newFile.exists()) {
                newFile.createNewFile();
            }
        }catch (IOException e){
        }

        StringBuffer sb = getContent(newFile).get("TagSettings.org");
        if(sb == null)
            return;

        String UsrTags = sb.toString();
        for(String _tag : UsrTags.split("\n")){
            UserDefinedTags.add(_tag);
        }

    }

    static public void FlushTags(String FilePath){
        File file = new File(FilePath);
        if(!file.exists()){
            file.mkdir();
        }
        File newFile = new File(file, "TagSettings.org");

        if(newFile.exists())
            newFile.delete();


        String Content = "";
        for(int i=0; i<UserDefinedTags.size(); i++)
            Content += UserDefinedTags.get(i) + "\n";


        try {
            newFile.createNewFile();
            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write(Content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void createTags(){
        UserDefinedTags.add("tag1");
        UserDefinedTags.add("tag2");
        UserDefinedTags.add("tag3");
        UserDefinedTags.add("tag4");
    }
    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
