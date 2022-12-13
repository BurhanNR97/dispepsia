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
import com.spk.dispepsia.Adapter.HasilDiagnosaAdapter;
import com.spk.dispepsia.Adapter.JenisPenyakitAdapter;
import com.spk.dispepsia.Adapter.LaporanAdapter;
import com.spk.dispepsia.Database.DiagnosaDB;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class HasilDiagnosa extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DiagnosaDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_diagnosa);

        Toolbar toolbar = findViewById(R.id.toolbarhasildiagnosa);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Riwayat Diagnosa");

        db = new DiagnosaDB(this);
        listView = (ListView)findViewById(R.id.list_hasil_diagnosa);
        listView.setOnItemClickListener(this);
    }

    public void setListView(){
        String nik = getIntent().getStringExtra("nik");
        Cursor cursor = db.dataNIK(nik);
        if (cursor.moveToNext()){
            HasilDiagnosaAdapter customCursorAdapter = new HasilDiagnosaAdapter(this, cursor, 1);
            listView.setAdapter(customCursorAdapter);
        } else {
            Toast.makeText(HasilDiagnosa.this, "Belum ada hasil diagnosa", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDhasil);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();
        Intent idlap = new Intent(HasilDiagnosa.this, EditHasilDiagnosa.class);
        idlap.putExtra(DiagnosaDB.row_id, id);
        startActivity(idlap);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}