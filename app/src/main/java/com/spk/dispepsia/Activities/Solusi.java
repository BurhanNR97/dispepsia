package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spk.dispepsia.Adapter.GejalaAdapter;
import com.spk.dispepsia.Adapter.JenisPenyakitAdapter;
import com.spk.dispepsia.Adapter.SolusiAdapter;
import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.SolusiDB;
import com.spk.dispepsia.R;

public class Solusi extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    SolusiDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solusi);

        Toolbar toolbar = findViewById(R.id.toolbarsolusi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Solusi Pencegahan");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabsolusi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Solusi.this, TambahSolusi.class));
            }
        });

        db = new SolusiDB(this);
        listView = (ListView)findViewById(R.id.list_solusi);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_solusi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListView(){
        Cursor cursor = db.allData();
        SolusiAdapter customCursorAdapter = new SolusiAdapter(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDsolusirow);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        Intent idsolusi = new Intent(Solusi.this, EditSolusi.class);
        idsolusi.putExtra(SolusiDB.row_id, id);
        startActivity(idsolusi);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}