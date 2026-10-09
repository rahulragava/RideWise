# Requirements

## Functional Requirements

1. **Register Riders** — Add riders with a name and location.
2. **Register Drivers** — Add drivers with a name and current location.
3. **Show Available Drivers** — List all drivers currently not on a ride.
4. **Request a Ride** — A rider requests a ride with a given distance.
5. **Match Ride to Driver** — Assign a driver using a configurable matching strategy.
6. **Calculate Fare** — Compute fare using a configurable pricing strategy.
7. **Track Ride Status** — Rides transition through:
   - `REQUESTED` — Ride created, awaiting driver assignment
   - `ASSIGNED` — Driver matched and assigned
   - `COMPLETED` — Ride finished, fare receipt generated
   - `CANCELLED` — Ride cancelled

## Non-Functional Requirements

- Easily extendable pricing algorithm (new `FareStrategy` implementations)
- Easily swappable driver matching logic (new `RideMatchingStrategy` implementations)
- Low coupling between services
- Maintainable and readable code

## Domain Entities

| Entity | Fields |
|--------|--------|
| **Rider** | id, name, location |
| **Driver** | id, name, currentLocation, available, totalRidesCompleted |
| **Ride** | id, rider, driver, distance, status, fareReceipt |
| **FareReceipt** | rideId, amount, generatedAt |

## Enums

- **RideStatus** — REQUESTED, ASSIGNED, COMPLETED, CANCELLED
- **VehicleType** — BIKE, AUTO, CAR
