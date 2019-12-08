package com.example.org_mode.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

import static com.example.org_mode.utils.DateUtils.convertDate;

public class NotificationSender {
    public static void SendNotification(Context mcontext, ScheduleItem item){
        Intent intent = new Intent(mcontext, AutoReceiver.class);
        intent.setAction("NOTIFICATION");
        intent.putExtra("HeadLine", item.getDescription());
        intent.putExtra("DeadLine", item.getDeadline());

        PendingIntent pi = PendingIntent.getBroadcast(mcontext, 0, intent, 0);
        AlarmManager manager = (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;
        ScheduleDate deadLine = new ScheduleDate(item.getDeadline());
        long triggerAtMillis = convertDate(deadLine).getTime();
        manager.set(type, triggerAtMillis, pi);
    }

}
