/*
History.java
@Author Sean Edwards
@Version 20200306
History class to handle data related to player logs.
*/
package com.example.hucklebucklebuckeye.ui.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hucklebucklebuckeye.Log;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class History {
    private static History sHistory;
    private List<Log> mLogs;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static History get(Context context){
        if (sHistory == null){
            sHistory = new History(context);
        }
        return sHistory;
    }

    private History(Context context){
        mContext = context;
        mDatabase = new LogBaseHelper(mContext).getWritableDatabase();
        mLogs = new ArrayList<>();
    }

    public List<Log> getLogs(){
        return mLogs;
    }

    public Log getLog(UUID id){
        for (Log log : mLogs){
            if (log.getId().equals(id)){
                return log;
            }
        }
        return null;
    }
}
