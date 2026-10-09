package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("leastActiveDriverStrategy")
public class LeastActiveDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        return drivers.stream()
                .filter(Driver::isAvailable)
                .min(Comparator.comparingInt(Driver::getTotalRidesCompleted))
                .orElseThrow(() -> new NoDriverAvailableException("No available driver found."));
    }
}
