package com.demo.keepalive;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends Activity {
    private ScreenOnReceiver screenOnReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerScreenStateReceiver();
    }

    @Override
    protected void onDestroy() {
        if (screenOnReceiver != null) {
            unregisterReceiver(screenOnReceiver);
        }
        super.onDestroy();
    }

    private void registerScreenStateReceiver() {
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, intentFilter);
    }
}