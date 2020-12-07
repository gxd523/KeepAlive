package com.demo.keepalive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;

/**
 * Created by guoxiaodong on 12/7/20 10:28
 */
public class NotificationUtil {
    public static Notification createNotification(Context context, String title) {
        Notification.Builder builder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelId = "CHANNEL_ID_1111";

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(
                    channelId, "CHANNEL_NAME_aaa", NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);

            builder = new Notification.Builder(context, channelId);
        } else {
            builder = new Notification.Builder(context);
        }
        return builder
                .setContentTitle(title)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .build();
    }
}