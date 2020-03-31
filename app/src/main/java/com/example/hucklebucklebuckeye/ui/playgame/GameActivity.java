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
        Location destination;
        Location currentLocation;

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
            handler.postDelayed( runnable = new Runnable() {
                public void run() {
                    //do something
                    s = "AM I REPEATING?";
                    handler.postDelayed(runnable, tick);
                    toast.setText(s);
                    if (running ){
                        toast.setText("I am still repeating!");
                        count ++;
                        if (count == 5){
                            running = false;
                        }
                    } else{
                        toast.setText("I will no longer repeat!");
                        handler.removeCallbacks(runnable);
                    }
                    toast.show();
                }
            }, tick);

            /*Location Checker Alogrithm:
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

    //    @SuppressWarnings("MissingPermission")
//    private void getLastLocation(){
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                        new OnCompleteListener<Location>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Location> task) {
//                                Location location = task.getResult();
//                                if (location == null) {
//                                    requestNewLocationData();
//                                } else {
//                                    //Game game = new Game();
//
//                                    //Log.d("latitude: ", location.getLatitude()+"");
//                                    //Log.d("longitude: ", location.getLongitude()+"");
//                                }
//                            }
//                        }
//                );
//            } else {
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        } else {
//            requestPermissions();
//        }
//    }
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

    /*Method to obtain the distance between two locations based on the Haversine formula:
        a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
        c = 2 * atan2( sqrt(a), sqrt(1-a) )
        d = R * c (where R is the radius of the Earth)
    * @Requires double srcLat
    *   Latitude of starting point.
    * @Requires double srcLon
    *   Longitude of starting point.
    * @Requires double destLat
    *   Latitude of end point.
    * @Requires double destLon
    *   Longitude of end point.
    * @Returns the distance between starting and ending points.
    * */
    private double calcDistance(double srcLat, double srcLon, double destLat, double destLon){

        /*r value obtained by taking the diameter of the Earth in miles and
        dividing by 2 to get the radius. Diameter of earth per NASA's fact sheet:
        https://nssdc.gsfc.nasa.gov/planetary/factsheet/planet_table_british.html*/
        double r = 3958;

        double lat1 = Math.toRadians(srcLat);
        double lat2 = Math.toRadians(destLat);
        double lon1 = Math.toRadians(srcLon);
        double lon2 = Math.toRadians(destLon);

        //Get delta values for both latitudes and longitude.
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        //Haversine formula calculation:
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2),2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (r * c);
    }

}