package com.example.hucklebucklebuckeye;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import java.util.Locale;

public class Stopwatch {

    //Stopwatch variables
    private int seconds;
    private boolean running;
    private boolean wasRunning;
    private String strTime;
    private TextView timeView;


    public Stopwatch(TextView view) {
        seconds = 0;
        timeView = view;
    }

    public void Start() {
        running = true;
        final Handler handler = new Handler();
        handler.post(new Runnable(){
            @Override
            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                strTime = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(strTime);

                if (running) {
                    seconds++;
                }

                handler.postDelayed((Runnable) this, 1000);
            }
        });
    }

    public void Stop(){
        running = false;
    }

    public String getTime() {
        return strTime;
    }
}
