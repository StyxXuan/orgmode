package com.example.org_mode.utils;

import android.util.Log;

import java.util.Date;

import static com.example.org_mode.utils.DateUtils.MonthNum2String;

public class ScheduleDate {
    private String WeekDay = "";
    private String Day = "";
    private String Month = "";
    private String Year = "";

    public String getWeekDay() {
        return WeekDay;
    }

    public String getDay() {
        return Day;
    }

    public String getMonth() {
        return Month;
    }

    public String getYear() {
        return Year;
    }

    public ScheduleDate(String date) {
        if(!date.equals("")) {
            String[] words = date.split(" ");

            this.WeekDay = words[1];
            String[] times = words[0].split("-");
            this.Year = times[0];
            this.Month = times[1];
            this.Day = times[2];
        }
    }

    public String getDate() {
        return Year + "-" + Month + "-" + Day + " " + WeekDay;
    }


    //compare with date whether it is older
    public Boolean isExpired(String date) {
        ScheduleDate DatetoCompare = new ScheduleDate(date);
        return isExpired(DatetoCompare);
    }

    public Boolean isExpired(ScheduleDate date) {
        if(Integer.parseInt(Year) < Integer.parseInt(date.getYear()))
            return true;

        if(Integer.parseInt(Month) < Integer.parseInt(date.getMonth()))
            return true;

        if(Integer.parseInt(Day) < Integer.parseInt(date.getDay()))
            return true;

        return false;
    }

    public String getInfoAboutExpiration(ScheduleDate date){
        String Text = "d. -";
        if(Integer.parseInt(date.getYear()) > Integer.parseInt(getYear())){
            Text += Integer.toString(Integer.parseInt(date.getYear()) - Integer.parseInt(getYear())) + "y";
        }else if(Integer.parseInt(date.getMonth()) > Integer.parseInt(getMonth())){
            Text += Integer.toString(Integer.parseInt(date.getMonth()) - Integer.parseInt(getMonth())) + "m";
        }else if(Integer.parseInt(date.getDay()) > Integer.parseInt(getDay())){
            Text += Integer.toString(Integer.parseInt(date.getDay()) - Integer.parseInt(getDay())) + "d";
        }

        return Text;
    }

    public String getText(){
        return getWeekDay() + " " + getDay() + " " + MonthNum2String(getMonth());
    }
}

