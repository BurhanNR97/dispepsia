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

public class BerandaAdmin extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textView;
    CardView gejala, solusi, laporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_admin);

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String nama = sharedPreferences.getString("email",null);
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
                        startActivity(new Intent(BerandaAdmin.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }

    public void akun(View view){
        startActivity(new Intent(getApplicationContext(), AkunPengguna.class));
    }

    public void laporan(View view){
        startActivity(new Intent(getApplicationContext(), LaporanDiagnosa.class));
    }
}