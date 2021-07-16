package uz.itjunior.yaseen.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.Locale;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.model.LanguageManager;

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome_screen);

        SharedPreferences preferences = getSharedPreferences("Requests", Context.MODE_PRIVATE);

        String lng = preferences.getString("language", null);
        if (lng == null && Locale.getDefault().getLanguage().equals("en"))
            LanguageManager.setDefaultLocale(this, "en");
        else LanguageManager.setDefaultLocale(this, "uz");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeScreenActivity.this, MainActivity.class));
                finish();
            }
        }, 500);
    }
}