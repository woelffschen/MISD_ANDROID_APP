package com.misd.cookapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

public class HelperMethods {
    public static final String PREFS_USER_DATA = "user_data";

    public static Calendar pasteCalendar(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,day,hour,minute);
        return cal;
    }

    public static void setPreferenceString(Context context, String prefsName, String preferencesName, String key, String value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }

    public static String getPreferenceString(Context context, String prefsName, String preferencesName, String key) {
        // Restore preferences
        SharedPreferences settings = context.getSharedPreferences(prefsName, 0);
        String output = settings.getString(key, null);
        return output;
    }

    public static void setPreferenceObject(Context context, String prefsName, String key, Object value) {
        SharedPreferences  mPrefs = context.getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String jsonValue = gson.toJson(value);
        prefsEditor.putString(key, jsonValue);
        prefsEditor.commit();
    }

    public static Object getPreferenceObject(Context context, String prefsName, Class expectedClassType, String key) {
        SharedPreferences  mPrefs = context.getSharedPreferences(prefsName, 0);
        Gson gson = new GsonBuilder().registerTypeAdapter(Uri.class, new UriAdapter()).create();
        String json = mPrefs.getString(key, "");
        return gson.fromJson(json, expectedClassType);
    }

    public static boolean isNumericInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
