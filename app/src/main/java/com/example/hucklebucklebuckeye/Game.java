package com.example.hucklebucklebuckeye;

import android.util.Log;

public class Game {

    private static Coordinates destinationLocation;
    //TODO: this is about 100 feet from the location. we might want to tweak this when we're further along
    final private static double CLOSE_ENOUGH_LATITUDE = 0.00002756515 * 10;
    final private static double CLOSE_ENOUGH_LONGITUDE = 0.00002738485 * 10;
    //TODO: most of dis
    //randomly select a location
    //start a timer

    //track path: might be handled in UI
        //maintain values in static variables so they can be retained if activity is destroyed
    //update warmer/colder
        //create benchmarks to define what is "warm, what is "cold", what is "goal"

    //when goal is reached, display image and message
        //display final path?
        //stop timer
        //add log to db
        //display play again option

    public Game(){
        Locations locations = new Locations();
        destinationLocation = locations.setLocation();
    }

    public static void logLocation(){
        Log.d("GAME location name: ", destinationLocation.getName());
        Log.d("GAME latitude: ", destinationLocation.getLat()+"");
        Log.d("GAME longitude: ", destinationLocation.getLon()+"");
    }

    //TODO: figure out where to use this. I think it needs to be called continuously from MapFragment or GameActivity
    public static boolean destinationReached (Coordinates currentLocation){
        boolean destinationReached = false;
        double latitudeDifference = Math.abs(currentLocation.getLat() - destinationLocation.getLat());
        double longitudeDifference = Math.abs(currentLocation.getLon() - destinationLocation.getLon());

        Log.d("tagtagtag lat", latitudeDifference+"");
        Log.d("tagtagtag lon", longitudeDifference+"");

        //TODO: this is about 30 feet from the single coordinate. We might want to adjust this a bit
        if (latitudeDifference < (CLOSE_ENOUGH_LATITUDE) || longitudeDifference < (CLOSE_ENOUGH_LONGITUDE)){
            destinationReached = true;
        }
        return destinationReached;

    }
    public static void endGame(){
        //TODO: stop timer
        //TODO: log data
        //nullify location
        destinationLocation = null;
    }

}
