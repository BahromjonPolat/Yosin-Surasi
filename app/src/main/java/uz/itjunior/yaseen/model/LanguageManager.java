package uz.itjunior.yaseen.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class LanguageManager {

    private static final String TAG = "LanguageManager";

    private Activity activity;
    public LanguageManager(Activity activity) {
        this.activity = activity;
    }


    public void setDefaultLocale(String lng) {
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
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.finish();
    }

}
