# Class Model

## Model Layer (`com.airtribe.ridewise.model`)

### Rider
- `id: String` (auto-generated)
- `name: String`
- `location: double`

### Driver
- `id: String` (auto-generated)
- `name: String`
- `currentLocation: double`
- `available: boolean`
- `totalRidesCompleted: int`

### Ride
- `id: String` (auto-generated)
- `rider: Rider`
- `driver: Driver`
- `distance: double`
- `status: RideStatus`
- `fareReceipt: FareReceipt` (composition)

### FareReceipt
- `rideId: String`
- `amount: double`
- `generatedAt: LocalDateTime`

## Strategy Layer (`com.airtribe.ridewise.strategy`)

### RideMatchingStrategy (interface)
- `findDriver(Rider rider, List<Driver> drivers): Driver`

| Implementation | Logic |
|----------------|-------|
| `NearestDriverStrategy` | Picks the available driver closest to the rider's location |
| `LeastActiveDriverStrategy` | Picks the available driver with the fewest completed rides |

### FareStrategy (interface)
- `calculateFare(Ride ride): double`

| Implementation | Formula |
|----------------|---------|
| `DefaultFareStrategy` | `50 + (distance × 10)` |
| `PeakHourFareStrategy` | `(50 + (distance × 10)) × 1.5` |

## Service Layer (`com.airtribe.ridewise.service`)

### RiderService
- `registerRider(name, location): Rider`
- `getRiderById(id): Optional<Rider>`
- `getAllRiders(): List<Rider>`

### DriverService
- `registerDriver(name, location): Driver`
- `getDriverById(id): Optional<Driver>`
- `getAvailableDrivers(): List<Driver>`
- `getAllDrivers(): List<Driver>`
- `updateAvailability(driverId, available): void`

### RideService
- Constructor injection: `RideMatchingStrategy`, `FareStrategy`
- `requestRide(rider, distance, availableDrivers): Ride`
- `completeRide(rideId): Ride`
- `getRideById(rideId): Optional<Ride>`
- `getAllRides(): List<Ride>`
- `setMatchingStrategy(strategy): void`
- `setFareStrategy(strategy): void`
