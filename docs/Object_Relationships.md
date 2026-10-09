# Object Relationships

## Relationship Summary

| Relationship | Type | Description |
|-------------|------|-------------|
| Rider → Ride | Association | A rider is associated with rides they request. Rider exists independently of any ride. |
| Driver → Ride | Association | A driver is associated with rides they are assigned to. Driver exists independently of any ride. |
| Ride → FareReceipt | Composition | A FareReceipt is created as part of a Ride and does not exist independently. When a ride is completed, the receipt is generated and owned by that ride. |
| RideService → RideMatchingStrategy | Composition | RideService holds a strategy reference injected at construction. The strategy is a collaborator owned by the service. |
| RideService → FareStrategy | Composition | Same as above — RideService composes a fare calculation strategy. |

## Why Composition Over Inheritance

- **Ride does not extend FareReceipt** — instead, Ride *has* a FareReceipt. This allows the receipt to be created only when the ride is completed, and keeps fare logic separate from ride lifecycle.
- **RideService does not extend any strategy** — it *composes* both a matching strategy and a fare strategy. This allows runtime swapping without subclassing.
- **No deep class hierarchies** — the design is flat and flexible. New behavior is added through new interface implementations, not by extending existing classes.

## Law of Demeter

Services follow the principle of least knowledge:

- `RideService.requestRide()` calls `matchingStrategy.findDriver()` directly — it does not reach into the strategy's internals.
- `Main` (the console runner) interacts only with the three services — it never directly manipulates model internals or strategy logic.
- `Ride.assignDriver()` encapsulates the driver assignment and status change — callers don't set fields individually.
