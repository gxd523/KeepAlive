package com.demo.keepalive.onepixel;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

/**
 * 监听锁屏、开屏
 */
public class ScreenOnReceiver extends BroadcastReceiver {
    private Handler mHandler;
    private boolean isScreenOn = true;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.e("ScreenOnReceiver", "onReceive-->");
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("ScreenOnReceiver", "onReceive-->ACTION_SCREEN_OFF");
            isScreenOn = false;
            startOnePixelActivity(context);
        } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
            isScreenOn = true;
            Log.e("ScreenOnReceiver", "onReceive-->ACTION_SCREEN_ON");
            if (pendingIntent != null) {
                pendingIntent.cancel();
            }
        }
    }

    private void startOnePixelActivity(final Context context) {
        if (mHandler == null) {
            mHandler = new Handler(Looper.myLooper());
        }
        mHandler.postDelayed(() -> {
            if (isScreenOn) {
                return;
            }
            if (pendingIntent != null) {
                pendingIntent.cancel();
            }
            Intent intent = new Intent(context, OnePixelActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            //启动一像素包活activity
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            try {
                pendingIntent.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1000);
    }
}