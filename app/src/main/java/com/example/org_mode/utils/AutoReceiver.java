package com.example.org_mode.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.org_mode.R;
import com.example.org_mode.view.MainActivity;
import com.ycbjie.notificationlib.NotificationUtils;

public class AutoReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 1000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("NOTIFICATION")) {
            Log.d("Notification","get Notifications");

            String headLine = intent.getStringExtra("HeadLine");
            String deadLine = intent.getStringExtra("DeadLine");
            NotificationUtils notificationUtils = new NotificationUtils(context);
            notificationUtils.sendNotification(NOTIFICATION_ID, headLine, deadLine, R.mipmap.ic_launcher);
        }

    }

}
