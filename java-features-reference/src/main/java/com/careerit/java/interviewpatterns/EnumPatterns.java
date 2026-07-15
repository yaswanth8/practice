package com.careerit.java.interviewpatterns;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.function.DoubleUnaryOperator;

/**
 * ============================================================================
 *  ENUMS - much more than a list of constants
 * ============================================================================
 *
 *  Things enums can do that people forget:
 *   1. Have fields, constructors, and methods.
 *   2. Implement interfaces.
 *   3. Override behaviour PER constant.
 *   4. Be used as super-efficient map keys (EnumMap) and sets (EnumSet).
 *   5. Replace tangled if/else-if switches with polymorphic dispatch.
 *   6. Serve as ready-made thread-safe singletons.
 * ============================================================================
 */
public class EnumPatterns {

    // ---------------------------------------------------------------
    // 1. Enum with fields, constructor, method
    // ---------------------------------------------------------------
    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        EARTH  (5.976e+24, 6.37814e6),
        JUPITER(1.9e+27,   7.1492e7);

        private final double mass;   // kg
        private final double radius; // m
        Planet(double mass, double radius) {
            this.mass   = mass;
            this.radius = radius;
        }
        private static final double G = 6.67300E-11;
        double surfaceGravity() { return G * mass / (radius * radius); }
    }

    // ---------------------------------------------------------------
    // 2. Per-constant behaviour (best replacement for switch)
    // ---------------------------------------------------------------
    enum Operation {
        ADD      { public double apply(double a, double b) { return a + b; } },
        SUBTRACT { public double apply(double a, double b) { return a - b; } },
        MULTIPLY { public double apply(double a, double b) { return a * b; } },
        DIVIDE   { public double apply(double a, double b) { return a / b; } };

        public abstract double apply(double a, double b);
    }

    // ---------------------------------------------------------------
    // 3. Same idea with a lambda field - shorter for one-liners
    // ---------------------------------------------------------------
    enum Discount {
        NONE   (amt -> amt),
        FLAT_10(amt -> amt - 10),
        PCT_20 (amt -> amt * 0.80);

        private final DoubleUnaryOperator fn;
        Discount(DoubleUnaryOperator fn) { this.fn = fn; }
        public double apply(double amount) { return fn.applyAsDouble(amount); }
    }

    // ---------------------------------------------------------------
    // 4. Enum implementing an interface + strategy pattern
    // ---------------------------------------------------------------
    interface Notifier { void notify(String msg); }
    enum Channel implements Notifier {
        EMAIL { public void notify(String msg) { System.out.println("email: " + msg); } },
        SMS   { public void notify(String msg) { System.out.println("sms:   " + msg); } },
        PUSH  { public void notify(String msg) { System.out.println("push:  " + msg); } };
    }

    // ---------------------------------------------------------------
    // 5. Enum singleton - the safest way to do a singleton in Java
    // ---------------------------------------------------------------
    enum Config {
        INSTANCE;
        private String env = "prod";
        public String env()          { return env; }
        public void setEnv(String e) { env = e; }
    }

    // ---------------------------------------------------------------
    // 6. EnumSet / EnumMap - very fast, memory-efficient
    // ---------------------------------------------------------------
    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    public static void main(String[] args) {

        // 1. Fields + method
        for (Planet p : Planet.values()) {
            System.out.printf("[1] %-8s gravity=%.2f m/s^2%n", p, p.surfaceGravity());
        }

        // 2. Per-constant abstract method - no switch anywhere
        double a = 12, b = 4;
        for (Operation op : Operation.values()) {
            System.out.println("[2] " + op + " -> " + op.apply(a, b));
        }

        // 3. Lambda-in-constant style
        System.out.println("[3] flat_10 on 100 = " + Discount.FLAT_10.apply(100));
        System.out.println("[3] pct_20 on 100  = " + Discount.PCT_20.apply(100));

        // 4. Enum implements interface - strategy in one type
        Notifier n = Channel.SMS;
        n.notify("hello there");

        // 5. Enum singleton - always exactly one instance, thread-safe by JVM
        Config.INSTANCE.setEnv("staging");
        System.out.println("[5] env = " + Config.INSTANCE.env());

        // 6a. EnumSet - bitset-backed, super fast
        EnumSet<Day> weekend = EnumSet.of(Day.SAT, Day.SUN);
        EnumSet<Day> workDays = EnumSet.complementOf(weekend);
        System.out.println("[6] weekend = " + weekend);
        System.out.println("[6] work    = " + workDays);

        // 6b. EnumMap - array-backed, faster + smaller than HashMap<Day,...>
        EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
        schedule.put(Day.MON, "standup");
        schedule.put(Day.FRI, "demo");
        System.out.println("[6] schedule = " + schedule);

        // 7. Useful static methods on any enum
        System.out.println("[7] valueOf     = " + Day.valueOf("WED"));
        System.out.println("[7] values      = " + Arrays.toString(Day.values()));
        System.out.println("[7] ordinal WED = " + Day.WED.ordinal());
    }
}
