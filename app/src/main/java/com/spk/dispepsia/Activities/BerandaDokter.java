package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spk.dispepsia.R;

public class BerandaDokter extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;
    CardView gejala, solusi, laporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_dokter);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String nama = sharedPreferences.getString("email",null);

        gejala = (CardView)findViewById(R.id.berandagejala);
        solusi = (CardView)findViewById(R.id.berandasolusi);
        laporan = (CardView)findViewById(R.id.berandalaporandiagnosa);

        gejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Gejala.class));
            }
        });

        solusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Solusi.class));
            }
        });
    }

    public void keluar(View view) {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("Anda yakin ingin logout?")
                .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(BerandaDokter.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }

    public void aturan(View view){
        startActivity(new Intent(getApplicationContext(), Aturan.class));
    }

    public void jenis(View view){
        startActivity(new Intent(getApplicationContext(), JenisPenyakit.class));
    }
}
