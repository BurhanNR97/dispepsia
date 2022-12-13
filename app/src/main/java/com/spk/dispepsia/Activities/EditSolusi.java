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
import com.spk.dispepsia.Database.SolusiDB;
import com.spk.dispepsia.R;

public class EditSolusi extends AppCompatActivity {

    SolusiDB db;
    EditText TxKode, TxNama;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_solusi);

        Toolbar toolbar = findViewById(R.id.toolbareditsolusi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perbarui Data");

        db = new SolusiDB(this);

        id = getIntent().getLongExtra(SolusiDB.row_id, 0);

        TxKode = (EditText)findViewById(R.id.txKode_Editsolusi);
        TxNama = (EditText)findViewById(R.id.txNama_Editsolusi);

        getData();
    }

    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(SolusiDB.row_kdSolusi));
            String nama = cursor.getString(cursor.getColumnIndex(SolusiDB.row_nmSolusi));

            TxKode.setText(kode);
            TxNama.setText(nama);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_solusi, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit_solusi:
                String kode = TxKode.getText().toString().trim();
                String nama = TxNama.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(SolusiDB.row_kdSolusi, kode);
                values.put(SolusiDB.row_nmSolusi, nama);

                if (nama.equals("")){
                    Toast.makeText(EditSolusi.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else {
                    db.updateData(values, id);
                    Toast.makeText(EditSolusi.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit_solusi:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditSolusi.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditSolusi.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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