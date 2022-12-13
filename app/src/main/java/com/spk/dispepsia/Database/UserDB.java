package com.spk.dispepsia.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class
UserDB extends SQLiteOpenHelper {

    public static final String database_name = "dispepsiaDiaagnosa87.db";
    public static final String table_user = "users";
    public static final String row_id = "_id";
    public static final String row_email = "email";
    public static final String row_password = "password";
    public static final String row_level = "level";

    private SQLiteDatabase db;


    public UserDB(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_user + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + row_email + " TEXT," + row_password + " TEXT," + row_level + " TEXT)";
        String query1 = "INSERT INTO " + table_user + "(" + row_id + ", " + row_email + ", " + row_password + ", "
                + row_level + ") VALUES('1', 'admin', '1234', 'admin')";
        String query2 = "INSERT INTO " + table_user + "(" + row_id + ", " + row_email + ", " + row_password + ", "
                + row_level + ") VALUES('2', 'user', '1234', 'user')";
        String query3 = "INSERT INTO " + table_user + "(" + row_id + ", " + row_email + ", " + row_password + ", "
                + row_level + ") VALUES('3', 'dokter', '1234', 'dokter')";
        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_user);
        onCreate(db);
    }

    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_user + " ORDER BY " + row_id + " ASC ", null);
        return cur;
    }

    public Cursor oneData(long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_user + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data
    public void insertData(ContentValues values){
        db.insert(table_user, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, String id){
        db.update(table_user, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_user, row_id + "=" + id, null);
    }

    public Cursor simpanUser(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session ORDER BY id_sess DESC", null);
        cursor.close();
        db.close();
        return cursor;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT email FROM " + table_user + " WHERE email = ?",new String[] {email});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor checkLevel(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_user + " WHERE email = ?",new String[] {email});
        return cursor;
    }

    public boolean checkUser(String username, String password){
        String[] columns = {row_id};
        SQLiteDatabase db = getReadableDatabase();
        String selection = row_email + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username,password};
        Cursor cursor = db.query(table_user, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count>0)
            return true;
        else
            return false;
    }

    public Cursor id(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_user + " ORDER BY " + row_id + " DESC", null);
        return cur;
    }
}
