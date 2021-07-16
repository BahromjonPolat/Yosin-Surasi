package uz.itjunior.yaseen.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import uz.itjunior.yaseen.R;

public class AboutSurahActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_surah);

        textView = findViewById(R.id.about_surah);


    }
}