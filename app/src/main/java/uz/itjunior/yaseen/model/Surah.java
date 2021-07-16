package uz.itjunior.yaseen.model;

public class Surah {
    private int ayat;
    private String arabic;
    private String transcription;
    private String meaning;

    public Surah(int ayat, String arabic, String transcription, String meaning) {
        this.ayat = ayat;
        this.arabic = arabic;
        this.transcription = transcription;
        this.meaning = meaning;
    }

    public int getAyat() {
        return ayat;
    }

    public String getArabic() {
        return arabic;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getMeaning() {
        return meaning;
    }
}
