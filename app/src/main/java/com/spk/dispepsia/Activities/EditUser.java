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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.spk.dispepsia.Database.GejalaDB;
import com.spk.dispepsia.Database.JenisPenyakitDB;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.R;

public class EditUser extends AppCompatActivity {
    UserDB db;
    EditText txID, txUsername, txPassword;
    RadioButton rbAdmin, rbUser;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Toolbar toolbar = findViewById(R.id.toolbaredituser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perbarui Data");

        db = new UserDB(this);

        id = getIntent().getLongExtra(UserDB.row_id, 0);

        txID = (EditText)findViewById(R.id.txtIdUsernameEdit);
        txUsername = (EditText)findViewById(R.id.txtUsernameEdit);
        txPassword = (EditText)findViewById(R.id.txtPasswordEdit);
        rbAdmin = (RadioButton)findViewById(R.id.RB_adminEdit);
        rbUser = (RadioButton)findViewById(R.id.RB_userEdit);

        Cursor cursor = db.oneData(id);
        if(cursor.moveToFirst()){
            String iid = cursor.getString(cursor.getColumnIndex(UserDB.row_id));
            String username = cursor.getString(cursor.getColumnIndex(UserDB.row_email));
            String password = cursor.getString(cursor.getColumnIndex(UserDB.row_password));
            String level = cursor.getString(cursor.getColumnIndex(UserDB.row_level));

            txID.setText(iid);
            txUsername.setText(username);
            txPassword.setText(password);
            if (level.equals("admin")) {
                rbAdmin.setChecked(true);
                rbUser.setChecked(false);
            } else
            if (level.equals("user")) {
                rbAdmin.setChecked(false);
                rbUser.setChecked(true);
            }
        }

        Button simpan = (Button)findViewById(R.id.btnSaveEdit);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iiid = txID.getText().toString().trim();
                String username = txUsername.getText().toString().trim();
                String password = txPassword.getText().toString().trim();
                String level;

                //Create Condition if Title and Detail is empty
                if (username.equals("") && password.equals("")){
                    Toast.makeText(EditUser.this, "Nothing to save", Toast.LENGTH_SHORT).show();
                }else{
                    RadioGroup rb = (RadioGroup)findViewById(R.id.RB_levelEdit);
                    int selID = rb.getCheckedRadioButtonId();
                    if (selID == rbAdmin.getId()) {
                        level = "admin";
                        ContentValues values = new ContentValues();
                        values.put(UserDB.row_email, username);
                        values.put(UserDB.row_password, password);
                        values.put(UserDB.row_level, level);
                        db.updateData(values, iiid);
                    } else
                    if (selID == rbUser.getId()) {
                        level = "user";
                        ContentValues values = new ContentValues();
                        values.put(UserDB.row_email, username);
                        values.put(UserDB.row_password, password);
                        values.put(UserDB.row_level, level);
                        db.insertData(values);
                    }
                    Toast.makeText(EditUser.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.edit_menu_user, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_edit_user:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditUser.this);
                builder.setMessage("Ingin menghapus data ini ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteData(id);
                        Toast.makeText(EditUser.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
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