package com.spk.dispepsia.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.spk.dispepsia.Database.BiodataDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

import java.util.Calendar;

public class ProfileUser extends AppCompatActivity {

    EditText nmr, nama, tgllahir, alamat, hp, email, pass;
    Button btnUpdate, btnSimpan;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

       /* Toolbar ToolBarAtas2 = (Toolbar) findViewById(R.id.toolbar_satu);
        setActionBar(ToolBarAtas2);
        //setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setTitle("");
        setActionBar(ToolBarAtas2); */

        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        String nik = getIntent().getStringExtra("nik");
        nmr = findViewById(R.id.txtNIK);
        nmr.setText(nik);
        nama = findViewById(R.id.txtNama);
        tgllahir = findViewById(R.id.txtTglLahir);
        alamat = findViewById(R.id.txtAlamat);
        hp = findViewById(R.id.txtTelp);


            tgllahir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            ProfileUser.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, tahun, bulan, hari);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            });

            setListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String bulan = String.valueOf(month);
                    if (bulan.length() > 1) {
                        String date = dayOfMonth+"-"+month+"-"+year;
                        tgllahir.setText(date);
                    } else
                    {
                        String date = dayOfMonth+"-0"+month+"-"+year;
                        tgllahir.setText(date);
                    }
                }
            };

        BiodataDB biodataDB = new BiodataDB(this);
        UserDB db = new UserDB(this);
        Cursor cur = biodataDB.tampilProfile1User(nik);

        if (cur != null && cur.moveToFirst()) {
            nama.setText(cur.getString(1));
            tgllahir.setText(cur.getString(2));
            alamat.setText(cur.getString(3));
            hp.setText(cur.getString(4));
        }

        btnUpdate = (Button) findViewById(R.id.Btn_ubahdata);
        btnSimpan = (Button) findViewById(R.id.Btn_simpandata);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdate.setVisibility(View.GONE);
                btnSimpan.setVisibility(View.VISIBLE);
                nama.setEnabled(true);
                tgllahir.setEnabled(true);
                alamat.setEnabled(true);
                hp.setEnabled(true);
                //Toast.makeText(getApplicationContext(), "HDShdishdu", Toast.LENGTH_SHORT).show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = biodataDB.getWritableDatabase();
                db.execSQL("UPDATE biodata SET nama='"+
                        nama.getText().toString() +"', tgl_lahir='" +
                        tgllahir.getText().toString()+"', alamat='"+
                        alamat.getText().toString() +"', telp='" +
                        hp.getText().toString() +"', email='" +
                        nmr.getText().toString()+"'");

                Toast.makeText(getApplicationContext(), "Data Berhasil Diubah", Toast.LENGTH_LONG).show();
                btnUpdate.setVisibility(View.VISIBLE);
                btnSimpan.setVisibility(View.GONE);
                nama.setEnabled(false);
                tgllahir.setEnabled(false);
                alamat.setEnabled(false);
                hp.setEnabled(false);
                btnUpdate.requestFocus();
            }
        });
    }

}