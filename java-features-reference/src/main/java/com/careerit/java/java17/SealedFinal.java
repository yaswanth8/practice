package com.careerit.java.java17;

/**
 * Java 17 - Sealed classes are now a permanent feature (JEP 409).
 *
 * Permitted types must be in the same module (or same package if the class is
 * in the unnamed module). Use `final`, `sealed`, or `non-sealed` on each
 * direct subtype to continue (or close) the hierarchy.
 */
public class SealedFinal {

    sealed interface Vehicle permits Car, Truck, Bike {}
    final class Car implements Vehicle {}
    non-sealed class Truck implements Vehicle {} // anyone may further extend Truck
    sealed class Bike implements Vehicle permits ElectricBike {}
    final class ElectricBike extends Bike {}

    public static void main(String[] args) {
        Vehicle v = new SealedFinal().new Car();
        System.out.println("vehicle kind = " + describe(v));
    }

    static String describe(Vehicle v) {
        return switch (v) {
            case Car c          -> "car";
            case Truck t        -> "truck";
            case ElectricBike e -> "e-bike";
            case Bike b         -> "bike"; // non-ElectricBike subclasses of Bike
        };
    }
}
