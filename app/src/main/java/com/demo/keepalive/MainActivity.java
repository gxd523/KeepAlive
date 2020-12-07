package com.demo.keepalive;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.demo.keepalive.doubleprocess.LocalService;
import com.demo.keepalive.doubleprocess.RemoteService;
import com.demo.keepalive.onepixel.ScreenOnReceiver;

public class MainActivity extends Activity {
    private ScreenOnReceiver screenOnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        onePixelActivity();

        doubleProcess();
    }

    private void doubleProcess() {
        Intent intent = new Intent(this, LocalService.class);
        startService(intent);
        intent = new Intent(this, RemoteService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        if (screenOnReceiver != null) {
            unregisterReceiver(screenOnReceiver);
        }
        super.onDestroy();
    }

    private void onePixelActivity() {
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, intentFilter);
    }
}