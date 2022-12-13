package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.SolusiDB;
import com.spk.dispepsia.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TambahSolusi extends AppCompatActivity {

    SolusiDB db;
    EditText txKode, txNama;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_solusi);

        Toolbar toolbar = findViewById(R.id.toolbartambahsolusi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Data");

        db = new SolusiDB(this);

        id = getIntent().getLongExtra(SolusiDB.row_id, 0);

        txKode = (EditText)findViewById(R.id.txKode_Addsolusi);
        txNama = (EditText)findViewById(R.id.txNama_Addsolusi);

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            String d = String.valueOf(c);
            if (d.length() == 1) {
                txKode.setText("S0"+c);
            } else
            if (d.length() == 2) {
                txKode.setText("S"+c);
            }
        } else
        {
            txKode.setText("S01");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_solusi, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_solusi:
                String kode = txKode.getText().toString().trim();
                String nama = txNama.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(SolusiDB.row_kdSolusi, kode);
                values.put(SolusiDB.row_nmSolusi, nama);

                //Create Condition if Title and Detail is empty
                if (nama.equals("")){
                    Toast.makeText(TambahSolusi.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    db.insertData(values);
                    Toast.makeText(TambahSolusi.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}