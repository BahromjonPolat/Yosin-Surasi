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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.manager.LanguageManager;

public class SettingsActivity extends AppCompatActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "SettingsActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private LanguageManager manager;


    // Views in activity
    private TextView tvArabic, tvTranscription, tvMeaning;
    private FloatingActionButton fb;

    // Views in Bottom sheet dialog layout
    private CheckBox chbArabic, chbTranscription, chbMeaning;
    private SeekBar sbArabic, sbTranscription, sbMeaning;
    private TextView tvCyrillic, tvLatin;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTitle(getString(R.string.settings));
        setContentView(R.layout.activity_settings);
        findView();

        preferences = getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
        manager = new LanguageManager(this);
        setSettings();

        Log.d(TAG, "onCreate: " + R.font.droid_naksh_regular);
    }


    private void findSettingView(View view) {
        tvCyrillic = view.findViewById(R.id.settings_cyrillic_tv);
        tvLatin = view.findViewById(R.id.settings_latin_tv);
        chbArabic = view.findViewById(R.id.settings_arabic_chb);
        chbTranscription = view.findViewById(R.id.settings_transcription_text_chb);
        chbMeaning = view.findViewById(R.id.settings_meaning_text_chb);
        sbArabic = view.findViewById(R.id.settings_arabic_sb);
        sbTranscription = view.findViewById(R.id.settings_transcription_text_sb);
        sbMeaning = view.findViewById(R.id.settings_meaning_sb);
    }

    private void findView() {
        tvArabic = findViewById(R.id.item_surah_arabic_tv);
        tvMeaning = findViewById(R.id.item_surah_meaning_tv);
        tvTranscription = findViewById(R.id.item_surah_transcription_tv);
        fb = findViewById(R.id.settings_show_bottom_sheet_fb);
    }

    private void setSettings() {
        fb.setColorFilter(Color.WHITE);
        tvArabic.setTextSize(preferences.getInt("ArabicTextSize", 22));
        tvMeaning.setTextSize(preferences.getInt("MeaningTextSize", 18));
        tvTranscription.setTextSize(preferences.getInt("TrTextSize", 18));

        if (preferences.getBoolean("isArabicChecked", true))
            tvArabic.setVisibility(View.VISIBLE);
        else tvArabic.setVisibility(View.GONE);

        if (preferences.getBoolean("isMeaningChecked", true))
            tvMeaning.setVisibility(View.VISIBLE);
        else tvMeaning.setVisibility(View.GONE);

        if (preferences.getBoolean("isTrChecked", true))
            tvTranscription.setVisibility(View.VISIBLE);
        else tvTranscription.setVisibility(View.GONE);
    }

    public void showSettings(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.bottom_sheet_dialog_settings_layout,
                findViewById(R.id.settings_bottom_sheet_container));
        findSettingView(view);

        String lng = preferences.getString("language", "uz");
        if (lng.equals("uz")) setTextSettings(tvCyrillic, tvLatin);
        else setTextSettings(tvLatin, tvCyrillic);

        setViewSettings(view);
        dialog.setContentView(view);
        dialog.show();

    }

    private void setViewSettings(View view) {

        boolean isArabicChecked = preferences.getBoolean("isArabicChecked", true);
        boolean isTrChecked = preferences.getBoolean("isTrChecked", true);
        boolean isMeaningChecked = preferences.getBoolean("isMeaningChecked", true);

        int arabicTextSize = preferences.getInt("ArabicTextSize", 22);
        int meaningTextSize = preferences.getInt("MeaningTextSize", 16);
        int trTextSize = preferences.getInt("TrTextSize", 16);

        chbArabic.setOnCheckedChangeListener(this);
        chbMeaning.setOnCheckedChangeListener(this);
        chbTranscription.setOnCheckedChangeListener(this);

        sbArabic.setOnSeekBarChangeListener(this);
        sbMeaning.setOnSeekBarChangeListener(this);
        sbTranscription.setOnSeekBarChangeListener(this);


        chbArabic.setChecked(isArabicChecked);
        chbMeaning.setChecked(isMeaningChecked);
        chbTranscription.setChecked(isTrChecked);

        sbArabic.setEnabled(isArabicChecked);
        sbTranscription.setEnabled(isTrChecked);
        sbMeaning.setEnabled(isMeaningChecked);

        sbArabic.setProgress(arabicTextSize);
        sbTranscription.setProgress(trTextSize);
        sbMeaning.setProgress(meaningTextSize);
    }


    private void setTextSize(SeekBar seekBar, TextView t, String key) {
        t.setTextSize(seekBar.getProgress());
        editor.putInt(key, seekBar.getProgress());
        editor.apply();
    }

    public void setCyrillic(View view) {
        setTextSettings(tvCyrillic, tvLatin);
        manager.setDefaultLocale("uz");
    }

    public void setLatin(View view) {
        setTextSettings(tvLatin, tvCyrillic);
        manager.setDefaultLocale("en");
    }

    private void setTextSettings(TextView t1, TextView t2) {
        t1.setBackgroundColor(Color.rgb(11, 135, 11));
        t1.setTextColor(Color.WHITE);
        t2.setBackgroundColor(Color.WHITE);
        t2.setTextColor(Color.BLACK);
    }

    @Override
    public void onClick(View v) {

    }

    // CheckBox | CompoundButton.OnCheckedChangeListener
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.settings_arabic_chb:

                if (chbArabic.isChecked()) tvArabic.setVisibility(View.VISIBLE);
                else tvArabic.setVisibility(View.GONE);

                sbArabic.setEnabled(chbArabic.isChecked());
                editor.putBoolean("isArabicChecked", chbArabic.isChecked());
                editor.apply();
                break;

            case R.id.settings_meaning_text_chb:

                if (chbMeaning.isChecked()) tvMeaning.setVisibility(View.VISIBLE);
                else tvMeaning.setVisibility(View.GONE);

                sbMeaning.setEnabled(chbMeaning.isChecked());
                editor.putBoolean("isMeaningChecked", chbMeaning.isChecked());
                editor.apply();
                break;

            case R.id.settings_transcription_text_chb:

                if (chbTranscription.isChecked()) tvTranscription.setVisibility(View.VISIBLE);
                else tvTranscription.setVisibility(View.GONE);

                sbTranscription.setEnabled(chbTranscription.isChecked());
                editor.putBoolean("isTrChecked", chbTranscription.isChecked());
                editor.apply();
                break;
        }
    }

    // SeekBar.OnSeekBarChangeListener
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.settings_arabic_sb:
                setTextSize(sbArabic, tvArabic, "ArabicTextSize");
                break;

            case R.id.settings_meaning_sb:
                setTextSize(sbMeaning, tvMeaning, "MeaningTextSize");
                break;

            case R.id.settings_transcription_text_sb:
                setTextSize(sbTranscription, tvTranscription, "TrTextSize");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}