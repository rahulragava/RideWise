package com.airtribe.ridewise.model;

import com.airtribe.ridewise.util.IdGenerator;

public class Rider {

    private final String id;
    private String name;
    private double location;

    public Rider(String name, double location) {
        this.id = IdGenerator.generate("RIDER");
        this.name = name;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(double location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Rider{id='" + id + "', name='" + name + "', location=" + location + "}";
    }
}
