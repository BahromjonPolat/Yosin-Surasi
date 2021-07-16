package uz.itjunior.yaseen.adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.itjunior.yaseen.R;
import uz.itjunior.yaseen.model.Surah;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahHolder> {

    private static final String TAG = "SurahAdapter";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private final Context context;
    private final List<Surah> surahList;

    public SurahAdapter(Context context, List<Surah> surahList) {
        this.context = context;
        this.surahList = surahList;
        preferences = context.getSharedPreferences("Requests", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @NonNull
    @Override
    public SurahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_surah, parent, false);
        return new SurahHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahAdapter.SurahHolder holder, int position) {
        holder.tvArabic.setTextSize(preferences.getInt("ArabicTextSize", 15));

        Log.d(TAG, "onBindViewHolder: was worked");

        Surah surah = surahList.get(position);
        holder.tvVerse.setText(String.valueOf(position + 1));
        holder.tvArabic.setText(surah.getArabic());
        holder.tvTranscription.setText(surah.getTranscription());
        holder.tvMeaning.setText(surah.getMeaning());

        holder.imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(surah.getArabic(), surah.getMeaning(), position);
            }
        });

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(android.content.Intent.ACTION_SEND);
                email.setType("application/octet-stream");
                context.startActivity(email);
            }
        });

    }

    private void copyToClipboard(String arabic, String meaning, int verse) {
        @SuppressLint("DefaultLocale") String clip =
                String.format("%s\n\n%s\n\n(Yosin surasi%d-oyat)", arabic, meaning, verse + 1);

        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", clip);
        manager.setPrimaryClip(clipData);

        Log.d(TAG, "copyToClipboard: method was worked");
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
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
        private final ImageView imgPlay;
        private final ImageView imgCopy;
        private final ImageView imgShare;

        public SurahHolder(@NonNull View itemView) {
            super(itemView);

            tvArabic = itemView.findViewById(R.id.item_surah_arabic_tv);
            tvMeaning = itemView.findViewById(R.id.item_surah_meaning_tv);
            tvTranscription = itemView.findViewById(R.id.item_surah_transcription_tv);
            tvVerse = itemView.findViewById(R.id.item_surah_verse_tv);
            imgCopy = itemView.findViewById(R.id.item_surah_copy_img);
            imgPlay = itemView.findViewById(R.id.item_surah_play_img);
            imgShare = itemView.findViewById(R.id.item_surah_share_img);

            Log.d(TAG, "SurahHolder: method was worked");
        }
    }
}
