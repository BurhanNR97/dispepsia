package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.spk.dispepsia.R;

public class Tentang extends AppCompatActivity {

    private int countFragment;
    TextView tentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        Toolbar toolbar = findViewById(R.id.toolbartentang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tentang");

        tentang = findViewById(R.id.textAbout);
        tentang.setText("Dispepsia berasal dari Bahasa Yunani yaitu (Dys-) dan (Pepse) yang secara harfiah dapat diterjemahkan sebagai "+
                "“pencernaan yang buruk” (bad digestion). Dispepsia adalah gejala dan bukan diagnosis. Hal ini dapat didefinisikan secara "+
                "luas sebagai rasa sakit atau ketidaknyamanan yang berpusat di perut bagian atas. Berpusat mengacu pada gejala utama berada di "+
                "dalam atau sekitar garis tengah dan bukan terletak di kuadran atas kiri atau kanan. Ketidaknyamanan mengacu pada perasaan tidak "+
                "menyenangkan yang singkat dan menyakitkan, termasuk rasa penuh di perut bagian atas, cepat kenyang, kembung, mual, dan muntah."+
                " Dispepsia juga dikaitkan dengan berbagai faktor risiko pribadi dan lingkungan seperti alkohol, tembakau, dan penggunaan obat-obatan "+
                "anti inflamasi non-steroid dan dapat memberikan dampak negatif yang signifikan terhadap kualitas hidup.\n\n"+
                "1.\tDispepsia Tipe Ulkus apabila keluhan yang dominan adalah nyeri ulu hati.\n" +
                "2.\tDispepsia Tipe Dismotilitas apabila keluhan yang dominan adalah perut kembung, mual dan cepat kenyang.\n" +
                "3.\tDispepsia Tipe Non-Spesifik apabila keluhan tidak jelas untuk dikelompokkan pada salah satu jenis di atas.\n");
    }

    public int getCountFragment() {
        return countFragment;
    }

    @Override
    public void onBackPressed() {
        int count = getCountFragment();

        if(count == 0) {
            getFragmentManager().popBackStack();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}