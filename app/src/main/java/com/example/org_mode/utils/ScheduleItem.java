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
    private String EditTime = "";
    private String RepeatTIme = "";
    private String Label = "";

    public ScheduleItem() {};

    public ScheduleItem(ScheduleItem item){
        this.FileName = item.FileName;
        this.State = item.State;
        this.Priority = item.Priority;
        this.Tags = item.Tags;
        this.DeadLine = item.DeadLine;
        this.Scheduled = item.Scheduled;
        this.ShowOn = item.ShowOn;
        this.Description = item.Description;
        this.Note = item.Note;
        this.EditTime = item.EditTime;
        this.RepeatTIme = item.RepeatTIme;
    }

    public ScheduleItem(String Description, String FileName){
        this.FileName = FileName;
        this.Description = Description;
    };

    public String getEditTime(){return EditTime;}

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

    public String getLabel(){return Label;}

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

    public String getRepeatTIme() {
        return RepeatTIme;
    }

    public void setState(String state) {
        this.State = state;
    }

    public void setLabel(String label) {this.Label = label; }

    public void setRepeatTIme(int repeatTIme){this.RepeatTIme = Integer.toString(repeatTIme); }

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

    public void setEditTime(String edit){this.EditTime = edit; }

    public void setKeyValue(String Key, String Value) {
        if(Key.equals("DEADLINE:")) {
            this.setDeadline(Value);
        }else if(Key.equals("SCHEDULED:")) {
            this.setScheduled(Value);
        }else if(Key.equals("NOTE:")) {
            this.setNote(Value);
        }else if(Key.equals("SHOWON:")) {
            this.setShowon(Value);
        }else if(Key.equals("LABEL:")) {
            this.setLabel(Value);
        } else if(Key.equals("REPEAT:")) {
            this.setLabel(RepeatTIme);
        }else if(Key.equals("EDITTIME:")) {
            Value = Value.replace("[", "");
            Value = Value.replace("]", "");
            Log.d("EditTime", Value);
            this.setEditTime(Value);
        }
    }

    public String printItem() {
        String Content = "";
        Content += State + " " + Tags + " " + Priority +  " " + Description + "\n";

        if(!Label.equals(""))
            Content += "LABEL: " + Label + "\n";

        if(!DeadLine.equals(""))
            Content += "DEADLINE: " + DeadLine + "\n";

        if(!Scheduled.equals(""))
            Content += "SCHEDULED: " + Scheduled + "\n";

        if(!ShowOn.equals(""))
            Content += "SHOWON: " + ShowOn + "\n";

        if(!RepeatTIme.equals(""))
            Content += "REPEAT: " + RepeatTIme + "\n";

        if(!Note.equals(""))
            Content += "NOTE: " + Note + "\n";

        if(!EditTime.equals(""))
            Content += "EDITTIME: [" + EditTime + "]\n";

        return Content;
    }

    public String ItemToShow() {
        String Content = "";

        if(!Label.equals(""))
            Content += "LABEL: " + Label + "\n";

        if(!DeadLine.equals(""))
            Content += "DEADLINE: " + DeadLine + "\n";

        if(!Scheduled.equals(""))
            Content += "SCHEDULED: " + Scheduled + "\n";

        if(!ShowOn.equals(""))
            Content += "SHOWON: " + ShowOn + "\n";

        if(!RepeatTIme.equals(""))
            Content += "REPEAT: " + RepeatTIme + "\n";

        if(!Note.equals(""))
            Content += "NOTE: " + Note + "\n";

        if(!EditTime.equals(""))
            Content += "EDITTIME: [" + EditTime + "]\n";

        return Content;
    }

    public void setDescription(String headline) {
        this.Description = headline;
    }

    public void setFileName(String fileName){
        this.FileName = fileName;
    }
}