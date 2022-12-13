package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.spk.dispepsia.Database.BiodataDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    EditText tgl, nik, nama, alamat, telp, e_mail, password;
    DatePickerDialog.OnDateSetListener setListener;
    private int countFragment;
    Button BtnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        tgl = findViewById(R.id.editTextTgl);
        Calendar calendar = Calendar.getInstance();
        final int tahun = calendar.get(Calendar.YEAR);
        final int bulan = calendar.get(Calendar.MONTH);
        final int hari = calendar.get(Calendar.DAY_OF_MONTH);

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, tahun, bulan, hari);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String bulan = String.valueOf(month);
                if (bulan.length() > 1) {
                    String date = dayOfMonth + "-" + month + "-" + year;
                    tgl.setText(date);
                } else {
                    String date = dayOfMonth + "-0" + month + "-" + year;
                    tgl.setText(date);
                }
            }
        };

        /*tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int t, int b, int h) {
                        b = b+1;
                        String date = h+"/"+b+"/"+t;
                        tgl.setText(date);
                    }
                },tahun, bulan, hari);
                datePickerDialog.show();
            }
        });*/

        nik = findViewById(R.id.editTextNIK);
        nama = findViewById(R.id.editTextNama);
        tgl = findViewById(R.id.editTextTgl);
        alamat = findViewById(R.id.editTextAlamat);
        telp = findViewById(R.id.editTextMobile);
        e_mail = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        UserDB userDB = new UserDB(this);
        BiodataDB biodataDB = new BiodataDB(this);
        BtnDaftar = findViewById(R.id.Btn_daftar);

        BtnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e_mail.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String noKTP = nik.getText().toString().trim();
                String nm_pasien = nama.getText().toString().trim();
                String tgl_lahir = tgl.getText().toString().trim();
                String alamat_pasien = alamat.getText().toString().trim();
                String noHP = telp.getText().toString().trim();


                Boolean ceknik = biodataDB.checkNIK(noKTP);
                Boolean cekemail = userDB.checkEmail(email);

                if (noKTP.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan No Identitas", Toast.LENGTH_SHORT).show();
                    nik.requestFocus();
                } else if (nm_pasien.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan Nama Anda", Toast.LENGTH_SHORT).show();
                    nama.requestFocus();
                } else if (tgl_lahir.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan Tanggal Lahir Anda", Toast.LENGTH_SHORT).show();
                    tgl.requestFocus();
                } else if (alamat_pasien.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan Alamat Anda", Toast.LENGTH_SHORT).show();
                    alamat.requestFocus();
                } else if (noHP.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan No. Telepon Anda", Toast.LENGTH_SHORT).show();
                    telp.requestFocus();
                } else if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    e_mail.requestFocus();
                } else {
                    if (ceknik == true) {
                        Toast.makeText(getApplicationContext(), "No. Identitas Sudah Digunakan", Toast.LENGTH_SHORT).show();
                        nik.requestFocus();
                        nik.selectAll();
                    } else {
                        if (cekemail == true) {
                            Toast.makeText(getApplicationContext(), "Email Sudah Digunakan", Toast.LENGTH_SHORT).show();
                            e_mail.requestFocus();
                            e_mail.selectAll();
                        } else {
                            ContentValues values = new ContentValues();
                            values.put("nik", noKTP);
                            values.put("nama", nm_pasien);
                            values.put("tgl_lahir", tgl_lahir);
                            values.put("telp", noHP);
                            values.put(BiodataDB.row_alamat, alamat_pasien);
                            values.put("email", email);
                            biodataDB.insertBiodata(values);

                            ContentValues value = new ContentValues();
                            value.put("email", email);
                            value.put("password", pass);
                            value.put("level", "user");
                            userDB.insertData(value);
                        }
                        Toast.makeText(getApplicationContext(), "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public void loginPage() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public int getCountFragment() {
        return countFragment;
    }

    @Override
    public void onBackPressed() {
        int count = getCountFragment();

        if (count == 0) {
            getFragmentManager().popBackStack();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}