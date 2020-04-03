package com.example.hucklebucklebuckeye.ui.playgame;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.ExecutionException;



public class GameActivity extends AppCompatActivity {
    public static double lat;
    public static double lon;
    Toast toast;
    String s = "";
    int PERMISSION_ID = 44;
    private AsyncTask<Game, String, Boolean> testTask;
    private boolean isCancelled;
    private TextView updateMessage;
    private TextView stopwatchView;
    private TextView stepView;
    MapFragment mapFragment;

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
        Game game = new Game(getCurrentLocation());

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

        if (stepSensor != null){
            sensorManager.registerListener(stepListener, stepSensor, sensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, "Step Detector not found!", Toast.LENGTH_SHORT).show();
        }

        Log.d("TEST", "onCreate: Line before Async task");
        toast = Toast.makeText(this, "Starting game!", Toast.LENGTH_SHORT);

        this.testTask= new LocationUpdateTask();
        testTask.execute(game);
        Log.d("TEST", this.s);

        mapFragment = MapFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mapFragment)
                    .commitNow();
        }

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

        private void addLog(){
            final LogBaseHelper logHandler = new LogBaseHelper(getApplicationContext());
            ContentValues values = new ContentValues();
            LocalDate date = LocalDate.now();
            SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
            String time = localDateFormat.format(new Date());
                values.put("ACID", AccountDBHelper.getId());
                values.put("STEPS", 15);
                values.put("DATE", date.toString());
                values.put("MAP", destination.getName());
                values.put("DISTANCE", 10);
                values.put("TIME", time);
                values.put("COMPLETED", true);
                logHandler.insertData(values);
        }
        @Override
        protected Boolean doInBackground(Game... games) {

            final Game game = games[0];
            destination = game.getDestinationCoords();


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
                        } else if (distanceToDestination > previousDistance) {
                            updateMessage.setText("Colder...");
                        }
                        previousDistance = distanceToDestination;
                        foundDestination = game.destinationReached(new Coordinates("current ", lat, lon));
                        if (!foundDestination ){
                            toast.setText("You haven't found the destination yet! Distance Away: " + distanceToDestination + " ft");

                        } else{
                            stopwatch.Stop();
                            toast.setText("You found your destination!!!! Distance Away: " + distanceToDestination + " ft");
                            addLog();
                            handler.removeCallbacks(runnable);
                        }
                        toast.show();
                    }
                }
            }, tick);

            return true;
        }

        @Override
        protected void onProgressUpdate(String... s) {


        }

        @Override
        protected void onPostExecute(Boolean found) {

        }
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
                    Log.d("location is", "here is " + lon);
                    //Log.d("latitude: ", location.getLatitude()+"");
                    //Log.d("longitude: ", location.getLongitude()+"");
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
        this.testTask.cancel(true);
    }
}



