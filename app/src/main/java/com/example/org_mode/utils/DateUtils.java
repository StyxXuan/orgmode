package com.example.org_mode.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.org_mode.model.FileResolution.getItemsbyFileName;

public class DateUtils {
    public static String getEditTime(String FileName){
        List<ScheduleItem> FileItems = getItemsbyFileName(FileName);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd E hh:mm:ss");
        Date date = null;
        for(int i=0; i<FileItems.size(); i++) {
            if (FileItems.get(i).getEditTime().equals(""))
                continue;

            try {
                Date newDay = sdf.parse(FileItems.get(i).getEditTime());
                if(date == null){
                    date = newDay;
                }else{
                    if(date.before(newDay))
                        date = newDay;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if(date == null){
            try {
                date = sdf.parse(getPresentTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return sdf.format(date);
    }
    
    public static String getPresentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd E hh:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public static ScheduleDate getToday(){
        Calendar calendar = Calendar.getInstance();
        String b = new SimpleDateFormat("yyyy-MM-dd E").format(calendar.getTime());
        return new ScheduleDate(b);
    }
    public static Boolean isToday(Date date){
        Calendar calendar = Calendar.getInstance();
        String a = new SimpleDateFormat("yyyy-MM-dd E").format(date);
        String b = new SimpleDateFormat("yyyy-MM-dd E").format(calendar.getTime());
        Log.d("isToday",a + " " + b);
        return (a.equals(b));
    }

    public static Date convertDate(ScheduleDate date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd E");
        try {
            Date day = sdf.parse(date.getDate());
            return day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getNextDate(Date today){
        if(today == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    public static Date getLastDate(Date today){
        if(today == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.getTime();
    }

    public static List<Date> getWeek() {
        Calendar calendar = Calendar.getInstance();
        // 获取本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<Date> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            list.add(calendar.getTime());
        }

        return list;
    }

    public static List<Date> getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<Date> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i);
            list.add(calendar.getTime());
        }

        return list;
    }

    public static List<Date> getLastWeek(List<Date> thisWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(thisWeek.get(0));
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        return getWeek(calendar.getTime());
    }

    public static List<Date> getNextWeek(List<Date> thisWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(thisWeek.get(0));
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return getWeek(calendar.getTime());
    }

    public static List<Date> getWeekByDate(Date date){
       return getWeek(date);
    }

    public static List<ScheduleDate> convertDate(List<Date> dates){
        List<ScheduleDate> converted = new ArrayList<ScheduleDate>();
        for(int i=0; i<dates.size(); i++){
            converted.add(convertDate(dates.get(i)));
        }

        return converted;
    }

    public static List<Date> convertScheduleDate(List<ScheduleDate> dates){
        List<Date> converted = new ArrayList<Date>();
        for(int i=0; i<dates.size(); i++){
            try {
                converted.add(new SimpleDateFormat("yyyy-MM-dd E").parse(dates.get(i).getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return converted;
    }

    public static ScheduleDate convertDate(Date date) {
        return new ScheduleDate(new SimpleDateFormat("yyyy-MM-dd E").format(date));
    }

    public static String MonthNum2String(String month){
        int m = Integer.parseInt(month);
        switch (m){
            case 1:
                return "Jan";

            case 2:
                return "Feb";

            case 3:
                return "Mar";

            case 4:
                return "Apr";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "Aug";

            case 9:
                return "Sept";

            case 10:
                return "Oct";

            case 11:
                return "Nov";

            case 12:
                return "Dec";

            default:
                return "";
        }
    }
}
