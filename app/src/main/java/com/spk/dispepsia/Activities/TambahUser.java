package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

public class TambahUser extends AppCompatActivity {

    UserDB db;
    EditText txID, txUsername, txPassword;
    RadioButton rbAdmin, rbUser;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);

        Toolbar toolbar = findViewById(R.id.toolbartambahuser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tambah Data");

        db = new UserDB(this);

        id = getIntent().getLongExtra(UserDB.row_id, 0);

        txID = (EditText)findViewById(R.id.txtIdUsernameAdd);
        txUsername = (EditText)findViewById(R.id.txtUsernameAdd);
        txPassword = (EditText)findViewById(R.id.txtPasswordAdd);
        rbAdmin = (RadioButton)findViewById(R.id.RB_adminAdd);
        rbUser = (RadioButton)findViewById(R.id.RB_userAdd);

        Button simpan = (Button)findViewById(R.id.btnSaveAdd);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txUsername.getText().toString().trim();
                String password = txPassword.getText().toString().trim();
                String level;

                //Create Condition if Title and Detail is empty
                if (username.equals("") && password.equals("")){
                    Toast.makeText(TambahUser.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    RadioGroup rb = (RadioGroup)findViewById(R.id.RB_level);
                    int selID = rb.getCheckedRadioButtonId();
                    if (selID == rbAdmin.getId()) {
                        level = "admin";
                        ContentValues values = new ContentValues();
                        values.put(UserDB.row_email, username);
                        values.put(UserDB.row_password, password);
                        values.put(UserDB.row_level, level);
                        db.insertData(values);
                    } else
                    if (selID == rbUser.getId()) {
                        level = "user";
                        ContentValues values = new ContentValues();
                        values.put(UserDB.row_email, username);
                        values.put(UserDB.row_password, password);
                        values.put(UserDB.row_level, level);
                        db.insertData(values);
                    }
                    Toast.makeText(TambahUser.this, "Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        Cursor id = db.id();
        if (id.moveToNext()) {
            int a = Integer.parseInt(id.getString(0));
            int b = a + 1;
            txID.setText(""+b);
        } else
        {
            txID.setText("1");
        }
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu_user, menu);
        return true;
    }

}