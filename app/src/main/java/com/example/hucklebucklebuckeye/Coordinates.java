package com.example.hucklebucklebuckeye;

public class Coordinates{
    private String name;
    private double latitude;
    private double longitude;

    public Coordinates(String name, double latitude, double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public double getLat(){
        return this.latitude;
    }
    public double getLon(){
        return this.longitude;
    }
    public String getName(){
        return this.name;
    }
}
