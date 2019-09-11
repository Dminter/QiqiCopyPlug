package com.zncm.dminter.qiqicopyplug;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class SPHelper {




    public static String getKey(String key) {
        return PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance().ctx).getString(key, "");
    }

    public static void setKey(String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(MyApp.getInstance().ctx).edit().putString(key, value).commit();
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







}
