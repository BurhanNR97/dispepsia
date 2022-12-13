package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.spk.dispepsia.Database.BiodataDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

public class UpdateProfil extends AppCompatActivity {
    TextView nmr, nama, tgllahir, alamat, hp, email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        String nik = getIntent().getStringExtra("nik");
        nmr = findViewById(R.id.txtNIK);
        nmr.setText(nik);
        nama = findViewById(R.id.txtNama);
        tgllahir = findViewById(R.id.txtTglLahir);
        alamat = findViewById(R.id.txtAlamat);
        hp = findViewById(R.id.txtTelp);

        BiodataDB biodataDB = new BiodataDB(this);
        UserDB db = new UserDB(this);
        Cursor cur = biodataDB.tampilProfile1User(nik);

        if (cur != null && cur.moveToFirst()) {
            nama.setText(cur.getString(1));
            tgllahir.setText(cur.getString(2));
            alamat.setText(cur.getString(cur.getColumnIndex("alamat")));
            hp.setText(cur.getString(4));
            email.setText(cur.getString(5));

            Cursor cursor = db.checkLevel(cur.getString(5));
            if (cursor != null && cursor.moveToFirst()) {
                pass.setText(cursor.getString(cursor.getColumnIndex("password")));
            }
        }
    }
}