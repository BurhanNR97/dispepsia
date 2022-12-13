package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class EditGejala extends AppCompatActivity {
    GejalaDB db;
    EditText TxKode, TxNama, TxBobot;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gejala);

        Toolbar toolbar = findViewById(R.id.toolbareditgejala);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perbarui Data");

        db = new GejalaDB(this);

        id = getIntent().getLongExtra(GejalaDB.row_id, 0);

        TxKode = (EditText)findViewById(R.id.txKode_Editgejala);
        TxNama = (EditText)findViewById(R.id.txNama_Editgejala);
        TxBobot = (EditText)findViewById(R.id.txBobot_editgejala);

        getData();
    }

    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(GejalaDB.row_kdGejala));
            String nama = cursor.getString(cursor.getColumnIndex(GejalaDB.row_nmGejala));
            String bobot = cursor.getString(cursor.getColumnIndex(GejalaDB.row_bobot));

            TxKode.setText(kode);
            TxNama.setText(nama);
            TxBobot.setText(bobot);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_gejala, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit_gejala:
                String kode = TxKode.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();
                String bobot = TxBobot.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(GejalaDB.row_kdGejala, kode);
                values.put(GejalaDB.row_nmGejala, nama);
                values.put(GejalaDB.row_bobot, bobot);

                if (bobot.equals("") && nama.equals("")){
                    Toast.makeText(EditGejala.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else {
                    db.updateData(values, id);
                    Toast.makeText(EditGejala.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit_gejala:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditGejala.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditGejala.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}