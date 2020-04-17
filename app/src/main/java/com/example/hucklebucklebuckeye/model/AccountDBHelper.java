package com.example.hucklebucklebuckeye.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;


public class AccountDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    //Database Version
    private static final int DATABASE_VERSION = 1;

    private static int userId;
    private static String username;

    //Database Name
    public static final String DATABASE_NAME = "huckle.db";

    //Table Name
    private static final String TABLE_NAME = "account";

    //Table Fields
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    SQLiteDatabase database;

    public AccountDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase mydatabase = context.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        this.username = username;
       long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void updatePassword(String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, this.username);
        contentValues.put(COLUMN_PASSWORD, password);
        String[] args = {this.username};
        db.update(TABLE_NAME, contentValues, "username = ?", args);
        //long result = db.insert(TABLE_NAME, null, contentValues);
    }

    public boolean userExists(String username) {
        boolean exists;
        String[] columns = {COLUMN_ID};
        String whereClause = COLUMN_USERNAME + " = ? ";
        String[] whereArgs = {username};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, "", "", "");

        exists = cursor.getCount() != 0;
        cursor.close();
        return exists;
    }

    public void updateId(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String whereClause = COLUMN_USERNAME + " = ? ";
        String[] whereArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, "", "", "");
        if (cursor.moveToNext()){
            userId = cursor.getInt(0);
        }
        cursor.close();
    }

    public static int getId(){
        return userId;
    }
    public static String getUsername(){return username;}
    public boolean userValid(String username, String password) {
        boolean exists;
        String[] columns = {COLUMN_ID};
        String whereClause = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] whereArgs = {username, password};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, whereClause, whereArgs, "", "", "");
        exists = cursor.getCount() != 0;
        if(exists){this.username = username;}
        cursor.close();
        return exists;
    }
}
