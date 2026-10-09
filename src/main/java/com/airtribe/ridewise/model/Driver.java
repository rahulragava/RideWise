package com.airtribe.ridewise.model;

import com.airtribe.ridewise.util.IdGenerator;

public class Driver {

    private final String id;
    private String name;
    private double currentLocation;
    private boolean available;
    private int totalRidesCompleted;

    public Driver(String name, double currentLocation) {
        this.id = IdGenerator.generate("DRIVER");
        this.name = name;
        this.currentLocation = currentLocation;
        this.available = true;
        this.totalRidesCompleted = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(double currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTotalRidesCompleted() {
        return totalRidesCompleted;
    }

    public void incrementRidesCompleted() {
        this.totalRidesCompleted++;
    }

    @Override
    public String toString() {
        return "Driver{id='" + id + "', name='" + name + "', location=" + currentLocation
                + ", available=" + available + ", ridesCompleted=" + totalRidesCompleted + "}";
    }
}
