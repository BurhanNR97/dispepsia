package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class PositionDB extends SQLiteOpenHelper {

    public static final String database_name = "lambung511.db";
    public static final String table_name = "posisi";
    public static final String row_kdRule = "kd_rules";
    public static final String row_posAlt = "alternatif";
    public static final String row_posKri = "kriteria";

    private SQLiteDatabase db;

    public PositionDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" +  row_kdRule + " TEXT,"
                + row_posAlt + " TEXT," + row_posKri + " TEXT)";
        String query1 = "INSERT INTO " + table_name + "(" + row_kdRule +", "+row_posAlt+", "+row_posKri+")"
                + "VALUES ('R001','0','0'), ('R002', '0', '1'), ('R003', '0', '2'), ('R004', '0', '3'), ('R005', '0', '4'),"
                +"('R006', '0', '5'), ('R007', '0', '6'), ('R008', '0', '7'), ('R009', '0', '8'), ('R010', '0', '9'),"
                +"('R011', '1', '0'), ('R012', '1', '1'), ('R013', '1', '2'), ('R014', '1', '3'), ('R015', '1', '4'),"
                +"('R016', '1', '5'), ('R017', '1', '6'), ('R018', '1', '7'), ('R019', '1', '8'), ('R020', '1', '9'),"
                +"('R021', '2', '0'), ('R022', '2', '1'), ('R023', '2', '2'), ('R024', '2', '3'), ('R025', '2', '4'),"
                +"('R026', '2', '5'), ('R027', '2', '6'), ('R028', '2', '7'), ('R029', '2', '8'), ('R030', '2', '9')";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
    }

    public Cursor oneData(String id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_kdRule + "= ?", new String[] {id});
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, String id){
        db.update(table_name, values, row_kdRule + "= ?", new String[] {id});
    }

    //Delete Data
    public void deleteData(String id){
        db.delete(table_name, row_kdRule+ "= ?", new String[] {id});
    }
}
