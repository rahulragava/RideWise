package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("nearestDriverStrategy")
public class NearestDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        return drivers.stream()
                .filter(Driver::isAvailable)
                .min((d1, d2) -> {
                    double dist1 = Math.abs(d1.getCurrentLocation() - rider.getLocation());
                    double dist2 = Math.abs(d2.getCurrentLocation() - rider.getLocation());
                    return Double.compare(dist1, dist2);
                })
                .orElseThrow(() -> new NoDriverAvailableException("No available driver nearby."));
    }
}
