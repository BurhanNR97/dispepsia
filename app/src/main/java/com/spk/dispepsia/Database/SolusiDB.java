package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SolusiDB extends SQLiteOpenHelper {

    public static final String database_name = "lambung4.db";
    public static final String table_name = "solusi";
    public static final String row_id = "_id";
    public static final String row_kdSolusi = "kd_solusi";
    public static final String row_nmSolusi = "nm_solusi";

    private SQLiteDatabase db;

    public SolusiDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdSolusi + " TEXT,"
                + row_nmSolusi + " TEXT)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdSolusi +", "+row_nmSolusi+")"
                + "VALUES ('S01', 'Mengatur pola makan'), ('S02', 'Menghindari makanan yang mengandung lemak yang tinggi, misalnya keju dan cokelat'), ('S03', 'Menghindari makanan yang menimbulkan gas seperti kol, kubis dan kentang'),"
                + "('S04', 'Menghindari makanan yang terlalu pedis'), ('S05', 'Menghindari rokok, alkohol, dan minuman dengan kadar kafein tinggi'), ('S06', 'Hindari obat yang bisa mengiritasi lambung'),"
                + "('S07', 'Hindari stress berlebihan'),"
                + " ('S08', 'Mengonsumsi makanan lebih sering dengan porsi lebih sedikit. Perut yang kosong kadang dapat menyebabkan sakit perut non-ulkus. Perut yang kosong dengan asam yang membuat anda mual. Cobalah untuk mengonsumsi cemilan, seperti cracker atau buah-buahan'), "
                + "('S09','Kunyalah makanan dengan perlahan hingga halus. Luangkan waktu untuk makan dengan perlahan')";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_id + " ASC ", null);
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
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdSolusi + " DESC", null);
        return cur;
    }
}
