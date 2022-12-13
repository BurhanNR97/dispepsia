package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spk.dispepsia.Database.AturanDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.PositionDB;
import com.spk.dispepsia.R;

import java.util.List;

public class TambahAturan extends AppCompatActivity {
    GejalaDB gejalaDB;
    JenisPenyakitDB jenisPenyakitDB;
    AturanDB aturanDB;
    PositionDB positionDB;
    Spinner sGejala, sPenyakit;
    TextView txKode, txNilai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_aturan);

        Toolbar toolbar = findViewById(R.id.toolbartambahaturan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Rules");

        sGejala = (Spinner) findViewById(R.id.Spin_kriteriaADD);
        sPenyakit = (Spinner) findViewById(R.id.Spin_alternatifADD);
        txKode = (TextView) findViewById(R.id.txKode_Addaturan);
        txNilai = (TextView) findViewById(R.id.txNilai_Addaturan);

        gejalaDB = new GejalaDB(this);
        jenisPenyakitDB = new JenisPenyakitDB(this);
        aturanDB = new AturanDB(this);
        positionDB = new PositionDB(this);

        loadSpinner();

        Cursor kode = aturanDB.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            String d = String.valueOf(c);
            if (d.length() == 1) {
                txKode.setText("R00"+c);
            } else
            if (d.length() == 2) {
                txKode.setText("R0"+c);
            } else
            if (d.length() == 3) {
                txKode.setText("R"+c);
            }
        } else
        {
            txKode.setText("R001");
        }
    }

    private void loadSpinner() {
        List<String> gejala = gejalaDB.ambilData();
        List<String> penyakit = jenisPenyakitDB.ambilData();

        // Creating adapter for spinner
        ArrayAdapter<String> dataGejala = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gejala);
        ArrayAdapter<String> dataPenyakit = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, penyakit);

        // Drop down layout style - list view with radio button
        dataGejala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataPenyakit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
       sGejala.setAdapter(dataGejala);
       sPenyakit.setAdapter(dataPenyakit);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_aturan, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_aturan:
                String kode = txKode.getText().toString().trim();
                String nilai = txNilai.getText().toString().trim();
                String alternatif = sPenyakit.getSelectedItem().toString().substring(0,2);
                String kriteria = sGejala.getSelectedItem().toString().substring(0,3);

                Boolean cek = aturanDB.cekData(alternatif, kriteria);

                ContentValues values = new ContentValues();
                ContentValues value = new ContentValues();
                values.put(AturanDB.row_kdRules, kode);
                values.put(AturanDB.row_alternatif, alternatif);
                values.put(AturanDB.row_kriteria, kriteria);
                values.put(AturanDB.row_nilai, nilai);

                if (nilai.equals("")){
                    Toast.makeText(TambahAturan.this, "Data tidak boleh kosong"+sGejala.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
                    txNilai.requestFocus();
                } else {
                    if (cek==true) {
                        Toast.makeText(TambahAturan.this, "Data sudah ada", Toast.LENGTH_SHORT).show();
                    } else {
                        value.put(PositionDB.row_kdRule, kode);
                        value.put(PositionDB.row_posAlt, sPenyakit.getSelectedItemPosition());
                        value.put(PositionDB.row_posKri, sGejala.getSelectedItemPosition());
                        aturanDB.insertData(values);
                        positionDB.insertData(value);
                        Toast.makeText(TambahAturan.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }
}