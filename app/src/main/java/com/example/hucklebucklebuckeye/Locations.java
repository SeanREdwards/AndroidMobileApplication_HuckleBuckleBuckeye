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
        this.coordinates.add(new Coordinates("Natalie's House", 40.949946, -81.477748));
        this.coordinates.add(new Coordinates("Apartment", 40.025423, -83.031916));
    }

    public Coordinates setLocation(){
        Random rand = new Random();
        int limit = this.coordinates.size();
        return this.coordinates.get(rand.nextInt(limit));
    }
}
