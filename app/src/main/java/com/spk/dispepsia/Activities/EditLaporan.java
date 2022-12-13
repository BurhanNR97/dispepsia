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
import android.widget.TextView;
import android.widget.Toast;

import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.R;

public class EditLaporan extends AppCompatActivity {
    DiagnosaDB db;
    TextView TxKode, TxNama, TxNIK, TxHasil ;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_laporan);
        Toolbar toolbar = findViewById(R.id.toolbareditlaporan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hapus Data");

        db = new DiagnosaDB(this);

        id = getIntent().getLongExtra(DiagnosaDB.row_id, 0);

        TxKode = (TextView) findViewById(R.id.listeditkodelaporan);
        TxNama = (TextView) findViewById(R.id.listeditnamalaporan);
        TxNIK = (TextView)findViewById(R.id.listedittanggallaporan);
        TxHasil = (TextView)findViewById(R.id.listedithasillaporan);

        getData();
    }

    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_kdDiagnosa));
            String nik = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_nik_pasien));
            String nama = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_nama_pasien));
            String hasil = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_hasil_diagnosa));

            TxKode.setText(kode);
            TxNama.setText(nama);
            TxNIK.setText(nik);
            TxHasil.setText(hasil);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_laporan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_edit_laporan:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditLaporan.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditLaporan.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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