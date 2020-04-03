package com.example.hucklebucklebuckeye;

import java.util.ArrayList;
import java.util.Random;

public class Locations {
    private ArrayList<Coordinates> coordinates;

    public Locations(){
        this.coordinates = new ArrayList<>();
        loadLocations();
    }

    private void loadLocations(){
        //Coordinates found on google maps
        this.coordinates.add(new Coordinates("The Oval", 39.9994979, -83.012709));
        this.coordinates.add(new Coordinates("Thompson Library", 39.9991, -83.0153));
        this.coordinates.add(new Coordinates("Thompson statue", 39.999437, -83.013931));
        this.coordinates.add(new Coordinates("Hansford Quadrangle", 40.001747, -83.012424));
        this.coordinates.add(new Coordinates("Wexner Center For the Arts", 40.000439,  -83.0093536));
        this.coordinates.add(new Coordinates("Hitchcock Hall", 40.0037896, -83.0150181));
        this.coordinates.add(new Coordinates("Ohio Stadium", 40.0016627, -83.0197277));
        this.coordinates.add(new Coordinates("Numbers Garden", 40.0022524, -83.0163104));
        this.coordinates.add(new Coordinates("Dreese Lab", 40.0023226, -83.0159112));
        this.coordinates.add(new Coordinates("Fisher College of Business", 40.0042692, -83.0151708));
        this.coordinates.add(new Coordinates("Mirror Lake", 39.9979975, -83.0142329));
        this.coordinates.add(new Coordinates("Younkin Success Center", 39.9949653, -83.0144281));
        this.coordinates.add(new Coordinates("Tom W. Davis Clock Tower", 40.0049371, -83.012978));
        this.coordinates.add(new Coordinates("RPAC", 39.9994014, -83.0182686));
        this.coordinates.add(new Coordinates("Ohio Union", 39.9976772, -83.0085748));
    }

    public Coordinates setLocation(){
        Random rand = new Random();
        int limit = this.coordinates.size();
        return this.coordinates.get(rand.nextInt(limit));
    }
}
