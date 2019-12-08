package com.example.org_mode.model;

import com.example.org_mode.utils.ScheduleItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.org_mode.model.FileResolution.getContent;

public class PinInfoResolution {
    static Map<String, Boolean> PinedInfo;

    public static Boolean getPinedInfo(String FileName) {
        if(PinedInfo.get(FileName) != null){
            return PinedInfo.get(FileName);
        }else{
            return false;
        }
    }

    public static void switchPinInfo(String FileName) {
        if(PinedInfo.get(FileName) != null){
            Boolean b = PinedInfo.get(FileName);
            PinedInfo.remove(FileName);
            PinedInfo.put(FileName, !b);
        }else{
            PinedInfo.put(FileName, true);
        }
    }

    static public void IniteSettings(String FilePath){
        File file = new File(FilePath);
        if(!file.exists()){
            file.mkdir();
        }
        File newFile = new File(file, "FileSettings.org");

        try {
            if(!newFile.exists()) {
                newFile.createNewFile();
            }
        }catch (IOException e){
        }

        PinedInfo = new HashMap<String, Boolean>();
        StringBuffer sb = getContent(newFile).get(newFile.getPath());
        if(sb == null)
            return;

        String Info = sb.toString();
        for(String _info : Info.split("\n")){
            String []words = _info.split(" ");
            PinedInfo.put(words[0], Boolean.parseBoolean(words[1]));
        }
    }

    static public void FlushSettings(String FilePath){
        String Content = "";
        File file = new File(FilePath, "FileSettings.org");
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Map.Entry<String, Boolean> entry : PinedInfo.entrySet()){
            Content += entry.getKey() + " " + entry.getValue().toString();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
