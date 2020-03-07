package com.example.hucklebucklebuckeye.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "logBase.db";

    public LogBaseHelper(Context context){
        super (context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + LogDBSchema.LogTable.NAME + "(" +
                LogDBSchema.LogTable.Cols.UUID + " integer primary key autoincrement, " +
                LogDBSchema.LogTable.Cols.DATE + ", " +
                LogDBSchema.LogTable.Cols.STEPS + ", " +
                LogDBSchema.LogTable.Cols.MAP + ", "  +
                LogDBSchema.LogTable.Cols.DISTANCE + ", " +
                LogDBSchema.LogTable.Cols.TIME + ", " +
                LogDBSchema.LogTable.Cols.COMPLETED + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
