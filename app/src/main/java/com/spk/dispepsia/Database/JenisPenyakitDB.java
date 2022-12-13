package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spk.dispepsia.Adapter.JenisPenyakitAdapter;

import java.util.ArrayList;
import java.util.List;

public class JenisPenyakitDB extends SQLiteOpenHelper {

    public static final String database_name = "lambun3.db";
    public static final String table_name = "jenispenyakit";
    public static final String row_id = "_id";
    public static final String row_kdPenyakit = "kd_penyakit";
    public static final String row_nmPenyakit = "nm_penyakit";

    private SQLiteDatabase db;

    public JenisPenyakitDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdPenyakit + " TEXT,"
                + row_nmPenyakit + " TEXT)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdPenyakit +", "+row_nmPenyakit+")"
                + "VALUES ('A1', 'Dispepsia Tipe Ulkus'), ('A2', 'Dispepsia Tipe Dismotilitas'), ('A3', 'Dispepsia Tipe Non-Spesifik')";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", new String[]{});
        return cur;
    }

    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Cursor kode(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdPenyakit + " DESC", null);
        return cur;
    }

    public List<String> ambilData() {
        List<String> listData = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {
                listData.add(cursor.getString(1) + " - " + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listData;
    }

    public List<String> alternatif() {
        List<String> listData = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
        if (cursor.moveToFirst()) {
            do {
                listData.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    public Cursor hasil(String a){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdPenyakit + "= ?", new String[] {a});
        return cur;
    }
}
