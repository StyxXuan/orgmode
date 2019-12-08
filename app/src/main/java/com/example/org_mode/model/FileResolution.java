package com.example.org_mode.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.org_mode.utils.AutoReceiver;
import com.example.org_mode.utils.ScheduleDate;
import com.example.org_mode.utils.ScheduleItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.org_mode.model.PinInfoResolution.IniteSettings;
import static com.example.org_mode.model.UserDefinedTagsResolution.InitTags;
import static com.example.org_mode.utils.DateUtils.convertDate;
import static com.example.org_mode.utils.DateUtils.getPresentTime;
import static com.example.org_mode.utils.NotificationSender.SendNotification;

public class FileResolution {

    static Vector<ScheduleItem> items;

    void SetNotification(Context context, ScheduleItem it){
        Intent intent = new Intent(context, AutoReceiver.class);
        intent.setAction("NOTIFICATION");
        intent.putExtra("HeadLine", it.getDescription());
        intent.putExtra("DeadLine", it.getDeadline());

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;

        long triggerAtMillis = convertDate(new ScheduleDate(it.getDeadline())).getTime();
        new Date().getTime();
        if(it.getRepeatTIme().equals(""))
            manager.set(type, triggerAtMillis, pi);
        else {
            long intervalMillis = 1000 * 60 * 24 * Long.parseLong(it.getRepeatTIme());
            manager.setInexactRepeating(type, triggerAtMillis, intervalMillis, pi);
        }

    }

    public static void CreateFile(String FilePath){
        File file = new File(FilePath);
        if(file.exists())
            return;

        try{
            file.createNewFile();
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }

    public static  List<ScheduleDate> getMatchedDates(String s){
        List<ScheduleDate> dates = new ArrayList<ScheduleDate>();
        for(int i=0; i<items.size(); i++){
            if(isMatched(items.get(i), s)){
                ScheduleDate newDate = new ScheduleDate(items.get(i).getShowOn());
                if(!dates.contains(newDate))
                    dates.add(newDate);

            }
        }
        return dates;
    }

    public static  List<String> filterFileByName(List<String> names, String s){
        List<String> FileName = new ArrayList<String>();
        for(int i=0; i< names.size(); i++){
            if(names.get(i).contains(s))
                FileName.add(names.get(i));
        }

        return FileName;
    }

    public static  List<String> getMatchedFiles(String s){
        List<String> files = new ArrayList<String>();
        for(int i=0; i<items.size(); i++){
            if(isMatched(items.get(i), s)){
                if(!files.contains(items.get(i).getFileName()))
                    files.add(items.get(i).getFileName());

            }
        }
        return files;
    }

    public static List<ScheduleItem> getMatchedItems(String s){
        List<ScheduleItem> dates = new ArrayList<ScheduleItem>();
        for(int i=0; i<items.size(); i++){
            if(isMatched(items.get(i), s)){
                dates.add(items.get(i));

            }
        }

        return dates;
    }

    public static boolean isMatched(ScheduleItem scheduleItem, String s){
        Boolean b = false;
        b |= scheduleItem.getDescription().contains(s) | scheduleItem.getNote().contains(s);
        return b;
    }

    public static File[] getFiles(String FilePath){
        return new File(FilePath).listFiles();
    }

    public static List<String> getFileNames(String FilePath){
        List<String>FileNames = new ArrayList<String>();

        for(File _file : getFiles(FilePath)){
            FileNames.add(_file.getName());
        }

        return FileNames;
    }

    public static void ReplaceScheduleItem(ScheduleItem oldItem, ScheduleItem newItem){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).printItem().equals(oldItem.printItem())){
                items.remove(i);
                items.add(newItem);
            }
        }
    }

    public static List<ScheduleItem> getItemsbyFileName(String FileName){
        List<ScheduleItem> Items= new ArrayList<ScheduleItem>();

        for(int i=0; i<items.size(); i++){
            if(items.get(i).getFileName().equals(FileName))
                Items.add(items.get(i));
        }

        return Items;
    }

    public static List<ScheduleItem> getItemsbyFileName(String FileName, String s){
        List<ScheduleItem> Items= new ArrayList<ScheduleItem>();

        for(int i=0; i<items.size(); i++){
            if(items.get(i).getFileName().equals(FileName) && items.get(i).getDescription().contains(s))
                Items.add(items.get(i));
        }

        return Items;
    }

    public static List<ScheduleItem> getExpiredItems(ScheduleDate date){
        List<ScheduleItem> Items= new ArrayList<ScheduleItem>();
        for(int i=0; i<items.size(); i++){
            if(!items.get(i).getTags().equals("TODO"))
                continue;

            if(new ScheduleDate(items.get(i).getDeadline()).isExpired(date)) {
                Items.add(items.get(i));
            }
        }

        return Items;
    }

    public static Map<String, StringBuffer> getContent(File file) {
        Map<String, StringBuffer> Contents = new HashMap<String, StringBuffer>();

        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = new FileInputStream(file.getAbsolutePath());
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
            reader.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Contents.put(file.getName(), sb);
        return Contents;
    }

    public static Map<String, StringBuffer> getContent(File[] files) {
        Map<String, StringBuffer> Contents = new HashMap<String, StringBuffer>();
        for(File _file : files) {
            Contents.putAll(getContent(_file));
        }

        return Contents;
    }

    public static Vector<ScheduleItem> getItems(String FileName, String Content) {
        Vector<ScheduleItem> Items = new Vector<ScheduleItem>();
        String[] lines = Content.split("\n");
        ScheduleItem si = null;

        for(String _line : lines) {
            if(_line.equals("\n") || _line.equals(""))
                continue;

            String []words = _line.split(" ");
            String Tag = "";
            String Value = "";
            for(String _word : words) {
                if(isTag(_word)) {
                    Tag += _word;
                }else {
                    if(!Value.equals(""))
                        Value += " ";

                    Value += _word;
                }
            }


            if (isTitle(Tag)) {
                if(si != null)
                    Items.add(si);

                String []WordItem = Value.split(" ");
                String Description = "";
                for(int i = 2; i < WordItem.length; i++) {
                    Description += WordItem[i];
                    Description += " ";
                }
                si = new ScheduleItem(Description, FileName);
                si.setTags(WordItem[0]);
                si.setPriority(WordItem[1]);
                si.setState(Tag);
            }else {
                si.setKeyValue(Tag, Value);
            }

        }

        if(si != null)
            Items.add(si);

        for(int i=0; i<Items.size(); i++){
            Log.d("item", Items.get(i).printItem());
        }

        return Items;
    }

    public static Boolean isTag(String word) {
        return word.equals("*") || word.equals("**") || word.equals("***")
                || word.equals("DEADLINE:") || word.equals("SCHEDULED:") || word.equals("NOTE:")
                || word.equals("SHOWON:") || word.equals("EDITTIME:") || word.equals("LABEL:")
                || word.equals("REPEAT:");
    }

    public static Boolean isTitle(String word) {
        return word.equals("*") || word.equals("**") || word.equals("***");
    }

    public static Vector<ScheduleItem> getItembyDate(String dates){
        return getItembyDate(new ScheduleDate(dates));
    }

    public static Vector<ScheduleItem> getItembyDate(ScheduleDate dates){
        Vector<ScheduleItem> Res = new Vector<ScheduleItem>();

        for(int i=0; i<items.size(); i++) {
//            if(!items.get(i).getTags().equals("TODO"))
//                continue;

            ScheduleDate deadline = new ScheduleDate(items.get(i).getDeadline());
            ScheduleDate showon = new ScheduleDate(items.get(i).getShowOn());

            if(showon.getDate().equals(dates.getDate()))
                Res.add(items.get(i));

        }

        return Res;
    }

    public static Vector<ScheduleItem> getItembyDate(ScheduleDate dates, String s){
        Vector<ScheduleItem> Res = new Vector<ScheduleItem>();

        for(int i=0; i<items.size(); i++) {
//            if(!items.get(i).getTags().equals("TODO"))
//                continue;

            ScheduleDate deadline = new ScheduleDate(items.get(i).getDeadline());
            ScheduleDate showon = new ScheduleDate(items.get(i).getShowOn());

            if(showon.getDate().equals(dates.getDate()) && items.get(i).getDescription().contains(s))
                Res.add(items.get(i));

        }

        return Res;
    }


    public static Vector<ScheduleItem> getItembyDate(Vector<ScheduleDate> dates){
        Vector<ScheduleItem> Res = new Vector<ScheduleItem>();
        for(int i = 0; i < dates.size(); i++) {
            Res.retainAll(getItembyDate(dates.get(i)));
        }
        return Res;
    }

    public static void insertItem(ScheduleItem si) {
        ScheduleItem newItem = new ScheduleItem((si));
        items.add(newItem);
    }

    public static void deleteItem(ScheduleItem si) {
        for(int i=0; i<items.size(); i++){
            if(si.printItem().equals(si.printItem()))
                items.remove(i);

        }
    }

    public static void deleteFile(String FilePath){
        File file = new File(FilePath);
        if(file.exists())
            file.delete();

    }

    public static ScheduleItem getItemByInfo(String Info){
        for(int i=0; i<items.size(); i++){
            if(items.get(i).printItem().equals(Info))
                return items.get(i);
        }

        return null;
    }
    static public void InitFileResolution(Context context, String FilePath, String SettingPath){
//        InitFile(FilePath);

        Log.d("FilePath", FilePath);

        File file = new File(FilePath);
        if(!file.exists())
            file.mkdir();

        File newFile = new File(file, "inbox.org");
        try {
            if(!newFile.exists()) {
                newFile.createNewFile();
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }

        IniteSettings(SettingPath);
        InitTags(SettingPath);
        Map<String, StringBuffer> Contents = new HashMap<String, StringBuffer>();
        for(File _file : getFiles(FilePath)) {
            Contents.putAll(getContent(_file));
        }

        items = new Vector<ScheduleItem>();
        for(String key : Contents.keySet()) {
            items.addAll(getItems(key, Contents.get(key).toString()));
        }

        for(int i=0; i<items.size(); i++){
            SendNotification(context, items.get(i));
        }

    }

    public static void FlushAll(String FilePath){
        Map<String, List<ScheduleItem>> FileItems = new HashMap<>();
        for(int i=0; i<items.size(); i++){
            if(FileItems.containsKey(items.get(i).getFileName()) == false)
                FileItems.put(items.get(i).getFileName(), new ArrayList<ScheduleItem>());

            FileItems.get(items.get(i).getFileName()).add(items.get(i));
        }

        for (Map.Entry<String, List<ScheduleItem>> entry : FileItems.entrySet()) {
            List<ScheduleItem> Items = entry.getValue();
            String Content = "";
            File file = new File(FilePath, entry.getKey());
            if(file.exists()){
                file.delete();
            }
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i=0; i<Items.size(); i++){
                Content += Items.get(i).printItem();
            }
            try {
                Log.d("Content", Content);
                Log.d("FilePath", file.getAbsolutePath());
                Log.d("FileName", file.getName());

                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(Content);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static private void InitFile(String FilePath){
        Log.d("Time Now", "[" + getPresentTime() + "]");
        File file = new File(FilePath);
        if(!file.exists()){
            file.mkdir();
        }
        File newFile = new File(file, "inbox.org");
        try {
            if(newFile.exists()) {
                newFile.delete();
                newFile.createNewFile();
            }
            else
                newFile.createNewFile();

            String Content = "* TODO [#A] Fuck a novel\n" +
                    "DEADLINE: <2019-11-20 Wed> \n" +
                    "SCHEDULED: <2019-11-20 Wed>\n" +
                    "SHOWON: <2019-11-20 Wed>\n"+
                    "NOTE: Hello\n" +
                    "** TODO [#A] SubTitles\n" +
                    "DEADLINE: <2019-11-20 Wed> \n" +
                    "SCHEDULED: <2019-11-20 Wed>\n" +
                    "SHOWON: <2019-11-20 Wed>\n"+
                    "NOTE: Hello\n" +
                    "*** TODO [#A] Write a novel\n" +
                    "DEADLINE: <2019-11-20 Wed> \n" +
                    "SCHEDULED: <2019-11-20 Wed>\n" +
                    "SHOWON: <2019-11-20 Wed>\n"+
                    "EDITTIME: [2019-11-09 Sat 11:35:00]\n"+
                    "NOTE: I'm Fine\n" +
                    "* DONE [#A] Write a novel\n" +
                    "DEADLINE: <2019-11-20 Wed> \n" +
                    "SCHEDULED: <2019-11-20 Wed>\n" +
                    "SHOWON: <2019-11-20 Wed>\n"+
                    "NOTE: I'm Fine\n" +
                    "* TODO [#A] Fuck a novel\n" +
                    "DEADLINE: <2019-11-07 Thu> \n" +
                    "SCHEDULED: <2019-11-07 Thu>\n" +
                    "SHOWON: <2019-11-07 Thu>\n"+
                    "EDITTIME: [2019-11-09 Sat 11:35:30]\n"+
                    "NOTE: Hello\n";

            FileWriter fileWriter = new FileWriter(newFile);
            fileWriter.write(Content);
            fileWriter.flush();
            fileWriter.close();
//
        }catch (IOException e){

        }
    };

}
