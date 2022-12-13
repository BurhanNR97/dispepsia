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
import com.spk.dispepsia.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TambahJenisPenyakit extends AppCompatActivity {

    JenisPenyakitDB db;
    EditText txKode, txNama;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_jenis_penyakit);

        Toolbar toolbar = findViewById(R.id.toolbartambahjenis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Data");

        db = new JenisPenyakitDB(this);

        id = getIntent().getLongExtra(JenisPenyakitDB.row_id, 0);

        txKode = (EditText)findViewById(R.id.txKode_Addjenis);
        txNama = (EditText)findViewById(R.id.txNama_Addjenis);

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            txKode.setText("A"+c);
        } else
        {
            txKode.setText("A1");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_jenispenyakit, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_jenis:
                String kode = txKode.getText().toString().trim();
                String nama = txNama.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(JenisPenyakitDB.row_kdPenyakit, kode);
                values.put(JenisPenyakitDB.row_nmPenyakit, nama);

                //Create Condition if Title and Detail is empty
                if (kode.equals("") && nama.equals("")){
                    Toast.makeText(TambahJenisPenyakit.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    db.insertData(values);
                    Toast.makeText(TambahJenisPenyakit.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}