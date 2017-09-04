package com.zncm.dminter.qiqicopyplug;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jiaomx on 2017/8/23.
 */

public class Xutils {

    public static void clearDefault() {
        PackageManager pm = MyApp.getInstance().ctx.getPackageManager();
        pm.clearPackagePreferredActivities( MyApp.getInstance().ctx.getPackageName());
    }
    public static void sendTo(Context ctx, String sendWhat) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sendWhat);
        ctx.startActivity(shareIntent);
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
