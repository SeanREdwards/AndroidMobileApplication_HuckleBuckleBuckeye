package com.example.hucklebucklebuckeye.model;

public class History {


    private String mACID;
    private String mDate;
    private String mSteps;
    private String mMap;
    private String mDistance;
    private String mTime;
    private String mCompleted;


    public String getACID() {
        return mACID;
    }

    public String getDate() {
        return mDate;
    }

    public String getSteps() {
        return mSteps;
    }

    public String getMap() {
        return mMap;
    }

    public String getDistance() {
        return mDistance;
    }

    public String getTime() {
        return mTime;
    }

    public String getCompleted() {
        return mCompleted;
    }

    public void setACID(String ACID) {
        mACID = ACID;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setSteps(String steps) {
        mSteps = steps;
    }

    public void setMap(String map) {
        mMap = map;
    }

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setCompleted(String completed) {
        mCompleted = completed;
    }
}