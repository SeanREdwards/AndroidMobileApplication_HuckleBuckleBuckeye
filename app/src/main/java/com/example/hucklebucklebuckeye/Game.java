/*
*Game.java
*Class to instantiate a single game session necessary for app functionality and to store game session data.
*@Author Sean Edwards
* @Version 20200306
*/
package com.example.hucklebucklebuckeye;

import android.util.Log;

public class Game {

    private static Coordinates destinationLocation;
    private static Coordinates currentLocation;
    private static int stepCount;
    private static int currentColor;
    private boolean isWon;
    private int time;
    private static String message;

    private static int id;
    final private static double CLOSE_ENOUGH_LATITUDE = 0.00002756515 * 3;
    final private static double CLOSE_ENOUGH_LONGITUDE = 0.00002738485 * 3;

    public Game(Coordinates initial){
        Locations locations = new Locations();
        destinationLocation = locations.setLocation();
        isWon = false;
        time = 0;
        stepCount = 0;
        currentColor = 0;
        id = -1;
        message = "";
    }
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Game.id = id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        Game.message = message;
    }
    public void setStepCount(int steps){
        stepCount = steps;
    }

    public int getStepCount(){
        return stepCount;
    }
    public int getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(int currentColor) {
        Game.currentColor = currentColor;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public void updateWin(){
        isWon = true;
    }

    public boolean status(){
        return isWon;
    }

    public static void logLocation(){
        Log.d("GAME location name: ", destinationLocation.getName());
        Log.d("GAME latitude: ", destinationLocation.getLat()+"");
        Log.d("GAME longitude: ", destinationLocation.getLon()+"");
    }

    public Coordinates getDestinationCoords(){
        return destinationLocation;
    }

    public static boolean destinationReached (Coordinates currentLocation){
        boolean destinationReached = false;
        double latitudeDifference = Math.abs(currentLocation.getLat() - destinationLocation.getLat());
        double longitudeDifference = Math.abs(currentLocation.getLon() - destinationLocation.getLon());

        if (latitudeDifference < (CLOSE_ENOUGH_LATITUDE) || longitudeDifference < (CLOSE_ENOUGH_LONGITUDE)){
            destinationReached = true;
        }
        return destinationReached;
    }

/*Method to obtain the distance between two locations based on the Haversine formula:
*       a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlon/2))^2
*       c = 2 * atan2( sqrt(a), sqrt(1-a) )
*       d = R * c (where R is the radius of the Earth)
* @Requires double srcLat
*   Latitude of starting point.
* @Requires double srcLon
*   Longitude of starting point.
* @Requires double destLat
*   Latitude of end point.
* @Requires double destLon
*   Longitude of end point.
* @Returns the distance between starting and ending points.*/
    public double calcDistance(Coordinates currentLocation){
        /*r value obtained by taking the diameter of the Earth in miles and
        dividing by 2 to get the radius. Diameter of earth per NASA's fact sheet:
        https://nssdc.gsfc.nasa.gov/planetary/factsheet/planet_table_british.html*/
        double r = 3958;
        double ftPerMile = 5280;

        double lat1 = Math.toRadians(currentLocation.getLat());
        double lat2 = Math.toRadians(destinationLocation.getLat());
        double lon1 = Math.toRadians(currentLocation.getLon());
        double lon2 = Math.toRadians(destinationLocation.getLon());

        //Get delta values for both latitudes and longitude.
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        //Haversine formula calculation:
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2),2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return ((r * c) * ftPerMile);
    }

    public static double stepsToFeet(double steps){
        return steps * 2.5;
    }

    public static void endGame(){
        //TODO: stop timer
        //nullify location
        destinationLocation = null;
    }

}
