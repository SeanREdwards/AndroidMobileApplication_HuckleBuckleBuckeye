package com.example.hucklebucklebuckeye.model;

import org.junit.Before;
import org.junit.Test;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryTest {

    private String mACID;
    private String mDate;
    private String mSteps;
    private String mMap;
    private String mDistance;
    private String mTime;
    private String mCompleted;
    private History mHistory;


    @Before
    public void setUp() throws Exception {

        mACID = "25";
        mDate = "2020-04-17";
        mSteps = "13";
        mMap = "";
        mDistance = "1.3";
        mTime = "14:47:48";
        mCompleted = "1";
        mHistory = new History();
        mHistory.setACID(mACID);
        mHistory.setDate(mDate);
        mHistory.setSteps(mSteps);
        mHistory.setMap(mMap);
        mHistory.setDistance(mDistance);
        mHistory.setTime(mTime);
        mHistory.setCompleted(mCompleted);

    }

    @Test
    public void checksACID() {
        assertThat(mHistory.getACID(), is(mACID));
    }

    @Test
    public void checksDate() {
        assertThat(mHistory.getDate(), is(mDate));
    }

    @Test
    public void checksSteps() {
        assertThat(mHistory.getSteps(), is(mSteps));
    }

    @Test
    public void checksMap() {
        assertThat(mHistory.getMap(), is(mMap));
    }

    @Test
    public void checksDistance() {
        assertThat(mHistory.getDistance(), is(mDistance));
    }

    @Test
    public void checksTime() {
        assertThat(mHistory.getTime(), is(mTime));
    }

    @Test
    public void checksCompleted() {
        assertThat(mHistory.getCompleted(), is(mCompleted));
    }

}