package com.example.hucklebucklebuckeye.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.hucklebucklebuckeye.model.Account.SQL_CREATE_ENTRIES;
import static com.example.hucklebucklebuckeye.model.Account.SQL_DELETE_ENTRIES;

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
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + UUID + " INTEGER PRIMARY KEY, " + DATE + " DATE, " + STEPS + " INTEGER, " + MAP + " BITMAP, " + DISTANCE + " INTEGER, " + TIME + " INTEGER, " + COMPLETED + " BOOLEAN) ");
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create table " + LogDBSchema.LogTable.NAME + "(" +
//                LogDBSchema.LogTable.Cols.UUID + " integer primary key autoincrement, " +
//                LogDBSchema.LogTable.Cols.DATE + ", " +
//                LogDBSchema.LogTable.Cols.STEPS + ", " +
//                LogDBSchema.LogTable.Cols.MAP + ", " +
//                LogDBSchema.LogTable.Cols.DISTANCE + ", " +
//                LogDBSchema.LogTable.Cols.TIME + ", " +
//                LogDBSchema.LogTable.Cols.COMPLETED + ")");
//
//    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + UUID + " INTEGER PRIMARY KEY, " + DATE + " DATE, " + STEPS + " INTEGER, " + MAP + " BITMAP, " + DISTANCE + " INTEGER, " + TIME + " INTEGER, " + COMPLETED + " BOOLEAN) ");
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

    public boolean deleteTitle(String temporary)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, DISTANCE + "=" + temporary, null) > 0;
    }


}
