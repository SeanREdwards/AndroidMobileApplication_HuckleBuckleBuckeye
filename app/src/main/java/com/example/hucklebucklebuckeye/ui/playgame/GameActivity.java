package com.example.hucklebucklebuckeye.ui.playgame;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hucklebucklebuckeye.Coordinates;
import com.example.hucklebucklebuckeye.Game;
import com.example.hucklebucklebuckeye.R;
import com.example.hucklebucklebuckeye.Stopwatch;
import com.example.hucklebucklebuckeye.model.AccountDBHelper;
import com.example.hucklebucklebuckeye.model.LogBaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;




public class GameActivity extends AppCompatActivity {
    public static double lat;
    public static double lon;
    Toast toast;
    int PERMISSION_ID = 44;
    private AsyncTask<Game, String, Boolean> locationUpdateTask;
    private boolean isCancelled;
    private String destinationName;

    private TextView updateMessage;
    private TextView stopwatchView;
    private TextView stepView;
    MapFragment mapFragment;
    private RelativeLayout background;

    //Background color setup
    private final int blue = Color.parseColor("#add8e6");
    private final int red = Color.parseColor("#da9b86");
    private final int white = Color.parseColor("#ffffff");
    private int currentColor;

    //For Stopwatch
    Stopwatch stopwatch;


    //For StepCounter
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int steps;


    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        isCancelled = false;
        background = findViewById(R.id.container);
        background.setBackgroundColor(white);
        Game game = new Game(getCurrentLocation());
        toast.makeText(this, "", Toast.LENGTH_SHORT);
        //Stopwatch setup
        stopwatchView = findViewById(R.id.stopwatch_view);
        stopwatch = new Stopwatch(stopwatchView);
        stopwatch.Start();

        //step counter setup
        stepView = findViewById(R.id.steps_view);
        steps = 0;
        //set initial steps taken text
        stepView.setText("Steps Taken: " + steps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        SensorEventListener stepListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                steps++;
                stepView.setText("Steps Taken: " + steps);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //Setup pedometer values
        if (stepSensor != null){
            sensorManager.registerListener(stepListener, stepSensor, sensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "Step Detector not found!", Toast.LENGTH_SHORT).show();
        }

        //Get initial background color
        currentColor = 0;
        if (background.getBackground() instanceof ColorDrawable){
            currentColor = ((ColorDrawable) background.getBackground()).getColor();}

        //start location update asyncronous task
        this.locationUpdateTask = new LocationUpdateTask();
        locationUpdateTask.execute(game);

        //commit mapFragment for mapview
        mapFragment = MapFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mapFragment)
                    .commitNow();
        }

        //initialize update message TextView
       updateMessage = findViewById(R.id.fragment_below_textview);
       updateMessage.setText("Play game!");
    }

    private class LocationUpdateTask extends AsyncTask<Game, String, Boolean> {
        private Handler handler;
        int tick;
        Runnable runnable;
        private boolean foundDestination;
        private double distanceToDestination;
        private double previousDistance;
        Coordinates destination;
        Coordinates currentLocation;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler = new Handler();
            tick = 3000;
            previousDistance = Integer.MAX_VALUE;
            foundDestination = false;
        }

        /*
         *
         * this was here to return the current coordinates. we might want to use it later
         */
        public Coordinates getCurrentLocation(){
            final double[] latlong = new double[2];
            mFusedLocationClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
                                                                              @Override
                                                                              public void onComplete(@NonNull Task<Location> task) {
                                                                                  Location location = task.getResult();
                                                                                  if (location == null) {
                                                                                      requestNewLocationData();
                                                                                  } else {
                                                                                      lat = location.getLatitude();
                                                                                      Log.d("location is", "here is" + lat);
                                                                                      lon = location.getLongitude();
                                                                                      Log.d("location is", "here is " + lon);
                                                                                      //Log.d("latitude: ", location.getLatitude()+"");
                                                                                      //Log.d("longitude: ", location.getLongitude()+"");
                                                                                  }
                                                                              }
                                                                          }
            );
            return new Coordinates("current", latlong[0], latlong[1]);

        }

        @Override
        protected Boolean doInBackground(Game... games) {

            final Game game = games[0];
            destination = game.getDestinationCoords();
            destinationName = destination.getName();


            handler.postDelayed( runnable = new Runnable() {
                public void run() {
                    if (!isCancelled){
                        handler.postDelayed(runnable, tick);
                        currentLocation = getCurrentLocation();
                        Log.d("HERE IT IS location is", "here is " + lat);
                        Log.d("HERE IT IS location is", "here is " + lon);
                        distanceToDestination = game.calcDistance(new Coordinates("current ", lat, lon));
                        if (distanceToDestination < previousDistance){
                            updateMessage.setText("Hotter...");
                            //background.setBackgroundColor(red);

                            if (currentColor != red){
                                transitionBackground(currentColor, red);
                            }

                            currentColor = red;
                        } else if (distanceToDestination > previousDistance) {
                            updateMessage.setText("Colder...");
                            //background.setBackgroundColor(blue);
                            if (currentColor != blue){
                                transitionBackground(currentColor, blue);
                            }
                            currentColor = blue;
                        }
                        previousDistance = distanceToDestination;
                        foundDestination = game.destinationReached(new Coordinates("current ", lat, lon));
                        if (!foundDestination ){
                            //toast.setText("You haven't found the destination yet! Distance Away: " + distanceToDestination + " ft");

                        } else{
                            stopwatch.Stop();
                            //toast.setText("You found your destination!!!! Distance Away: " + distanceToDestination + " ft");
                            addLog();
                            handler.removeCallbacks(runnable);
                        }
//                        toast.show();
                    }
                }
            }, tick);

            return true;
        }

        private void transitionBackground(int colorFrom, int colorTo){
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(300); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    background.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        }

        @Override
        protected void onProgressUpdate(String... s) {


        }

        @Override
        protected void onPostExecute(Boolean found) {
            //addLog();
        }
    }
    private void addLog(){
        final LogBaseHelper logHandler = new LogBaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();
        LocalDate date = LocalDate.now();
        //SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        double dist = Game.stepsToFeet(steps);
        String time = stopwatch.getTime();
        values.put("ACID", AccountDBHelper.getId());
        values.put("STEPS", steps);
        values.put("DATE", date.toString());
        values.put("MAP", destinationName);
        values.put("DISTANCE", dist);
        values.put("TIME", time);
        values.put("COMPLETED", true);
        logHandler.insertData(values);
    }

    /*
     *
     * returns current location as a set of coordinates
     */
    public Coordinates getCurrentLocation(){
        final double[] latlong = new double[2];
        mFusedLocationClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location == null) {
                    requestNewLocationData();
                } else {
                    lat = location.getLatitude();
                    Log.d("location is", "here is" + lat);
                    lon = location.getLongitude();
                }
            }
        }
        );
        return new Coordinates("current", latlong[0], latlong[1]);

    }


    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //Log.d("latitude: ", mLastLocation.getLatitude()+"");
            //Log.d("longitude: ", mLastLocation.getLongitude()+"");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("GameActivity", "onDestroy() method called");
        this.isCancelled = true;
        this.locationUpdateTask.cancel(true);

        //This is going to be removed later, but we are using it for testing purposes
        addLog();
    }
}



