package com.demo.keepalive;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by guoxiaodong on 12/7/20 12:00
 */
public class PermissionUtil {
    public static boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsMgr == null) {
                return false;
            }
            int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context.getPackageName());
            return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(context);
        } else {
            try {
                Class<?> ContextClass = Context.class;
                Field declaredField = ContextClass.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(ContextClass);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = ContextClass.getMethod("getSystemService", String.class).invoke(context, str2);
                Class<?> AppOpsManagerClass = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = AppOpsManagerClass.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = AppOpsManagerClass.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(AppOpsManagerClass);
            } catch (Exception e) {
                return false;
            }
        }
    }
}