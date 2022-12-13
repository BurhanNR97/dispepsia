package com.spk.dispepsia.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.spk.dispepsia.Database.UserDB;
import com.spk.dispepsia.Database.BiodataDB;
import com.spk.dispepsia.R;

public class LoginActivity extends AppCompatActivity {

    EditText txt_username;
    EditText txt_password;
    TextInputLayout input_username;
    TextInputLayout input_password;
    Button btn_login;
    SQLiteOpenHelper sqLiteOpenHelper;
    private int countFragment;
    private SharedPreferences preferences;
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        //if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
          //  getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //}
        setContentView(R.layout.activity_login);
        changeStatusBarColor();

        txt_username = (EditText) findViewById(R.id.txt_Email);
        txt_password = (EditText) findViewById(R.id.txt_Password);
        btn_login = (Button) findViewById(R.id.cirLoginButton);

        UserDB db = new UserDB(this);
        BiodataDB db_ll = new BiodataDB(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txt_username.getText().toString().trim();
                String password = txt_password.getText().toString().trim();

                ContentValues values3 = new ContentValues();
                Boolean res = db.checkUser(email, password);

                if(res == true){
                    Cursor hak = db.checkLevel(email);
                    Cursor level = db_ll.sessionLogin(email);
                    finish();
                    Toast.makeText(LoginActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    if (hak.moveToNext()) {
                        if (hak.getString(3).equals("admin")) {
                            String l = "admin";
                            Intent intent = new Intent(getApplicationContext(), BerandaAdmin.class);
                            intent.putExtra("admin", l);
                            finish();
                            startActivity(intent);
                        } else
                        if (hak.getString(3).equals("user")) {
                            if (level.moveToNext()) {
                                String l = level.getString(1);
                                String nikk = level.getString(0);
                                Intent intent = new Intent(getApplicationContext(), BerandaUser.class);
                                intent.putExtra("user", l);
                                intent.putExtra("nik", nikk);
                                finish();
                                startActivity(intent);
                            }
                        } else
                        if (hak.getString(3).equals("dokter")) {
                            String l = "Dokter";
                            Intent intent = new Intent(getApplicationContext(), BerandaDokter.class);
                            intent.putExtra("dokter", l);
                            finish();
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public int getCountFragment() {
        return countFragment;
    }

    @Override
    public void onBackPressed() {
        int count = getCountFragment();
        if(count == 0) {
            getFragmentManager().popBackStack();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}