package com.example.hucklebucklebuckeye.ui.playgame;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hucklebucklebuckeye.Coordinates;
import com.example.hucklebucklebuckeye.Game;
import com.example.hucklebucklebuckeye.R;
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
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class GameActivity extends AppCompatActivity {
    Toast toast;
    String s = "";
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Game game = new Game();
        Log.d("TEST", "onCreate: Line before Async task");
        toast = Toast.makeText(this, "Starting game!", Toast.LENGTH_SHORT);
        AsyncTask<Game, String, String> testTask = new LocationUpdateTask();
        testTask.execute(game);
        Log.d("TEST", this.s);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapFragment.newInstance())
                    .commitNow();
        }
        //toast.show();
    }

    private class LocationUpdateTask extends AsyncTask<Game, String, String> {
        private Handler handler;
        int tick;
        Runnable runnable;
        private boolean running;
        private int count;
        Coordinates destination;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler = new Handler();
            tick = 5000;
            running = true;
            count = 0;
        }

        @Override
        protected String doInBackground(Game... games) {

            Game game = games[0];
            destination = game.getDestinationCoords();

            handler.postDelayed( runnable = new Runnable() {
                public void run() {
                    handler.postDelayed(runnable, tick);
                    if (running ){
                        toast.setText("Coordinates of Destination: (" + destination.getLat() + ", " + destination.getLon());
                        count ++;
                        if (count == 3){
                            running = false;
                        }
                    } else{
                        toast.setText("I will no longer repeat!");
                        handler.removeCallbacks(runnable);
                    }
                    toast.show();
                }
            }, tick);

            /*Location Checker Algorithm:
                Coordinates startLocation = start location;
                Coordinates currentLocation = start location;
                Coordinates destination = end location;
                    //Tick
                UpdateCurrentLocation(currentLocation);

                getCurrentCoordinates;



             */

//            Coordinates current = getCurrentLocation();
//            if (game.destinationReached(current)) {
//                s = "Congrats - you're here!";
//            } else {
//                s = "Oops - too far";
//            }
            return s;
        }

        @Override
        protected void onProgressUpdate(String... s) {


        }

        @Override
        protected void onPostExecute(String result) {
            //toast.setText(result);

            /*for testing shouldn't make changes to UI*/
            //toast.show();

        }
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
                    latlong[0] = location.getLatitude();
                    latlong[1] = location.getLongitude();
                    //Log.d("latitude: ", location.getLatitude()+"");
                    //Log.d("longitude: ", location.getLongitude()+"");
                }
            }
        }
        );
        return new Coordinates("current", latlong[0], latlong[1]);

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
}