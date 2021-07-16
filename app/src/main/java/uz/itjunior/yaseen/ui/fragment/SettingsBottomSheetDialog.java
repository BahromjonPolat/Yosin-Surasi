package uz.itjunior.yaseen.ui.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.databinding.ActivityMainBinding;

public class SettingsBottomSheetDialog extends BottomSheetDialog {
    private static final String TAG = "SettingsBottomSheetDial";

    private SeekBar sbArabicTextSize, sbTranscriptionSize, sbMeaningSize;
    private CheckBox chbArabic, chbTranscription, chbMeaning;
    
    public SettingsBottomSheetDialog(@NonNull Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_bottom_dialog_settings,
                findViewById(R.id.settings_bottom_sheet_container));

        findView(view);
        resizeText();
        setContentView(view);
    }

    private void findView(View view) {
        sbArabicTextSize = view.findViewById(R.id.settings_arabic_text_sb);
        sbTranscriptionSize = view.findViewById(R.id.settings_transcription_text_sb);
        sbMeaningSize = view.findViewById(R.id.settings_meaning_text_sb);
        chbArabic = view.findViewById(R.id.settings_arabic_text_check_box);
        chbMeaning = view.findViewById(R.id.settings_meaning_text_check_box);
        chbTranscription = view.findViewById(R.id.settings_transcription_text_check_box);
    }

    private void resizeText() {

        sbArabicTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getContext(), "progress: " + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: ");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");
            }
        });
    }
}
