package uz.itjunior.yaseen.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class LanguageManager {

    private static final String TAG = "LanguageManager";


    public static void setDefaultLocale(Activity activity, String lng) {
        SharedPreferences preferences = activity.getSharedPreferences("Requests", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        activity.getBaseContext().getResources().updateConfiguration(
                configuration, activity.getBaseContext().getResources().getDisplayMetrics());
        editor.putString("language", lng);
        editor.apply();
    }

}
