package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AturanDB extends SQLiteOpenHelper {

    public static final String database_name = "lambung11.db";
    public static final String table_name = "rules";
    public static final String row_id = "_id";
    public static final String row_kdRules = "kd_rules";
    public static final String row_alternatif = "alternatif";
    public static final String row_kriteria = "kriteria";
    public static final String row_nilai = "nilai";

    private SQLiteDatabase db;

    public AturanDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + row_kdRules + " TEXT,"
                + row_alternatif + " TEXT," + row_kriteria + " TEXT," + row_nilai + ")";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdRules +", "+row_alternatif+", "+row_kriteria+", "+row_nilai+")"
                + "VALUES ('R001','A1','G01','2'), ('R002', 'A1', 'G02', '2'), ('R003', 'A1', 'G03', '5'), ('R004', 'A1', 'G04', '2'), ('R005', 'A1', 'G05', '4'),"
                +"('R006', 'A1', 'G06', '1'), ('R007', 'A1', 'G07', '4'), ('R008', 'A1', 'G08', '3'), ('R009', 'A1', 'G09', '4'), ('R010', 'A1', 'G10', '2'),"
                +"('R011', 'A2', 'G01', '5'), ('R012', 'A2', 'G02', '4'), ('R013', 'A2', 'G03', '2'), ('R014', 'A2', 'G04', '5'), ('R015', 'A2', 'G05', '5'),"
                +"('R016', 'A2', 'G06', '4'), ('R017', 'A2', 'G07', '3'), ('R018', 'A2', 'G08', '2'), ('R019', 'A2', 'G09', '1'), ('R020', 'A2', 'G10', '1'),"
                +"('R021', 'A3', 'G01', '1'), ('R022', 'A3', 'G02', '2'), ('R023', 'A3', 'G03', '2'), ('R024', 'A3', 'G04', '1'), ('R025', 'A3', 'G05', '1'),"
                +"('R026', 'A3', 'G06', '1'), ('R027', 'A3', 'G07', '1'), ('R028', 'A3', 'G08', '5'), ('R029', 'A3', 'G09', '3'), ('R030', 'A3', 'G10', '5')";
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
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " ORDER BY " + row_kdRules + " DESC", null);
        return cur;
    }

    public Cursor checkData (String alt, String kri) {
        String table = table_name;
        String[] columns = new String[] {"*"};
        String selection = row_alternatif+ "=?" + " and " + row_kriteria + "=?";
        String[] selectionArgs = {alt,kri};
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        return cursor;
    }

    public boolean cekData(String alt, String Ci) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? AND kriteria = ?",new String[] {alt,Ci});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor dataNilai(String Ci) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE kriteria = ? ORDER BY "+row_id+"",new String[] {Ci});
        return cursor;
    }

    public List<Integer> a1() {
        List<Integer> listData = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? ORDER BY " + row_id + " ASC ", new String[] {"A1"});
        if (cursor.moveToFirst()) {
            do {
                listData.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(row_nilai))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    public List<Integer> a2() {
        List<Integer> listData = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? ORDER BY " + row_id + " ASC ", new String[] {"A2"});
        if (cursor.moveToFirst()) {
            do {
                listData.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(row_nilai))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    public List<Integer> a3() {
        List<Integer> listData = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? ORDER BY " + row_id + " ASC ", new String[] {"A3"});
        if (cursor.moveToFirst()) {
            do {
                listData.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(row_nilai))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }

    public Cursor altkri(String alt, String kri) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name + " WHERE alternatif = ? AND kriteria = ? ORDER BY " + row_id + " ASC ", new String[] {alt, kri});
        return cursor;
    }
}
