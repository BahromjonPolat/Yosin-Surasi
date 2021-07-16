package uz.itjunior.yaseen.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.adapter.SurahAdapter;
import uz.itjunior.yaseen.model.Surah;
import uz.itjunior.yaseen.ui.fragment.SettingsBottomSheetDialog;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        RecyclerView rv = findViewById(R.id.main_surah_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rv.setAdapter(new SurahAdapter(this, surahList()));
    }

    private List<Surah> surahList() {
        List<Surah> surahList = new ArrayList<>();

        String[] ayatList = getResources().getStringArray(R.array.ayat_list);
        String[] meanings_cyrillic = getResources().getStringArray(R.array.meaning_cyrillic);
        String[] meanings_latin = getResources().getStringArray(R.array.meaning_latin);
        String[] meaning;

        String lng = preferences.getString("language", "uz");

        if (lng.equals("uz")) meaning = meanings_cyrillic;
        else meaning = meanings_latin;

        int i = 0;
        for (String s : ayatList) {
            surahList.add(new Surah(i, s, "Transcription", meaning[i]));
            i++;
        }
        return surahList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option_menu_tajvid:
                SettingsBottomSheetDialog dialog = new SettingsBottomSheetDialog(this);
                dialog.show();
                break;

            case R.id.option_menu_about_surah:
                break;

            case R.id.option_menu_info:
                startActivity(new Intent(MainActivity.this, AboutSurahActivity.class));
                break;

            case R.id.option_menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}