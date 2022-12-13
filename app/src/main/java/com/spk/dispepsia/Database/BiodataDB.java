package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BiodataDB extends SQLiteOpenHelper {

    public static final String database_name = "DBspkdisspepsia.db";
    public static final String table_biodata = "biodata";
    public static final String row_NIK = "nik";
    public static final String row_nama = "nama";
    public static final String row_tgl = "tgl_lahir";
    public static final String row_alamat = "alamat";
    public static final String row_telp = "telp";
    public static final String row_email = "email";

    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_biodata + "(" + row_NIK + " INTEGER PRIMARY KEY," + row_nama + " TEXT,"
                + row_tgl + " TEXT," + row_alamat + " TEXT," + row_telp + " TEXT,"
                + row_email + " TEXT)";
        String query1 = "INSERT INTO " + table_biodata + "(" + row_NIK + "," + row_nama + ", " + row_tgl + ", " + row_alamat + ", "
                + row_telp + ", " + row_email + ") VALUES(123456789, 'User Biasa', '01-01-1997', 'Indonesia', '08512345678', 'user')";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_biodata);
        onCreate(db);
    }

    public void insertBiodata(ContentValues values){
        db.insert(table_biodata, null, values);
    }

    /*public void insertBiodata(String nik, String nama, String tgl_lahir, String alamat, String telp, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues biodatavalues = new ContentValues();
        biodatavalues.put("nik", nik);
        biodatavalues.put("nama", password);
        biodatavalues.p ut("level", level);

        sqLiteDatabase.insert(table_user, null, uservalues);
    }*/

    public BiodataDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    public Cursor sessionLogin(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata WHERE email = ?",new String[] {email});
        return cursor;
    }

    public Cursor tampilProfile1User(String nik) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata WHERE nik = ?",new String[] {nik});
        return cursor;
    }

    public boolean checkNIK (String nik) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM biodata WHERE nik = ?",new String[] {nik});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM " + table_biodata + " WHERE email = ?",new String[] {email});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

}
