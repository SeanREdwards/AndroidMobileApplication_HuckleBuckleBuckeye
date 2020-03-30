package com.example.hucklebucklebuckeye.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.hucklebucklebuckeye.model.AccountDBHelper;

public class LogBaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    public static final String DATABASE_NAME = "huckle.db";

    //Table Name
    private static final String TABLE_NAME = "logs";

    //Table Fields
    public static final String UUID = "uuid";
    public static final String ACID = "acid";
    public static final String DATE = "date";
    public static final String STEPS = "steps";
    public static final String MAP = "map";
    public static final String DISTANCE = "distance";
    public static final String TIME = "time";
    public static final String COMPLETED = "completed";

    
    SQLiteDatabase database;

    public LogBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + UUID + " INTEGER PRIMARY KEY, " + ACID + " UUID," + DATE + " DATE, " + STEPS + " INTEGER, " + MAP + " BITMAP, " + DISTANCE + " INTEGER, " + TIME + " INTEGER, " + COMPLETED + " BOOLEAN, " + " FOREIGN KEY(ACID) REFERENCES account(id))");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + UUID + " INTEGER PRIMARY KEY, " + ACID + " UUID," + DATE + " DATE, " + STEPS + " INTEGER, " + MAP + " BITMAP, " + DISTANCE + " INTEGER, " + TIME + " INTEGER, " + COMPLETED + " BOOLEAN, " + " FOREIGN KEY(ACID) REFERENCES account(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean updateData(ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.update(TABLE_NAME, contentValues, "uuid = 1", null);
        //long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean deleteRow(String temporary)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,  "uuid = " + temporary, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


}
