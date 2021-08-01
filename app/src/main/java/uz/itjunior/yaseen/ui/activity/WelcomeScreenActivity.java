package uz.itjunior.yaseen.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.manager.LanguageManager;

public class WelcomeScreenActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeScreenActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public LanguageManager manager;
    private AlertDialog.Builder builder;

    private TextView tvLatin, tvCyrillic;
    private FirebaseAnalytics analytics;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome_screen);

        analytics = FirebaseAnalytics.getInstance(this);

        preferences = getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
        manager = new LanguageManager(this);

        String lng = preferences.getString("language", null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lng != null) {
                    Log.d(TAG, "run: method was worked");
                    manager.getDefaultLanguage();
                    goToMain();
                }
                else setLanguage();
            }
        }, 500);

        Log.d(TAG, "onCreate: bottom of Handler");
    }

    private void goToMain() {
        startActivity(new Intent(WelcomeScreenActivity.this, MainActivity.class));
        finish();
    }

    private void setLanguage() {
        View view = LayoutInflater.from(this).inflate(
                R.layout.select_language_layout,
                findViewById(R.id.select_language_container));
        tvCyrillic = view.findViewById(R.id.select_language_cyrillic_tv);
        tvLatin = view.findViewById(R.id.select_language_latin_tv);

        builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("NonConstantResourceId")
    public void setLanguage(View view) {
        switch (view.getId()) {
            case R.id.select_language_cyrillic_tv:
                tvCyrillic.setBackgroundColor(Color.GREEN);
                manager.setDefaultLocale("uz");
                builder.setCancelable(true);
                goToMain();
                break;

            case R.id.select_language_latin_tv:
                tvLatin.setBackgroundColor(Color.GREEN);
                manager.setDefaultLocale("en");
                builder.setCancelable(true);
                goToMain();
                break;
        }
    }
}