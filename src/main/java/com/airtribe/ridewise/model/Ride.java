package com.airtribe.ridewise.model;

import com.airtribe.ridewise.util.IdGenerator;

public class Ride {

    private final String id;
    private final Rider rider;
    private Driver driver;
    private final double distance;
    private RideStatus status;
    private FareReceipt fareReceipt;

    public Ride(Rider rider, double distance) {
        this.id = IdGenerator.generate("RIDE");
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
    }

    public String getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void assignDriver(Driver driver) {
        this.driver = driver;
        this.status = RideStatus.ASSIGNED;
    }

    public double getDistance() {
        return distance;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public FareReceipt getFareReceipt() {
        return fareReceipt;
    }

    public void setFareReceipt(FareReceipt fareReceipt) {
        this.fareReceipt = fareReceipt;
    }

    @Override
    public String toString() {
        String driverInfo = (driver != null) ? driver.getName() : "N/A";
        String fareInfo = (fareReceipt != null) ? String.format("%.2f", fareReceipt.getAmount()) : "N/A";
        return "Ride{id='" + id + "', rider='" + rider.getName() + "', driver='" + driverInfo
                + "', distance=" + distance + "km, status=" + status + ", fare=" + fareInfo + "}";
    }
}
