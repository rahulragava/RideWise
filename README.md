# RideWise - Ride Sharing System

A simplified, console-based ride-sharing (Uber/Ola style) system built with **Java Spring Boot**, demonstrating high-quality Low-Level Design (LLD) and design thinking.

## Features

- Register riders and drivers
- Find available drivers nearby
- Request rides with configurable driver matching strategies
- Calculate fares with swappable pricing strategies
- Track ride lifecycle (REQUESTED → ASSIGNED → COMPLETED / CANCELLED)
- Runtime strategy switching via console menu

## Tech Stack

- Java 17
- Spring Boot 3.2.5
- Maven

## Project Structure

```
src/main/java/com/airtribe/ridewise/
├── RideWiseApplication.java          # Spring Boot entry point
├── Main.java                         # Console menu (CommandLineRunner)
├── model/
│   ├── Rider.java                    # Rider entity
│   ├── Driver.java                   # Driver entity
│   ├── Ride.java                     # Ride entity (composes FareReceipt)
│   ├── FareReceipt.java              # Fare receipt (composed in Ride)
│   ├── RideStatus.java               # Enum: REQUESTED, ASSIGNED, COMPLETED, CANCELLED
│   └── VehicleType.java              # Enum: BIKE, AUTO, CAR
├── strategy/
│   ├── RideMatchingStrategy.java     # Interface for driver matching
│   ├── NearestDriverStrategy.java    # Matches closest available driver
│   ├── LeastActiveDriverStrategy.java# Matches driver with fewest rides
│   ├── FareStrategy.java             # Interface for fare calculation
│   ├── DefaultFareStrategy.java      # Base fare + per-km rate
│   └── PeakHourFareStrategy.java     # 1.5x surge multiplier
├── service/
│   ├── RiderService.java             # Rider registration and lookup
│   ├── DriverService.java            # Driver registration and availability
│   └── RideService.java              # Ride orchestration with injected strategies
├── exception/
│   └── NoDriverAvailableException.java
└── util/
    └── IdGenerator.java              # Thread-safe ID generation
```

## Design Patterns & Principles

| Principle | How It's Applied |
|-----------|-----------------|
| **Strategy Pattern** | `RideMatchingStrategy` and `FareStrategy` interfaces with swappable implementations |
| **Composition over Inheritance** | `Ride` composes `FareReceipt`; `RideService` composes strategies |
| **SRP** | Each service handles a single domain concern |
| **OCP** | New strategies are added as `@Component` beans without modifying existing code |
| **LSP** | All strategy implementations are interchangeable |
| **ISP** | Small, focused interfaces (`RideMatchingStrategy`, `FareStrategy`) |
| **DIP** | Services depend on interfaces, injected by Spring |
| **Law of Demeter** | Services communicate with direct collaborators only |

## How to Run

### Prerequisites

- Java 17 or higher
- Maven 3.8+

### Build and Run

```bash
mvn clean compile
mvn spring-boot:run
```

### Console Menu

```
--- Main Menu ---
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
7. Change Ride Matching Strategy
8. Change Fare Strategy
9. Exit
```

## Docs

- [Requirements](docs/Requirements.md)
- [Class Model](docs/Class_Model.md)
- [SOLID Reflection](docs/SOLID_Reflection.md)
- [Object Relationships](docs/Object_Relationships.md)
