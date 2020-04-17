/*
UserLog.java
@Author Sean Edwards
@Version 20200306
UserLog class to store past played sessions of HuckleBuckleBuckeye.
*/
package com.example.hucklebucklebuckeye;

import android.graphics.Bitmap;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

public class UserLog {
    private UUID mId;

    //Date session played.
    private Date mDate;

    //Steps taken.
    private int mSteps;

    //Logged map image.
    private Bitmap mMapImage;

    //Distance in miles.
    private double mDistance;

    //Total Time of Session.
    private LocalTime mTime;

    //Status of successful finish.
    private boolean mCompleted;

    public UserLog(int steps, Bitmap mapImage, double distance, LocalTime time, boolean completed){
        mId = UUID.randomUUID();
        UUID acid = UUID.randomUUID();
        mDate = new Date();
        mSteps = steps;
        mMapImage = mapImage;
        mDistance = distance;
        mTime = time;
        mCompleted = completed;
    }

    /*Getter methods*/

    public UUID getId() {

        //TODO May be able to remove
        //AccountDBHelper.getId();

        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public int getSteps() {
        return mSteps;
    }

    public Bitmap getMapImage() {
        return mMapImage;
    }

    public double getDistance() {
        return mDistance;
    }

    public LocalTime getTime() {
        return mTime;
    }

    public boolean ismCompleted() {
        return mCompleted;
    }
}
