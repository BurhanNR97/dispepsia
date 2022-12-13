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

public class EditHasilDiagnosa extends AppCompatActivity {
    DiagnosaDB db;
    TextView TxKode, TxTgl, TxHasil ;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hasil_diagnosa);
        Toolbar toolbar = findViewById(R.id.toolbaredithasildiagnosa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hapus Data");

        db = new DiagnosaDB(this);

        id = getIntent().getLongExtra(DiagnosaDB.row_id, 0);

        TxKode = (TextView) findViewById(R.id.listeditkodehasildiagnosa);
        TxTgl = (TextView) findViewById(R.id.listedittanggalhasildiagnosa);
        TxHasil = (TextView)findViewById(R.id.listedithasildiagnosa);

        getData();
    }

    private void getData() {
        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_kdDiagnosa));
            String tgl = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_tgl_diagnosa));
            String hasil = cursor.getString(cursor.getColumnIndex(DiagnosaDB.row_hasil_diagnosa));

            TxKode.setText(kode);
            TxTgl.setText(tgl);
            TxHasil.setText(hasil);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_hasil, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_edit_hasil:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditHasilDiagnosa.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditHasilDiagnosa.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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