package com.careerit.java.java14;

import java.util.List;

/**
 * Java 14 - Records (preview; standard in Java 16).
 *
 * A record is a transparent, immutable data carrier. The compiler generates:
 *   - private final fields for each component
 *   - a canonical constructor
 *   - accessors named after each component
 *   - equals/hashCode/toString based on components
 *
 * Records can have:
 *   - compact constructors for validation / normalization
 *   - additional static factories and instance methods
 *   - static fields
 * But NOT additional instance fields (all state must come from components).
 *
 * Records are implicitly final and extend java.lang.Record.
 */
public class RecordsPreview {

    record Point(int x, int y) {
        // compact constructor: validation, no parameter list
        public Point {
            if (x < 0 || y < 0) throw new IllegalArgumentException("non-negative only");
        }
        // extra behaviour is allowed
        public Point translate(int dx, int dy) { return new Point(x + dx, y + dy); }
        public static Point origin() { return new Point(0, 0); }
    }

    public static void main(String[] args) {
        Point a = new Point(1, 2);
        Point b = a.translate(10, 10);
        System.out.println(a + " -> " + b);
        System.out.println("equals? " + a.equals(new Point(1, 2)));
        System.out.println(List.of(Point.origin(), a, b));
    }
}
