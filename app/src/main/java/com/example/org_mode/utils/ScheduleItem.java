package com.example.org_mode.utils;

import android.util.Log;

public class ScheduleItem {
    private String FileName = "";
    private String State = "";
    private String Priority = "";
    private String Tags = "";
    private String DeadLine = "";
    private String Scheduled = "";
    private String ShowOn = "";
    private String Description = "";
    private String Note= "";

    public ScheduleItem() {};

    public ScheduleItem(String Description, String FileName){
        this.FileName = FileName;
        this.Description = Description;
    };

    public String getFileName() {
        return FileName;
    }

    public String getState() {
        return State;
    }

    public String getPriority() {
        return Priority.replace("<", "").replace(">", "");
    }

    public String getTags() {
        return Tags;
    }

    public String getDeadline() {
        return DeadLine.replace("<", "").replace(">", "");
    }

    public String getScheduled() {
        return Scheduled.replace("<", "").replace(">", "");
    }

    public String getShowOn() {
        return ShowOn.replace("<", "").replace(">", "");
    }

    public String getDescription() {
        return Description;
    }

    public String getNote() {
        return Note;
    }

    public void setState(String state) {
        this.State = state;
    }

    public void setPriority(String priority) {
        this.Priority = priority;
    }

    public void setTags(String tag) {
        this.Tags = tag;
    }

    public void setDeadline(String deadLine) {
        this.DeadLine = deadLine;
    }

    public void setScheduled(String scheduled) {
        this.Scheduled = scheduled;
    }

    public void setShowon(String showOn) {
        this.ShowOn = showOn;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public void setKeyValue(String Key, String Value) {
        if(Key.equals("DEADLINE:")) {
            this.setDeadline(Value);
        }else if(Key.equals("SCHEDULED:")) {
            this.setScheduled(Value);
        }else if(Key.equals("NOTE:")) {
            this.setNote(Value);
        }else if(Key.equals("SHOWON:")) {
            this.setShowon(Value);
        }
    }

    public void printItem() {
        String Content = "";
        Content += State + " " + Tags + " " + Priority +  " " + Description + "\n";

        if(!DeadLine.equals(""))
            Content += "DEADLINE: " + DeadLine + "\n";

        if(!Scheduled.equals(""))
            Content += "SCHEDULED: " + Scheduled + "\n";

        if(!ShowOn.equals(""))
            Content += "SHOWON: " + ShowOn + "\n";

        if(!Note.equals(""))
            Content += "NOTE: " + Note + "\n";


        Log.i("Content", Content);
    }
}