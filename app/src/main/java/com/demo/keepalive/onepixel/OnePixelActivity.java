package com.demo.keepalive.onepixel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * APP退到后台之后，锁屏时，启动OnePixelActivity，让APP变为前台进程
 * OnePixelActivity.onResume()时，如果是开屏则finish
 * OnePixelActivity需将Window设置为1像素
 * OnePixelActivity启动模式为singleInstance
 */
public class OnePixelActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("OnePixelActivity", "onCreate-->");

        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.START | Gravity.TOP);
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 0;
            params.y = 0;
            params.height = 1;
            params.width = 1;
            window.setAttributes(params);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && powerManager.isInteractive()) {
            finish();// 亮屏时关闭OnePixelActivity
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("OnePixelActivity", "onDestroy-->");
    }
}