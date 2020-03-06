/*
Log.java
@Author Sean Edwards
@Version 20200306
Log class to store past played sessions of HuckleBuckleBuckeye.
*/
package com.example.hucklebucklebuckeye;

import android.graphics.Bitmap;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    //Steps taken.
    private int mSteps;

    //Logged map image.
    private Bitmap mMapImage;

    //Distance in miles.
    private double mDistance;

    //Date session played.
    private LocalDate mDate;

    //Total Time of Session.
    private LocalTime mTime;

    //Status of successful finish.
    private boolean mWon;

    public Log(int steps, Bitmap mapImage, double distance, LocalDate date, LocalTime time, boolean won){
        mSteps = steps;
        mMapImage = mapImage;
        mDistance = distance;
        mDate = date;
        mTime = time;
        mWon = won;
    }

    /*Getter methods*/
    public int getSteps() {
        return mSteps;
    }

    public Bitmap getMapImage() {
        return mMapImage;
    }

    public double getDistance() {
        return mDistance;
    }

    public LocalDate getDate() {
        return mDate;
    }

    public LocalTime getTime() {
        return mTime;
    }

    public boolean isWon() {
        return mWon;
    }
}
