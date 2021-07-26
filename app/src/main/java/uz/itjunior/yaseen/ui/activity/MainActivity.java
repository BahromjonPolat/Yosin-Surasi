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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.zhanghai.android.fastscroll.FastScrollerBuilder;
import me.zhanghai.android.fastscroll.PopupTextProvider;
import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.adapter.SurahAdapter;
import uz.itjunior.yaseen.model.Surah;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private DatabaseReference reference;

    // TODO: 7/24/21 LanguageManager'dan foydalanib tilni sozlash!

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setTitle(R.string.app_name);
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
        rv.scrollToPosition(preferences.getInt("lastPosition", 0));

        FastScrollerBuilder scrollerBuilder = new FastScrollerBuilder(rv);
        scrollerBuilder.disableScrollbarAutoHide();
        scrollerBuilder.setPopupTextProvider(new PopupTextProvider() {
            @NonNull
            @Override
            public String getPopupText(int position) {
                editor.putInt("lastPosition", position);
                editor.apply();

                return String.valueOf(surahList().get(position).getAyat());
            }
        }).build();
    }

    private List<Surah> surahList() {
        List<Surah> surahList = new ArrayList<>();

        // Raw papkasining ichidagi barcha elementlarni olish uchun ishlatiladi.
        Field[] fields = R.raw.class.getDeclaredFields();

        String[] ayatList = getResources().getStringArray(R.array.ayat_list);
        String[] trCyrillic = getResources().getStringArray(R.array.transcription_cyrillic);
        String[] meanings_cyrillic = getResources().getStringArray(R.array.meaning_cyrillic);
        String[] meanings_latin = getResources().getStringArray(R.array.meaning_latin);
        String[] meaning;


        String lng = preferences.getString("language", "uz");

        if (lng.equals("uz")) meaning = meanings_cyrillic;
        else meaning = meanings_latin;

        int i = 0;
        for (String s : ayatList) {
            int resId = getResources().getIdentifier(fields[i].getName(), "raw", getPackageName());
            surahList.add(new Surah(i, s, trCyrillic[i], meaning[i], resId));
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

            case R.id.option_menu_about_surah:
                startActivity(new Intent(MainActivity.this, AboutSurahActivity.class));
                break;

            case R.id.option_menu_info:
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
                break;

            case R.id.option_menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}