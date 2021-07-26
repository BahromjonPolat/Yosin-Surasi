package uz.itjunior.yaseen.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import uz.itjunior.yaseen.R;

public class AboutSurahActivity extends AppCompatActivity {
    private static final String TAG = "AboutSurahActivity";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_about_surah);

        textView = findViewById(R.id.about_surah);
        textView.setText(Html.fromHtml(getText()));

    }

    String getText() {
        return "<p>Анас розияллоҳу анҳудан ривоят қилинади: Набий соллаллоҳу алайҳи васаллам: “Ҳар бир нарсанинг қалби бор. Қуръоннинг қалби “Ёсин”дир. Ким “Ёсин”ни қироат қилса, Аллоҳ унга бу қироати учун Қуръонни ўн марта қироат қилган (савоби)ни ёзади” дедилар.</p>" +
                "<br /><p>Ҳар нарсанинг қалби унинг асосий аъзоларидан бири бўлади. Ёсин сураси ҳам Қуръоннинг қалби бўлиши ила ана ўша мартабага эришган. Сурада мўъжаз калималар билан Аллоҳ таолонинг биру борлиги, ҳар бир нарсага қодирлиги, Пайғамбар ва ваҳийнинг ҳақлиги, кишилар Пайғамбар етказган ваҳийни ўзларига дастур қилиб яшамоқлари лозимлиги, қайта тирилиш каби масалалар баён этилади.</p>" +
                "<br /><p>Булар ҳар бир кишининг қалбида туриши лозим бўлган ва ҳар бир киши эртаю кеч эслаб туриши зарур бўлган масалалардир.<br /><br /><br />Тасаввуф йўлини тутган зотлар ҳар куни бомдод намозидан кейин бу сураи каримани ўқишни ўзларига кундалик вазифа қилиб олганлар.</p>" +
                "<br /><p>Абу Ҳурайра розияллоҳу анҳудан ривоят қилинади: “Набий соллаллоҳу алайҳи васаллам: “Ким “Ёсин”ни кечаси Аллоҳнинг розилиги талабида ўқиса, мағфират қилинади”, дедилар.</p>" +
                "<br /><p>Ёсин сурасининг фазли ҳақида кўпгина ҳадислар ривоят қилинган. Уларнинг аксариятида Ёсин сураси Қуръоннинг қалби экани таъкидланган. Шунинг учун “Қуръон қалби” сурага иккинчи ном бўлиб қолган.</p>" +
                "<br /><p>Имом Баззор Абдуллоҳ ибн Аббос розияллоҳу анҳудан ривоят қилган ҳадисда Расулуллоҳ соллаллоҳу алайҳи васаллам: “Ҳар бир нарсанинг қалби бор. Қуръоннинг қалби “Ёсин”дир. Мен уни умматимдан ҳар бирининг қалбида бўлишини истардим”, деганлар.</p>" +
                "<br /><p><strong>Абдуллоҳ ПАРПИЕВ</strong><strong>,</strong></p>" +
                "<br /><p>Халқаро алоқалар бўлими ходими</p>" +
                "        <br />" +
                "        <p>Manba: <a href='https://muslim.uz/'>Muslim.uz</a></p>" +
                "</div></string>";
    }
}