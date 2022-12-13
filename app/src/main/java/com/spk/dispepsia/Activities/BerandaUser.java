package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.spk.dispepsia.R;

public class BerandaUser extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView nama_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_user);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.hijau));
        }

        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String nama = getIntent().getStringExtra("user");
        nama_user = findViewById(R.id.txt_nama_user);
        nama_user.setText(nama);

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
                        startActivity(new Intent(BerandaUser.this, MainActivity.class));
                        finish();
                    }
                })
                .show();
    }

    public void ProfilUSer(View view){
        Intent intent = new Intent(BerandaUser.this, ProfileUser.class);
        intent.putExtra("nik", getIntent().getStringExtra("nik"));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void diagnosa(View view){
        Intent intent = new Intent(BerandaUser.this, Diagnosa.class);
        intent.putExtra("nik", getIntent().getStringExtra("nik"));
        intent.putExtra("nama", getIntent().getStringExtra("user"));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void hasil(View view){
        Intent intent = new Intent(BerandaUser.this, HasilDiagnosa.class);
        intent.putExtra("nik", getIntent().getStringExtra("nik"));
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}