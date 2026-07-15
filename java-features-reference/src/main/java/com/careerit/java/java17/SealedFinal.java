package com.careerit.java.java17;

/**
 * ============================================================================
 *  Java 17 : SEALED CLASSES (permanent - JEP 409)
 * ============================================================================
 *
 *  WHY SEALED?
 *  -----------
 *  Normal inheritance is open: anyone anywhere can extend your class.
 *  `sealed` lets an author say "these are the ONLY allowed direct subtypes."
 *  That enables:
 *    - safe, exhaustive switch (no default needed)
 *    - algebraic data types (records + sealed = ADTs)
 *    - better modelling of closed hierarchies (Result = Ok | Err)
 *
 *  SYNTAX
 *  ------
 *   sealed interface Shape permits Circle, Square, Triangle { }
 *
 *  Every permitted subtype MUST be declared as exactly one of:
 *    - final        : no further extension
 *    - sealed       : continues the restriction with its own permits clause
 *    - non-sealed   : opens the hierarchy back up from this point down
 *
 *  Permitted types must live in the same module (or same package for unnamed module).
 * ============================================================================
 */
public class SealedFinal {

    // ---------------------------------------------------------------
    // Example 1: records + sealed = algebraic data type (ADT)
    // Exhaustive switch needs no default.
    // ---------------------------------------------------------------
    sealed interface Shape permits Circle, Square, Triangle {}
    record Circle(double r)                    implements Shape {}
    record Square(double side)                 implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    static double area(Shape s) {
        return switch (s) {
            case Circle c   -> Math.PI * c.r() * c.r();
            case Square sq  -> sq.side() * sq.side();
            case Triangle t -> 0.5 * t.base() * t.height();
            // No default - compiler verifies all subtypes are covered.
        };
    }

    // ---------------------------------------------------------------
    // Example 2: final / sealed / non-sealed working together
    // ---------------------------------------------------------------
    sealed interface Vehicle permits Car, Truck, Bike {}
    final class Car implements Vehicle {}
    non-sealed class Truck implements Vehicle {}   // anyone may subclass Truck further
    sealed class Bike implements Vehicle permits ElectricBike, PedalBike {}
    final class ElectricBike extends Bike {}
    final class PedalBike extends Bike {}

    static String describe(Vehicle v) {
        return switch (v) {
            case Car c          -> "car";
            case Truck t        -> "truck";
            case ElectricBike e -> "e-bike";
            case PedalBike p    -> "pedal-bike";
            case Bike b         -> "bike";  // catches future subclasses of non-sealed? NO, Bike is sealed
        };
    }

    // ---------------------------------------------------------------
    // Example 3: Result type - a tiny "Either" / "Result"
    // Common ADT used for explicit success/failure without exceptions.
    // ---------------------------------------------------------------
    sealed interface Result<T> permits Ok, Err {}
    record Ok<T>(T value)           implements Result<T> {}
    record Err<T>(String message)   implements Result<T> {}

    static Result<Integer> safeParse(String s) {
        try {
            return new Ok<>(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new Err<>("invalid int: " + s);
        }
    }

    static String describeResult(Result<Integer> r) {
        return switch (r) {
            case Ok<Integer> ok    -> "ok=" + ok.value();
            case Err<Integer> err  -> "err=" + err.message();
        };
    }

    public static void main(String[] args) {
        System.out.println("[1] circle area  = " + area(new Circle(2)));
        System.out.println("[1] square area  = " + area(new Square(3)));
        System.out.println("[1] triangle area= " + area(new Triangle(4, 5)));

        SealedFinal outer = new SealedFinal();
        Vehicle v = outer.new Car();
        System.out.println("[2] vehicle = " + describe(v));
        v = outer.new ElectricBike();
        System.out.println("[2] vehicle = " + describe(v));

        System.out.println("[3] " + describeResult(safeParse("42")));
        System.out.println("[3] " + describeResult(safeParse("oops")));
    }
}
