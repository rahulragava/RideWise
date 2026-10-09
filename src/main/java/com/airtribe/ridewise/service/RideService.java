package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final List<Ride> rides = new ArrayList<>();
    private RideMatchingStrategy matchingStrategy;
    private FareStrategy fareStrategy;

    public RideService(RideMatchingStrategy matchingStrategy, FareStrategy fareStrategy) {
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public void setMatchingStrategy(RideMatchingStrategy matchingStrategy) {
        this.matchingStrategy = matchingStrategy;
    }

    public void setFareStrategy(FareStrategy fareStrategy) {
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(Rider rider, double distance, List<Driver> availableDrivers) {
        Ride ride = new Ride(rider, distance);
        Driver driver = matchingStrategy.findDriver(rider, availableDrivers);
        ride.assignDriver(driver);
        driver.setAvailable(false);
        rides.add(ride);
        return ride;
    }

    public Ride completeRide(String rideId) {
        Ride ride = getRideById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found: " + rideId));

        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride cannot be completed. Current status: " + ride.getStatus());
        }

        double fare = fareStrategy.calculateFare(ride);
        ride.setFareReceipt(new FareReceipt(ride.getId(), fare));
        ride.setStatus(RideStatus.COMPLETED);
        ride.getDriver().setAvailable(true);
        ride.getDriver().incrementRidesCompleted();
        return ride;
    }

    public Optional<Ride> getRideById(String rideId) {
        return rides.stream()
                .filter(r -> r.getId().equals(rideId))
                .findFirst();
    }

    public List<Ride> getAllRides() {
        return Collections.unmodifiableList(rides);
    }
}
