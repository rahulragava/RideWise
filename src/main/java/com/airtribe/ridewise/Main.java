package com.airtribe.ridewise;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final RiderService riderService;
    private final DriverService driverService;
    private final RideService rideService;
    private final Map<String, RideMatchingStrategy> matchingStrategies;
    private final Map<String, FareStrategy> fareStrategies;

    public Main(RiderService riderService,
                DriverService driverService,
                RideService rideService,
                Map<String, RideMatchingStrategy> matchingStrategies,
                Map<String, FareStrategy> fareStrategies) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.rideService = rideService;
        this.matchingStrategies = matchingStrategies;
        this.fareStrategies = fareStrategies;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("   Welcome to RideWise Ride-Sharing!");
        System.out.println("========================================");

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addRider(scanner);
                case "2" -> addDriver(scanner);
                case "3" -> viewAvailableDrivers();
                case "4" -> requestRide(scanner);
                case "5" -> completeRide(scanner);
                case "6" -> viewRides();
                case "7" -> changeMatchingStrategy(scanner);
                case "8" -> changeFareStrategy(scanner);
                case "9" -> {
                    System.out.println("Thank you for using RideWise. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Change Ride Matching Strategy");
        System.out.println("8. Change Fare Strategy");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addRider(Scanner scanner) {
        System.out.print("Enter rider name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter rider location (numeric): ");
        double location;
        try {
            location = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid location. Please enter a number.");
            return;
        }

        Rider rider = riderService.registerRider(name, location);
        System.out.println("Rider registered: " + rider);
    }

    private void addDriver(Scanner scanner) {
        System.out.print("Enter driver name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter driver location (numeric): ");
        double location;
        try {
            location = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid location. Please enter a number.");
            return;
        }

        Driver driver = driverService.registerDriver(name, location);
        System.out.println("Driver registered: " + driver);
    }

    private void viewAvailableDrivers() {
        List<Driver> available = driverService.getAvailableDrivers();
        if (available.isEmpty()) {
            System.out.println("No available drivers at the moment.");
            return;
        }
        System.out.println("\n--- Available Drivers ---");
        available.forEach(System.out::println);
    }

    private void requestRide(Scanner scanner) {
        List<Rider> riders = riderService.getAllRiders();
        if (riders.isEmpty()) {
            System.out.println("No riders registered. Please add a rider first.");
            return;
        }

        System.out.println("\n--- Registered Riders ---");
        riders.forEach(System.out::println);
        System.out.print("Enter rider ID: ");
        String riderId = scanner.nextLine().trim();

        Rider rider = riderService.getRiderById(riderId).orElse(null);
        if (rider == null) {
            System.out.println("Rider not found.");
            return;
        }

        System.out.print("Enter ride distance (km): ");
        double distance;
        try {
            distance = Double.parseDouble(scanner.nextLine().trim());
            if (distance <= 0) {
                System.out.println("Distance must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid distance. Please enter a number.");
            return;
        }

        try {
            List<Driver> availableDrivers = driverService.getAvailableDrivers();
            Ride ride = rideService.requestRide(rider, distance, availableDrivers);
            System.out.println("Ride requested successfully: " + ride);
        } catch (NoDriverAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void completeRide(Scanner scanner) {
        List<Ride> rides = rideService.getAllRides();
        if (rides.isEmpty()) {
            System.out.println("No rides to complete.");
            return;
        }

        System.out.println("\n--- All Rides ---");
        rides.forEach(System.out::println);
        System.out.print("Enter ride ID to complete: ");
        String rideId = scanner.nextLine().trim();

        try {
            Ride ride = rideService.completeRide(rideId);
            System.out.println("Ride completed: " + ride);
            System.out.println("Fare receipt: " + ride.getFareReceipt());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewRides() {
        List<Ride> rides = rideService.getAllRides();
        if (rides.isEmpty()) {
            System.out.println("No rides recorded yet.");
            return;
        }
        System.out.println("\n--- All Rides ---");
        rides.forEach(System.out::println);
    }

    private void changeMatchingStrategy(Scanner scanner) {
        System.out.println("\nAvailable matching strategies:");
        System.out.println("1. Nearest Driver");
        System.out.println("2. Least Active Driver");
        System.out.print("Choose strategy: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                rideService.setMatchingStrategy(matchingStrategies.get("nearestDriverStrategy"));
                System.out.println("Strategy changed to: Nearest Driver");
            }
            case "2" -> {
                rideService.setMatchingStrategy(matchingStrategies.get("leastActiveDriverStrategy"));
                System.out.println("Strategy changed to: Least Active Driver");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void changeFareStrategy(Scanner scanner) {
        System.out.println("\nAvailable fare strategies:");
        System.out.println("1. Default Fare");
        System.out.println("2. Peak Hour Fare (1.5x surge)");
        System.out.print("Choose strategy: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                rideService.setFareStrategy(fareStrategies.get("defaultFareStrategy"));
                System.out.println("Fare strategy changed to: Default");
            }
            case "2" -> {
                rideService.setFareStrategy(fareStrategies.get("peakHourFareStrategy"));
                System.out.println("Fare strategy changed to: Peak Hour");
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
