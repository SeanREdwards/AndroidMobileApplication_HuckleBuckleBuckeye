/*
Locations.java
@Author Sean Edwards & Natalie Hartsook
@Version 20200306
Primary game activity that runs and displays the main game functionality of the app.
*/
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
import java.time.LocalDate;


public class GameActivity extends AppCompatActivity {
    private static Game game;
    public static double lat;
    public static double lon;
    Toast toast;
    int PERMISSION_ID = 44;
    private AsyncTask<Game, String, Boolean> locationUpdateTask;
    private boolean isCancelled;
    private String destinationName;
    private static boolean gameInProg;
    private TextView updateMessage;
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


    private int steps;


    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean gameWon = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        isCancelled = false;
        AccountDBHelper db = new AccountDBHelper(getApplicationContext());
        int id = db.getId();
        if (gameInProg){
            if(game.getId() != id){
                gameInProg = false;
            }
        }

        //Message setup
        background = findViewById(R.id.container);
        if(!gameInProg){
            background.setBackgroundColor(white);
        } else {
            background.setBackgroundColor(game.getCurrentColor());
        }
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        //Stopwatch setup
        TextView stopwatchView = findViewById(R.id.stopwatch_view);
        if (gameInProg){
            stopwatch = new Stopwatch(stopwatchView, game.getTime());
        } else {
            stopwatch = new Stopwatch(stopwatchView);
        }
        stopwatch.Start();

        //step counter setup
        stepView = findViewById(R.id.steps_view);
        if (gameInProg){
            steps = game.getStepCount();
        } else {
            steps = 0;
        }
        //set initial steps taken text
        stepView.setText(getString(R.string.Steps) + steps);
        //For StepCounter
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        SensorEventListener stepListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                steps++;
                stepView.setText(getString(R.string.Steps) + steps);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //Setup pedometer values
        if (stepSensor != null){
            sensorManager.registerListener(stepListener, stepSensor, sensorManager.SENSOR_DELAY_UI);
        }else{
            Toast.makeText(this, getString(R.string.stepDetector), Toast.LENGTH_SHORT).show();
        }

        //Get initial background color
        currentColor = 0;
        if (background.getBackground() instanceof ColorDrawable){
            currentColor = ((ColorDrawable) background.getBackground()).getColor();
        }

        //initialize update message TextView
        updateMessage = findViewById(R.id.fragment_below_textview);
        if (gameInProg){
            updateMessage.setText(game.getMessage());
        } else {
            updateMessage.setText(getString(R.string.playGame));
        }

        if (gameInProg) {
        } else {
            toast.setText(getString(R.string.goodLuck));
            toast.show();
            gameInProg = true;
            game = new Game(getCurrentLocation());
        }

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


    }

    /*We made sure to end the async task when the activity is destroyed
     to avoid the static field leak*/
    @SuppressLint("StaticFieldLeak")
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
                                                                                      Log.d(getString(R.string.locationIS), getString(R.string.hereIS) + lat);
                                                                                      lon = location.getLongitude();
                                                                                      Log.d(getString(R.string.locationIS), getString(R.string.hereIS) + lon);
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
                        Log.d(getString(R.string.locationIS), getString(R.string.hereIS) + lat);
                        Log.d(getString(R.string.locationIS), getString(R.string.hereIS) + lon);
                        distanceToDestination = game.calcDistance(new Coordinates("current ", lat, lon));
                        if (distanceToDestination < previousDistance){
                            updateMessage.setText(getString(R.string.Hotter));
                            //background.setBackgroundColor(red);

                            if (currentColor != red){
                                transitionBackground(currentColor, red);
                            }

                            currentColor = red;
                        } else if (distanceToDestination > previousDistance) {
                            updateMessage.setText(getString(R.string.Colder));
                            //background.setBackgroundColor(blue);
                            if (currentColor != blue){
                                transitionBackground(currentColor, blue);
                            }
                            currentColor = blue;
                        }
                        previousDistance = distanceToDestination;
                        foundDestination = game.destinationReached(new Coordinates(getString(R.string.Current), lat, lon));
                        if (foundDestination ){
                            stopwatch.Stop();
                            toast.setText(getString(R.string.Congrats));
                            toast.show();
                            game.updateWin();
                            showImage();
                            addLog();
                            handler.removeCallbacks(runnable);
                        }
                    }
                }
            }, tick);

            return foundDestination;
        }

        private void showImage(){
            GameDestinationFragment gdf = new GameDestinationFragment();
            gdf.setDestination(game.getDestinationCoords().getName());
            //if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, gdf)
                        .commitNow();
           // }
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
        values.put(getString(R.string.ACID), AccountDBHelper.getId());
        values.put(getString(R.string.STEPSCOUNT), steps);
        values.put(getString(R.string.DATE), date.toString());
        values.put(getString(R.string.MAPS), destinationName);
        values.put(getString(R.string.DISTANCECOUNT), dist);
        values.put(getString(R.string.TIMECOUNT), time);
        values.put(getString(R.string.COMPLETED), true);
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
                    Log.d(getString(R.string.locationIS), getString(R.string.hereIS) + lat);
                    lon = location.getLongitude();
                }
            }
        }
        );
        return new Coordinates(getString(R.string.Current), latlong[0], latlong[1]);

    }


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
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
        Log.d(getString(R.string.GameActivity), getString(R.string.onDestroy));
        this.isCancelled = true;

        this.locationUpdateTask.cancel(true);
        boolean success = game.status();
        //if the game has not been won, save the state
        if (!success){
            gameInProg = true;
            AccountDBHelper db = new AccountDBHelper(getApplicationContext());
            int id = db.getId();
            game.setId(id);
            game.setStepCount(steps);
            game.setTime(stopwatch.getSeconds());
            game.setCurrentColor(((ColorDrawable) background.getBackground()).getColor());
            game.setMessage(updateMessage.getText()+"");
        } else {
            gameInProg = false;
            game.setId(-1);
        }
    }
}



