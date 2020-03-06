package com.example.hucklebucklebuckeye.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.hucklebucklebuckeye.model.Account.SQL_CREATE_ENTRIES;
import static com.example.hucklebucklebuckeye.model.Account.SQL_DELETE_ENTRIES;

public class AccountDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    //Database Version
    private static final int DATABASE_VERSION = 1;

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
        database = getWritableDatabase();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_USERNAME + " TEXT, " + COLUMN_PASSWORD + " TEXT) ");
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
       long result = db.insert(TABLE_NAME, null, contentValues);
       if (result == -1) {
           return false;
       } else {
           return true;
       }
    }

}
