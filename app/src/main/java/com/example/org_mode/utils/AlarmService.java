package com.example.org_mode.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.org_mode.R;
import com.example.org_mode.view.MainActivity;

public class AlarmService extends Service {
    private static final int NOTIFICATION_ID = 1000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent2 = new Intent(AlarmService.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent2, 0);
                Notification notify = new Notification.Builder(getApplication())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setTicker("您的***项目即将到期，请及时处理！")
                        .setContentTitle("项目到期提醒")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                manager.notify(NOTIFICATION_ID, notify);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
