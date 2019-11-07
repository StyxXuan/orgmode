package com.example.org_mode.utils;

public class Date {
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

    public Date(String date) {
        String []words = date.split(" ");
        String []times = words[0].split("-");
        this.WeekDay = words[1];
        this.Year = times[0];
        this.Month = times[1];
        this.Day = times[2];
    }

    public String getDate() {
        return Year + "-" + Month + "-" + Day + " " + WeekDay;
    }

    //compare with date whether it is older
    public Boolean isExpired(String date) {
        Date DatetoCompare = new Date(date);
        return isExpired(DatetoCompare);
    }

    public Boolean isExpired(Date date) {
        if(Year.compareTo(date.getYear()) >= 0)
            return true;

        if(Month.compareTo(date.getMonth()) >= 0)
            return true;

        if(Day.compareTo(date.getDay()) >= 0)
            return true;

        return false;
    }
}

