package com.zncm.dminter.qiqicopyplug;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jiaomx on 2017/8/23.
 */

public class Xutils {
    public static String getLastApp(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (processInfo.pkgList == null || processInfo.pkgList.length == 0) {
                        return "";
                    }
                    return processInfo.pkgList[0];
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            return componentInfo.getPackageName();
        }
        return "";
    }

    /*
     *获取程序的版本号
     */
    public static String getAppVersion(String packname) {

        try {
            PackageManager pm = MyApp.getInstance().ctx.getPackageManager();
            PackageInfo packinfo = pm.getPackageInfo(packname, 0);
            return packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static void clearDefault() {
        PackageManager pm = MyApp.getInstance().ctx.getPackageManager();
        pm.clearPackagePreferredActivities(MyApp.getInstance().ctx.getPackageName());
    }

    public static void sendTo(Context ctx, String sendWhat) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sendWhat);
        if (Xutils.notEmptyOrNull(SPHelper.getTo())) {
            shareIntent.setPackage(SPHelper.getTo());
        }
        ctx.startActivity(shareIntent);
    }


    private void initShareIntent(Context ctx, String packageName, String sendWhat) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        List<ResolveInfo> resInfo = ctx.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(packageName) ||
                        info.activityInfo.name.toLowerCase().contains(packageName)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, sendWhat);
                    share.putExtra(Intent.EXTRA_TEXT, sendWhat);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;
            ctx.startActivity(Intent.createChooser(share, "Select"));
        }
    }


    public static boolean notEmptyOrNull(String string) {
        if (string != null && !string.equalsIgnoreCase("null") && string.trim().length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static <T> boolean listNotNull(List<T> t) {
        if (t != null && t.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static void debug(Object string) {
        try {
            Log.i("[qiqicopyplug]", String.valueOf(string));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void tShort(String msg) {
        if (!notEmptyOrNull(msg)) {
            return;
        }
        Toast.makeText(MyApp.getInstance().ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void tLong(String msg) {
        if (!notEmptyOrNull(msg)) {
            return;
        }
        Toast.makeText(MyApp.getInstance().ctx, msg, Toast.LENGTH_LONG).show();
    }

}
