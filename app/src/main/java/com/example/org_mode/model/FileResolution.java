package com.example.org_mode.model;

import com.example.org_mode.utils.Date;
import com.example.org_mode.utils.ScheduleItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class FileResolution {

    static Vector<ScheduleItem> items;

    public static File[] getFiles(String FilePath){
        return new File(FilePath).listFiles();
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

        return Items;
    }

    public static Boolean isTag(String word) {
        return word.equals("*") || word.equals("**") || word.equals("***")
                || word.equals("DEADLINE:") || word.equals("SCHEDULED:") || word.equals("NOTE:")
                || word.equals("SHOWON:");
    }

    public static Boolean isTitle(String word) {
        return word.equals("*") || word.equals("**") || word.equals("***");
    }

    public static Vector<ScheduleItem> getItembyDate(String dates){
        return getItembyDate(new Date(dates));
    }

    public static Vector<ScheduleItem> getItembyDate(Date dates){
        Vector<ScheduleItem> Res = new Vector<ScheduleItem>();

        for(int i=0; i<items.size(); i++) {
            if(!items.get(i).getTags().equals("TODO"))
                continue;

            Date deadline = new Date(items.get(i).getDeadline());
            Date showon = new Date(items.get(i).getShowOn());

            if(showon.getDate().equals(dates.getDate()) || deadline.isExpired(dates))
                Res.add(items.get(i));

        }

        return Res;
    }

    public static Vector<ScheduleItem> getItembyDate(Vector<Date> dates){
        Vector<ScheduleItem> Res = new Vector<ScheduleItem>();
        for(int i = 0; i < dates.size(); i++) {
            Res.retainAll(getItembyDate(dates.get(i)));
        }
        return Res;
    }

    public static void insertItem(ScheduleItem si) {
        items.add(si);
    }

    public static void deleteItem(ScheduleItem si) {
        items.remove(si);
    }

    static public void InitFileResolution(String FilePath){
        Map<String, StringBuffer> Contents = new HashMap<String, StringBuffer>();
        for(File _file : getFiles(FilePath)) {
            Contents.putAll(getContent(_file));
        }

        items = new Vector<ScheduleItem>();
        for(String key : Contents.keySet()) {
            items.addAll(getItems(key, Contents.get(key).toString()));
        }

        for(int i=0; i<items.size(); i++) {
            items.get(i).printItem();
        }
    }
}
