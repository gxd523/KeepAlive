package com.demo.keepalive.doubleprocess;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.demo.keepalive.NotificationUtil;
import com.demo.keepalive.ProcessConnect;

public class LocalService extends Service {
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Intent intent = new Intent(LocalService.this, RemoteService.class);
            startService(intent);
            bindService(intent, connection, BIND_ABOVE_CLIENT);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent remoteServiceIntent = new Intent(LocalService.this, RemoteService.class);
        bindService(remoteServiceIntent, connection, BIND_ABOVE_CLIENT);

        Notification notification = NotificationUtil.createNotification(LocalService.this, "Local");
        startForeground(1111, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnect.Stub() {
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("LocalService", "onCreate-->");
    }

    @Override
    public void onDestroy() {
        Log.e("LocalService", "onDestroy-->");
        super.onDestroy();
    }
}