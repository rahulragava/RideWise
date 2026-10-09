package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final List<Driver> drivers = new ArrayList<>();

    public Driver registerDriver(String name, double location) {
        Driver driver = new Driver(name, location);
        drivers.add(driver);
        return driver;
    }

    public Optional<Driver> getDriverById(String id) {
        return drivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.stream()
                .filter(Driver::isAvailable)
                .toList();
    }

    public List<Driver> getAllDrivers() {
        return Collections.unmodifiableList(drivers);
    }

    public void updateAvailability(String driverId, boolean available) {
        getDriverById(driverId).ifPresent(d -> d.setAvailable(available));
    }
}
