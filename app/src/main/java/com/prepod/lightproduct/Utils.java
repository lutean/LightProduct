package com.prepod.lightproduct;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.MediaSessionCompat;

import com.prepod.lightproduct.containers.Token;

public class Utils {

    public static void showMessage(CoordinatorLayout coordinatorLayout, String message){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void storeToken(Context context, Token token){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", token.getToken());
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString("token", "");
    }

    public static void clearToken(Context context) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", "");
        editor.commit();
    }


}
