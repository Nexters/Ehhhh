package com.teamnexters.ehhhh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 현식 on 2015-08-12.
 */
public class AppPreference {

    public static void saveName(Context mContext, String state) {
        SharedPreferences callSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = callSettings.edit();
        editor.putString("saveName", state);
        editor.apply();
    }

    public static String loadName(Context mContext) {
        SharedPreferences callSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
        return callSettings.getString("saveName", "");
    }

    public static void saveMail(Context mContext, String state) {
        SharedPreferences callSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = callSettings.edit();
        editor.putString("saveMail", state);
        editor.apply();
    }

    public static String loadMail(Context mContext) {
        SharedPreferences callSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
        return callSettings.getString("saveMail", "");
    }
}
