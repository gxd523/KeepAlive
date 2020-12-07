package com.demo.keepalive.doubleprocess;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

import com.demo.keepalive.NotificationUtil;
import com.demo.keepalive.PermissionUtil;
import com.demo.keepalive.ProcessConnect;

public class RemoteService extends Service {
    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Intent intent = new Intent(RemoteService.this, LocalService.class);
            startService(intent);
            bindService(intent, connection, BIND_ABOVE_CLIENT);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent localServiceIntent = new Intent(RemoteService.this, LocalService.class);
        bindService(localServiceIntent, connection, BIND_ABOVE_CLIENT);

        Notification notification = NotificationUtil.createNotification(RemoteService.this, "Remote");
        startForeground(1111, notification);

        if (PermissionUtil.checkFloatPermission(RemoteService.this)) {
            AlertDialog alertDialog = new AlertDialog.Builder(RemoteService.this)
                    .setTitle(String.format("%s成功复活!",RemoteService.class.getSimpleName()))
                    .create();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alertDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                alertDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
            } else {
                alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            }

            alertDialog.show();
        }

//        Intent intent1 = new Intent(RemoteService.this, OnePixelActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent1);
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
        Log.e("RemoteService", "onCreate-->");
    }

    @Override
    public void onDestroy() {
        Log.e("RemoteService", "onDestroy-->");
        super.onDestroy();
    }
}