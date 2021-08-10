package uz.itjunior.yaseen.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import uz.itjunior.yaseen.R;

public class ReadingModeActivity extends AppCompatActivity {

    private TextView tvReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reading_mode);

        tvReading = findViewById(R.id.reading_surah_tv);
        tvReading.setText(getSurah());
    }

    private StringBuilder getSurah() {
        StringBuilder builder = new StringBuilder();
        String[] surah = getResources().getStringArray(R.array.ayat_list);
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
        int i = 0;
        for (String s : surah) {
            if (i != 0) builder.append(s).append("\uFD3F").append(nf.format(i)).append("\uFD3E").append("  ");
            i++;
        }

        return builder;
    }
}