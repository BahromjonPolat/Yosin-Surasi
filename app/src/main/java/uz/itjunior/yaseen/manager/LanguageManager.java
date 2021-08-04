package uz.itjunior.yaseen.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

import uz.itjunior.yaseen.ui.activity.MainActivity;

public class LanguageManager {

    private static final String TAG = "LanguageManager";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Activity activity;
    @SuppressLint("CommitPrefEdits")
    public LanguageManager(Activity activity) {
        this.activity = activity;

        preferences = activity.getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void getDefaultLanguage() {
        String lng = preferences.getString("language", "uz");
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;

        activity.getBaseContext().getResources().updateConfiguration(
                configuration, activity.getBaseContext()
                        .getResources()
                        .getDisplayMetrics());

    }

    public void setDefaultLocale(String lng) {
        Locale locale = new Locale(lng);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;

        activity.getBaseContext().getResources().updateConfiguration(
                configuration, activity.getBaseContext()
                                .getResources()
                                .getDisplayMetrics());

        editor.putString("language", lng);
        editor.apply();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(new Intent(intent));
        activity.finish();
    }

}
