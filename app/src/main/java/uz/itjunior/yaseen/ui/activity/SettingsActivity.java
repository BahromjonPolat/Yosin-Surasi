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
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.model.LanguageManager;

public class SettingsActivity extends AppCompatActivity
        implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener {

    private static final String TAG = "SettingsActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private CheckBox chbArabic, chbTranscription, chbMeaning;
    private SeekBar sbArabic, sbTranscription, sbMeaning;
    private TextView tvArabic, tvTranscription, tvMeaning, tvCyrillic, tvLatin;

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

    public void showSettings(View v) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(
                R.layout.bottom_sheet_dialog_settings_layout,
                findViewById(R.id.settings_bottom_sheet_container));
        findSettingView(view);

        sbArabic.setOnSeekBarChangeListener(this);
        sbMeaning.setOnSeekBarChangeListener(this);
        sbTranscription.setOnSeekBarChangeListener(this);

        dialog.setContentView(view);
        dialog.show();

    }

    private void setTextSize(SeekBar seekBar,TextView t, String key) {
        t.setTextSize(seekBar.getProgress());
        editor.putInt(key, seekBar.getProgress());
        editor.apply();
    }

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