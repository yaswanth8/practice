package com.careerit.java.java16;

import java.util.List;

/**
 * ============================================================================
 *  Java 16 : RECORDS (permanent - JEP 395)
 * ============================================================================
 *
 *  WHAT THE COMPILER GENERATES FROM  `record Point(int x, int y) {}`
 *  ----------------------------------------------------------------
 *   - private final int x;  private final int y;
 *   - public Point(int x, int y) { this.x = x; this.y = y; }   // canonical ctor
 *   - public int x() { return x; }                              // accessor
 *   - public int y() { return y; }
 *   - public boolean equals(Object)  : field-by-field
 *   - public int hashCode()          : based on fields
 *   - public String toString()       : "Point[x=.., y=..]"
 *
 *  RULES
 *  -----
 *   - records are implicitly FINAL (can't be extended)
 *   - cannot declare additional INSTANCE fields (state must come from components)
 *   - CAN declare static fields, static methods, instance methods
 *   - CAN declare compact / canonical constructors for validation
 *   - CAN implement interfaces
 *
 *  USE CASES
 *  ---------
 *   * DTOs / value objects
 *   * method return tuples (e.g. "record Result(int count, double avg){}")
 *   * algebraic data types (combine with sealed)
 *   * local records inside methods - great for grouping intermediate data
 *     in a single pipeline stage
 * ============================================================================
 */
public class RecordsFinalized {

    // ----------------------------------------------------------------
    // 1. Basic record with validation (compact constructor)
    // ----------------------------------------------------------------
    record Money(long amount, String currency) {
        // Compact constructor: no parameter list, validates / normalizes.
        public Money {
            if (amount < 0) throw new IllegalArgumentException("negative amount");
            if (currency == null || currency.isBlank())
                throw new IllegalArgumentException("currency required");
            currency = currency.toUpperCase();   // normalize - assignment is allowed here
        }

        public Money add(Money other) {
            if (!currency.equals(other.currency))
                throw new IllegalArgumentException("mismatched currency");
            return new Money(amount + other.amount, currency);
        }
    }

    // ----------------------------------------------------------------
    // 2. Record with extra behaviour + static factory + constants
    // ----------------------------------------------------------------
    record Range(int low, int high) {
        public Range {
            if (low > high) throw new IllegalArgumentException("low > high");
        }

        public boolean contains(int v) { return v >= low && v <= high; }
        public int width()             { return high - low; }

        public static Range ofWidth(int low, int width) { return new Range(low, low + width); }
        public static final Range PERCENT = new Range(0, 100);
    }

    // ----------------------------------------------------------------
    // 3. Record implementing an interface
    // ----------------------------------------------------------------
    interface Named { String name(); }
    record Student(String name, int score) implements Named {
        public boolean passed() { return score >= 40; }
    }

    // ----------------------------------------------------------------
    // 4. Overriding an accessor (rare, but legal)
    //    Useful when you want a defensive copy.
    // ----------------------------------------------------------------
    record Wrap(int[] data) {
        @Override
        public int[] data() {
            return data.clone();   // defensive copy out
        }
    }

    // ----------------------------------------------------------------
    // 5. Generic record
    // ----------------------------------------------------------------
    record Pair<A, B>(A first, B second) {
        public <A2> Pair<A2, B> withFirst(A2 a) { return new Pair<>(a, second); }
    }

    public static void main(String[] args) {

        // Local record: allowed since Java 16. Perfect for pipeline intermediates.
        record Sale(String item, Money price) {}

        List<Sale> sales = List.of(
                new Sale("book",  new Money(500,  "inr")),
                new Sale("pen",   new Money(100,  "inr")),
                new Sale("mouse", new Money(1200, "inr")));

        Money total = sales.stream()
                .map(Sale::price)
                .reduce(new Money(0, "INR"), Money::add);
        System.out.println("[1] total = " + total);

        // Validation kicks in on construction.
        try {
            new Money(-10, "INR");
        } catch (IllegalArgumentException ex) {
            System.out.println("[1] validation ok: " + ex.getMessage());
        }

        // Range demo
        System.out.println("[2] percent contains 50? " + Range.PERCENT.contains(50));
        System.out.println("[2] ofWidth(10,5) = " + Range.ofWidth(10, 5));

        // Student demo
        Student s = new Student("Ravi", 72);
        System.out.println("[3] " + s + " passed=" + s.passed());

        // Defensive copy
        int[] raw = {1, 2, 3};
        Wrap w = new Wrap(raw);
        w.data()[0] = 99;   // mutating the returned clone does NOT change the record
        System.out.println("[4] data[0] = " + w.data()[0]);

        // Generic pair
        Pair<String, Integer> p = new Pair<>("age", 30);
        System.out.println("[5] pair = " + p + ", withFirst = " + p.withFirst(42));

        // equals / hashCode are component-based - free for free.
        Money a = new Money(100, "INR");
        Money b = new Money(100, "inr");  // normalized to INR in compact ctor
        System.out.println("[equals] " + a.equals(b) + " hash=" + (a.hashCode() == b.hashCode()));
    }
}
