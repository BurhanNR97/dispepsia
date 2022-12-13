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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spk.dispepsia.Database.AturanDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.PositionDB;
import com.spk.dispepsia.R;

import java.util.List;

public class EditAturan extends AppCompatActivity {
    GejalaDB gejalaDB;
    JenisPenyakitDB jenisPenyakitDB;
    AturanDB aturanDB;
    PositionDB positionDB;
    Spinner sGejala, sPenyakit;
    TextView txKode, txNilai;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aturan);

        Toolbar toolbar = findViewById(R.id.toolbareditaturan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perbarui Data");

        sGejala = (Spinner) findViewById(R.id.Spin_kriteriaEdit);
        sPenyakit = (Spinner) findViewById(R.id.Spin_alternatifEdit);
        txKode = (TextView) findViewById(R.id.txKode_Editaturan);
        txNilai = (TextView) findViewById(R.id.txNilai_Editaturan);

        gejalaDB = new GejalaDB(this);
        jenisPenyakitDB = new JenisPenyakitDB(this);
        aturanDB = new AturanDB(this);
        positionDB = new PositionDB(this);

        id = getIntent().getLongExtra(AturanDB.row_id, 0);

        getData();
        loadSpinner();
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
        Cursor cur = positionDB.oneData(txKode.getText().toString().trim());
        if(cur.moveToFirst()){
            sPenyakit.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posAlt))));
            sGejala.setSelection(Integer.parseInt(cur.getString(cur.getColumnIndex(PositionDB.row_posKri))));
        }
    }

    private void getData() {
        Cursor cursor = aturanDB.oneData(id);
        if(cursor.moveToFirst()){
            String kode = cursor.getString(cursor.getColumnIndex(AturanDB.row_kdRules));
            String nilai = cursor.getString(cursor.getColumnIndex(AturanDB.row_nilai));
            txKode.setText(kode);
            txNilai.setText(nilai);
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_aturan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_edit_aturan:
                String kode = txKode.getText().toString().trim();
                String nilai = txNilai.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(AturanDB.row_kdRules, kode);
                values.put(AturanDB.row_alternatif, sPenyakit.getSelectedItem().toString().trim().substring(0,2));
                values.put(AturanDB.row_kriteria, sGejala.getSelectedItem().toString().trim().substring(0,3));
                values.put(AturanDB.row_nilai, nilai);

                if (nilai.equals("")){
                    Toast.makeText(EditAturan.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    txNilai.requestFocus();
                }else {
                    Boolean cek = aturanDB.cekData(sPenyakit.getSelectedItem().toString().trim().substring(0,2), sGejala.getSelectedItem().toString().trim().substring(0,3));
                    Cursor check = aturanDB.checkData(sPenyakit.getSelectedItem().toString().trim().substring(0,2), sGejala.getSelectedItem().toString().trim().substring(0,3));
                    if (check.moveToFirst()) {
                        String data = check.getString(check.getColumnIndex(AturanDB.row_kdRules));
                        if (cek==true && data.equals(txKode.getText().toString())) {
                            ContentValues value = new ContentValues();
                            value.put(PositionDB.row_kdRule, kode);
                            value.put(PositionDB.row_posAlt, sPenyakit.getSelectedItemPosition());
                            value.put(PositionDB.row_posKri, sGejala.getSelectedItemPosition());
                            positionDB.updateData(value, kode);
                            aturanDB.updateData(values, id);
                            Toast.makeText(EditAturan.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                            finish();
                        } else
                        if (cek == true && data != txKode.getText().toString()) {
                                Toast.makeText(EditAturan.this, "Data sudah ada", Toast.LENGTH_SHORT).show();
                        } else
                        if (cek == false) {
                            ContentValues value = new ContentValues();
                            value.put(PositionDB.row_kdRule, kode);
                            value.put(PositionDB.row_posAlt, sPenyakit.getSelectedItemPosition());
                            value.put(PositionDB.row_posKri, sGejala.getSelectedItemPosition());
                            positionDB.updateData(value, kode);
                            aturanDB.updateData(values, id);
                            finish();
                            Toast.makeText(EditAturan.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit_aturan:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditAturan.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aturanDB.deleteData(id);
                        positionDB.deleteData(txKode.getText().toString().trim());
                        Toast.makeText(EditAturan.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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