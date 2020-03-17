/*
History.java
@Author Sean Edwards
@Version 20200306
History class to handle data related to player logs.
*/
package com.example.hucklebucklebuckeye;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import com.example.hucklebucklebuckeye.model.LogBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class History {
    private static History sHistory;
    private List<UserLog> mLogs;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static History get(Context context){
        if (sHistory == null){
            sHistory = new History(context);
        }
        return sHistory;
    }

    public History(Context context){
        mContext = context;
        mDatabase = new LogBaseHelper(mContext).getWritableDatabase();
        Log.d("database:" , mDatabase.toString());
        mLogs = new ArrayList<>();
    }

    public List<UserLog> getLogs(){
        return mLogs;
    }

    public UserLog getLog(UUID id){
        for (UserLog log : mLogs){
            if (log.getId().equals(id)){
                return log;
            }
        }
        return null;
    }
}
