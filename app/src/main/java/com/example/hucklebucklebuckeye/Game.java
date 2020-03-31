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

    public Coordinates getDestinationCoords(){
        return destinationLocation;
    };

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

    public static void endGame(){
        //TODO: stop timer
        //TODO: log data
        //nullify location
        destinationLocation = null;
    }

}
