package com.example.hucklebucklebuckeye.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hucklebucklebuckeye.model.AccountDBHelper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static LogBaseHelper sLogBaseHelper;

    private List<History> mHistory;

    /*public static LogBaseHelper get(Context context) {

    }*/

    SQLiteDatabase database;

    public static LogBaseHelper get(Context context) {
        if (sLogBaseHelper == null) {
            sLogBaseHelper = new LogBaseHelper(context);
        }
        return sLogBaseHelper;
    }


    public LogBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();

        LocalDate date = LocalDate.now();
        ContentValues values = new ContentValues();
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = localDateFormat.format(new Date());
        values.put("ACID", AccountDBHelper.getId());
        values.put("STEPS", 12);
        values.put("DATE", date.toString());
        values.put("MAP", "");
        values.put("DISTANCE", 1.3);
        values.put("TIME", time);
        values.put("COMPLETED", false);

        long result = db.insert(TABLE_NAME, null, values);

        Map<String,String> data = new HashMap<>();

        for (String key : values.keySet()) {
            data.put(key, values.get(key).toString());
        }

        Log.d("heree it is", "hi " + values.get("DISTANCE"));

        mHistory = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            History history = new History();
            history.setACID(values.get("ACID").toString());
            history.setSteps(values.get("STEPS").toString());
            history.setDate(values.get("DATE").toString());
            history.setMap(values.get("MAP").toString());
            history.setDistance(values.get("DISTANCE").toString());
            history.setTime(values.get("TIME").toString());
            history.setCompleted(values.get("COMPLETED").toString());
            /*Log.d("sadada", "here is" + history);*/
            mHistory.add(history);
        }

        database = getWritableDatabase();
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " + UUID + " INTEGER PRIMARY KEY, " + ACID + " UUID," + DATE + " DATE, " + STEPS + " INTEGER, " + MAP + " BITMAP, " + DISTANCE + " INTEGER, " + TIME + " INTEGER, " + COMPLETED + " BOOLEAN, " + " FOREIGN KEY(ACID) REFERENCES account(id))");
    }

    public List<History> getHistorys() {
            return mHistory;
    }

    public History getHistory(String ACID) {
        for (History history : mHistory) {
            if (history.getACID().equals(ACID)) { return history;
            } }
        return null;
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

        Map<String,String> data = new HashMap<>();

        for (String key : contentValues.keySet()) {
            data.put(key, contentValues.get(key).toString());
        }

        Log.d("heree it is", "hi " + data.get("ACID"));
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
