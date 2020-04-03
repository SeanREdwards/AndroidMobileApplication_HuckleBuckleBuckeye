package com.example.hucklebucklebuckeye;

import android.os.Handler;
import android.os.SystemClock;

public class Stopwatch {

    //Stopwatch variables for  Game
    private int milliseconds;
    private int seconds;
    private int minutes;
    private int hours;
    private long millisecondTime;
    private long startTime;
    private long timeBuffer;
    private long updatedTime;
    private Handler stopwatchHandler;
    private Runnable runnable;

    public Stopwatch() {
        updatedTime = 0L;
        startTime = SystemClock.uptimeMillis();
        stopwatchHandler = new Handler();
        stopwatchHandler.postDelayed(runnable, 0);
        runnable = new Runnable() {
            public void run() {
                millisecondTime = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeBuffer + millisecondTime;
                seconds = (int) (updatedTime / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                milliseconds = (int) (updatedTime % 1000);
                stopwatchHandler.postDelayed(this, 0);
            }
        };
    }

    public int getMiliseconds(){
        return milliseconds;
    }

    public int getSeconds(){
        return seconds;
    }

    public int getMinutes(){
        return minutes;
    }

}
