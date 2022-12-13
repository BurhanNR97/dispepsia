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

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class TambahGejala extends AppCompatActivity {

    GejalaDB db;
    EditText txKode, txNama, txBobot;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_gejala);

        Toolbar toolbar = findViewById(R.id.toolbartambahgejala);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Gejala");

        db = new GejalaDB(this);

        id = getIntent().getLongExtra(GejalaDB.row_id, 0);

        txKode = (EditText)findViewById(R.id.txKode_Addgejala);
        txNama = (EditText)findViewById(R.id.txNama_Addgejala);
        txBobot = (EditText)findViewById(R.id.txBobot_Addgejala);

        Cursor kode = db.kode();
        if (kode.moveToNext()) {
            String a = kode.getString(1).substring(1);
            int b = Integer.parseInt(a);
            int c = b + 1;
            String d = String.valueOf(c);
            if (d.length() == 1) {
                txKode.setText("G0"+c);
            } else
            if (d.length() == 2) {
                txKode.setText("G"+c);
            }
        } else
        {
            txKode.setText("G01");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_gejala, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.save_add_gejala:
                String kode = txKode.getText().toString().trim();
                String nama = txNama.getText().toString().trim();
                String bobot = txBobot.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(GejalaDB.row_kdGejala, kode);
                values.put(GejalaDB.row_nmGejala, nama);
                values.put(GejalaDB.row_bobot, bobot);

                //Create Condition if Title and Detail is empty
                if (bobot.equals("") && nama.equals("")){
                    Toast.makeText(TambahGejala.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    db.insertData(values);
                    Toast.makeText(TambahGejala.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}