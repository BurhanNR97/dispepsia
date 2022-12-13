package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiagnosaDB extends SQLiteOpenHelper {

    public static final String database_name = "DBspkdiispepsiia.db";
    public static final String table_name = "diagnosa";
    public static final String row_id = "_id";
    public static final String row_kdDiagnosa = "kd_diagnosa";
    public static final String row_nik_pasien = "nik_pasien";
    public static final String row_nama_pasien = "nama_pasien";
    public static final String row_hasil_diagnosa = "hasil_diagnosa";
    public static final String row_tgl_diagnosa = "tgl_diagnsoa";

    private SQLiteDatabase db;

    public DiagnosaDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdDiagnosa + " TEXT,"
                + row_nik_pasien + " TEXT," + row_nama_pasien + " TEXT," + row_hasil_diagnosa + " TEXT," + row_tgl_diagnosa + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
        return cur;
    }

    public Cursor oneData(long kode){
        Cursor cur = db.rawQuery("SELECT * FROM diagnosa WHERE _id " + "=" + kode, null);
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
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdDiagnosa + " DESC", null);
        return cur;
    }

    public void hapusdata(){
        db.delete(table_name, null, null);
    }

    public Cursor dataNIK(String nik){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_nik_pasien + "= ?", new String[] {nik});
        return cur;
    }
}
