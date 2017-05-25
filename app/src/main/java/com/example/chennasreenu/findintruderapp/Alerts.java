package com.example.chennasreenu.findintruderapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by chennasreenu on 4/5/2017.
 */

public class Alerts {
    Context context_alerts;
    Alerts(Context context){
        context_alerts=context;
    }
    public void setNotification(){
        NotificationCompat.Builder notification_builder=new NotificationCompat.Builder(context_alerts);
        notification_builder.setSmallIcon(R.mipmap.app_icon);
        notification_builder.setContentTitle("Find Intruder");
        notification_builder.setContentText("Application running...");
        notification_builder.setOngoing(true);
        NotificationManager notificationManager=(NotificationManager)context_alerts.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification_builder.build());
    }

    public void sms_failed_notification(){
        NotificationCompat.Builder notification_builder=new NotificationCompat.Builder(context_alerts);
        notification_builder.setSmallIcon(R.mipmap.app_icon);
        notification_builder.setContentTitle("Find Intruder");
        notification_builder.setContentText("sending SMS failed");
        NotificationManager notificationManager=(NotificationManager)context_alerts.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification_builder.build());
    }

    public void sms_sent_notification(){
        NotificationCompat.Builder notification_builder=new NotificationCompat.Builder(context_alerts);
        notification_builder.setSmallIcon(R.mipmap.app_icon);
        notification_builder.setContentTitle("Find Intruder");
        notification_builder.setContentText("SMS sent");
        NotificationManager notificationManager=(NotificationManager)context_alerts.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2,notification_builder.build());
    }
}
