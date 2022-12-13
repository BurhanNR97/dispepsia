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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spk.dispepsia.Adapter.JenisPenyakitAdapter;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.R;

public class JenisPenyakit extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    JenisPenyakitDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis_penyakit);

        Toolbar toolbar = findViewById(R.id.toolbarjenis);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Jenis Penyakit");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabjenispenyakit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JenisPenyakit.this, TambahJenisPenyakit.class));
            }
        });

        db = new JenisPenyakitDB(this);
        listView = (ListView)findViewById(R.id.list_jenispenyakit);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_jenis, menu);
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
        JenisPenyakitAdapter customCursorAdapter = new JenisPenyakitAdapter(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDjenisrow);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        Intent idjenis = new Intent(JenisPenyakit.this, EditJenisPenyakit.class);
        idjenis.putExtra(JenisPenyakitDB.row_id, id);
        startActivity(idjenis);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}