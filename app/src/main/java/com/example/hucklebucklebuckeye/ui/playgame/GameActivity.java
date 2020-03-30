package com.example.hucklebucklebuckeye.ui.playgame;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
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
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        //TODO: request location on mainmenu screen and disable playgame if permission not granted

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MapFragment.newInstance())
                    .commitNow();
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

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Game game = new Game();
                                    game.logLocation();
                                    Coordinates current = new Coordinates("Current", location.getLatitude(), location.getLongitude());
                                    if (game.destinationReached(current)){
                                        //TODO: probably put this in a thread and continuously make this check
                                        Toast.makeText(getApplicationContext(), "Congrats - you're here!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Oops - too far", Toast.LENGTH_LONG).show();
                                    }

                                    Log.d("latitude: ", location.getLatitude()+"");
                                    Log.d("longitude: ", location.getLongitude()+"");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
/*
*
* this was here to return the current coordinates. we might want to use it later
 */
//    public Coordinates getCurrentLocation(){
//        final double[] latlong = new double[2];
//        mFusedLocationClient.getLastLocation().addOnCompleteListener( new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location = task.getResult();
//                if (location == null) {
//                    requestNewLocationData();
//                } else {
//                    Game game = new Game();
//                    game.logLocation();
//                    Coordinates current = new Coordinates("Current", location.getLatitude(), location.getLongitude());
//                    if (!game.continueGame(current)){
//                        //TODO: probably put this in a thread and continuously make this check
//                        Toast.makeText(getApplicationContext(), "Congrats - you're here!", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Oops - too far", Toast.LENGTH_LONG).show();
//                    }
//                    latlong[0] = location.getLatitude();
//                    latlong[1] = location.getLongitude();
//                    Log.d("latitude: ", location.getLatitude()+"");
//                    Log.d("longitude: ", location.getLongitude()+"");
//                }
//            }
//        }
//        );
//       return new Coordinates("current", latlong[0], latlong[1]);
//
//    }
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
            Log.d("latitude: ", mLastLocation.getLatitude()+"");
            Log.d("longitude: ", mLastLocation.getLongitude()+"");
        }
    };

}