# SOLID Reflection

## Single Responsibility Principle (SRP)

Each class has one reason to change:

- **RiderService** — only handles rider registration and lookup
- **DriverService** — only handles driver registration and availability
- **RideService** — only orchestrates ride lifecycle (request, assign, complete)
- **Strategy classes** — each encapsulates a single algorithm

## Open/Closed Principle (OCP)

The system is open for extension, closed for modification:

- Adding a new matching strategy (e.g., `HighestRatedDriverStrategy`) requires only creating a new class implementing `RideMatchingStrategy` and annotating it with `@Component`. No existing code changes.
- Adding a new fare strategy (e.g., `WeekendFareStrategy`) follows the same pattern with `FareStrategy`.

## Liskov Substitution Principle (LSP)

All strategy implementations are interchangeable:

- Any `RideMatchingStrategy` implementation can replace another — `NearestDriverStrategy` and `LeastActiveDriverStrategy` both accept a rider and driver list, returning a valid driver.
- Any `FareStrategy` implementation can replace another — both return a positive fare amount for a given ride.

## Interface Segregation Principle (ISP)

Interfaces are small and focused:

- `RideMatchingStrategy` has a single method: `findDriver()`
- `FareStrategy` has a single method: `calculateFare()`
- No client is forced to depend on methods it doesn't use.

## Dependency Inversion Principle (DIP)

High-level modules depend on abstractions:

- `RideService` depends on `RideMatchingStrategy` and `FareStrategy` interfaces, not on `NearestDriverStrategy` or `DefaultFareStrategy` directly.
- Spring Boot injects concrete implementations at runtime via constructor injection.
- Strategies can be swapped at runtime through setter methods without rebuilding the service.
