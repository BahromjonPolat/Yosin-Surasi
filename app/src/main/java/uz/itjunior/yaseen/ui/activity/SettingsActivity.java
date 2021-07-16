package uz.itjunior.yaseen.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.model.LanguageManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "SettingsActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private CheckBox chbArabic, chbTranscription, chbMeaning;
    private SeekBar sbArabic, sbTranscription, sbMeaning;
    private TextView tvCyrillic, tvLatin;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTitle("Sozlamalar");
        setContentView(R.layout.activity_settings);
        findView();

        preferences = getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
        setProgress();
    }

    private void findView() {
        tvCyrillic = findViewById(R.id.settings_cyrillic_tv);
        tvLatin = findViewById(R.id.settings_latin_tv);
        chbArabic = findViewById(R.id.settings_arabic_chb);
        chbTranscription = findViewById(R.id.settings_transcription_text_chb);
        chbMeaning = findViewById(R.id.settings_meaning_text_chb);
        sbArabic = findViewById(R.id.settings_arabic_sb);
        sbTranscription = findViewById(R.id.settings_transcription_text_sb);
        sbMeaning = findViewById(R.id.settings_meaning_sb);


        chbMeaning.setOnCheckedChangeListener(this);
        chbArabic.setOnCheckedChangeListener(this);
    }

    private void setProgress() {
        sbTranscription.setEnabled(chbTranscription.isChecked());

        int arabicTS = preferences.getInt("arabicTextSize", 24);
        int trTS = preferences.getInt("trTextSize", 18);
        int meaningTS = preferences.getInt("meaningTextSize", 18);

        sbArabic.setProgress(arabicTS);
        sbMeaning.setProgress(meaningTS);
        sbTranscription.setProgress(trTS);

        sbArabic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                editor.putInt("arabicTextSize", progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbMeaning.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editor.putInt("meaningTextSize", progress);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setCyrillic(View view) {
        tvCyrillic.setBackgroundColor(Color.rgb(11, 135, 11));
        tvCyrillic.setTextColor(Color.WHITE);
        tvLatin.setBackgroundColor(Color.WHITE);
        tvLatin.setTextColor(Color.BLACK);
        LanguageManager.setDefaultLocale(this, "uz");
    }

    public void setLatin(View view) {
        tvLatin.setBackgroundColor(Color.rgb(11, 135, 11));
        tvLatin.setTextColor(Color.WHITE);
        tvCyrillic.setBackgroundColor(Color.WHITE);
        tvCyrillic.setTextColor(Color.BLACK);
        LanguageManager.setDefaultLocale(this, "en");
    }

    @Override
    public void onClick(View v) {

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.settings_arabic_chb:
                sbArabic.setEnabled(chbArabic.isChecked());
                editor.putBoolean("isArabicChecked", chbArabic.isChecked());
                editor.apply();
                break;
            case R.id.settings_meaning_text_chb:
                sbMeaning.setEnabled(chbMeaning.isChecked());
                editor.putBoolean("isMeaningChecked", chbMeaning.isChecked());
                editor.apply();
                break;
        }
    }
}