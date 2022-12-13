package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spk.dispepsia.Adapter.GejalaAdapter;
import com.spk.dispepsia.Adapter.JenisPenyakitAdapter;
import com.spk.dispepsia.Adapter.LaporanAdapter;
import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class LaporanDiagnosa extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DiagnosaDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_diagnosa);

        Toolbar toolbar = findViewById(R.id.toolbarlaporan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Laporan Diagnosa");

        db = new DiagnosaDB(this);
        listView = (ListView)findViewById(R.id.list_laporan);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_laporan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_remove_all) {
            DiagnosaDB db = new DiagnosaDB(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(LaporanDiagnosa.this);
            builder.setMessage("Ingin menghapus semua data diagnosa ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.hapusdata();
                    Toast.makeText(LaporanDiagnosa.this, "Semua data berhasil dihapus", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LaporanDiagnosa.class));
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

    public void setListView(){
        Cursor cursor = db.allData();
        if (cursor.moveToNext()){
            LaporanAdapter customCursorAdapter = new LaporanAdapter(this, cursor, 1);
            listView.setAdapter(customCursorAdapter);
        } else {
            Toast.makeText(LaporanDiagnosa.this, "Belum ada data laporan diagnosa", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDlaporan);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();
        Intent idlap = new Intent(LaporanDiagnosa.this, EditLaporan.class);
        idlap.putExtra(DiagnosaDB.row_id, id);
        startActivity(idlap);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}