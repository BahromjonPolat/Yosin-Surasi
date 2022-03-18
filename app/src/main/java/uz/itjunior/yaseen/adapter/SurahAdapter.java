package uz.itjunior.yaseen.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.model.Surah;

import static uz.itjunior.yaseen.ui.activity.MainActivity.player;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahHolder> {

    private static final String TAG = "SurahAdapter";
    private final SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final Context context;
    private final List<Surah> surahList;

    private final FirebaseAnalytics analytics;
    private final Bundle bundle;

    public SurahAdapter(Context context, List<Surah> surahList) {
        this.context = context;
        this.surahList = surahList;
        preferences = context.getSharedPreferences("Requests", Context.MODE_PRIVATE);
        analytics = FirebaseAnalytics.getInstance(context);
        bundle = new Bundle();
    }

    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_surah, parent, false);
        return new SurahHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SurahAdapter.SurahHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvArabic.setTextSize(preferences.getInt("ArabicTextSize", 22));
        holder.tvMeaning.setTextSize(preferences.getInt("MeaningTextSize", 15));
        holder.tvTranscription.setTextSize(preferences.getInt("TrTextSize", 15));

        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
        String ayah = "\uFD3F" + nf.format(position) + "\uFD3E";

        Surah surah = surahList.get(position);
        holder.tvVerse.setText(String.valueOf(position));
        holder.tvVerseArabic.setText(nf.format(position));


        if (preferences.getBoolean("isArabicChecked", true)) {
            holder.tvArabic.setVisibility(View.VISIBLE);
            holder.tvArabic.setText(surah.getArabic() + ayah + " ");
        } else {
            holder.tvArabic.setVisibility(View.GONE);
        }

        if (preferences.getBoolean("isMeaningChecked", true)) {
            holder.tvMeaning.setVisibility(View.VISIBLE);
            holder.tvMeaning.setText(surah.getMeaning());
        } else {
            holder.tvMeaning.setVisibility(View.GONE);
        }

        if (preferences.getBoolean("isTrChecked", true)) {
            holder.tvTranscription.setVisibility(View.VISIBLE);
            holder.tvTranscription.setText(surah.getTranscription());
        } else {
            holder.tvTranscription.setVisibility(View.GONE);
        }

        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", getFormattedAyat(position));
                manager.setPrimaryClip(clipData);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();

            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, getFormattedAyat(position));
                context.startActivity(Intent.createChooser(intent, "Ulashing"));

            }
        });

        holder.imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (player != null) {
                    player.stop();
                    player.release();
                    Log.d(TAG, "onClick: " + surah.getAudio());
                }
                player = MediaPlayer.create(context, surah.getAudio());
                player.start();

                bundle.putString("oyat", surah.getAudio() + "-oyat");
                analytics.logEvent("play_audio", bundle);

            }
        });

    }

    private String getFormattedAyat(int verse) {
        @SuppressLint("DefaultLocale") String formattedAyat = String.format("%s\n\n%s\n\n(%s, %d-%s)",
                        surahList.get(verse).getArabic(),
                        surahList.get(verse).getMeaning(),
                        context.getResources().getString(R.string.app_name)
                        , verse,
                        context.getResources().getString(R.string.ayat));
        return formattedAyat;
    }


    @Override
    public int getItemCount() {
        return surahList.size();
    }


    static class SurahHolder extends RecyclerView.ViewHolder {

        private final TextView tvArabic;
        private final TextView tvTranscription;
        private final TextView tvMeaning;
        private final TextView tvVerse;
        private final TextView tvVerseArabic;
        private final ImageView imgPlay;
        private final ImageView imgCopy;
        private final ImageView imgShare;

        public SurahHolder(@NonNull View itemView) {
            super(itemView);

            tvArabic = itemView.findViewById(R.id.item_surah_arabic_tv);
            tvMeaning = itemView.findViewById(R.id.item_surah_meaning_tv);
            tvTranscription = itemView.findViewById(R.id.item_surah_transcription_tv);
            tvVerse = itemView.findViewById(R.id.item_surah_verse_tv);
            tvVerseArabic = itemView.findViewById(R.id.item_surah_verse_arabic_tv);
            imgCopy = itemView.findViewById(R.id.item_surah_copy_img);
            imgPlay = itemView.findViewById(R.id.item_surah_play_img);
            imgShare = itemView.findViewById(R.id.item_surah_share_img);
        }
    }
}
