package com.zncm.dminter.qiqicopyplug;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class SPHelper {
    public static int getGridColumns(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt("grid_columns", 4);
    }

    public static void setGridColumns(Context context, int grid_columns) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt("grid_columns", grid_columns).commit();
    }



    public static String getFrom() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance().getApplicationContext());
        return sp.getString("from", "");
    }


    public static void setFrom(Context context, String from) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("from", from).commit();
    }




    public static String getTo() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance().getApplicationContext());
        return sp.getString("to", "");
    }


    public static void setTo(Context context, String to) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString("to", to).commit();
    }





    public static boolean isSnowFall(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("is_snowfall", true);
    }

    public static void setIsSnowFall(Context context, boolean is_snowfall) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("is_snowfall", is_snowfall).commit();
    }





    public static boolean isRootMode(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("is_root_mode", false);
    }

    public static void setIsRootMode(Context context, boolean is_root_mode) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean("is_root_mode", is_root_mode).commit();
    }

}
