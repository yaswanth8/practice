package com.careerit.java.java19;

/**
 * Java 19 - Record patterns (preview; standard in Java 21).
 *
 * Deconstruct a record directly inside instanceof / switch:
 *   case Point(int x, int y) -> ...
 *
 * Supports nesting: you can match on `Line(Point(var x1, var y1), Point(var x2, var y2))`.
 */
public class RecordPatternsPreview {

    record Point(int x, int y) {}
    record Line(Point from, Point to) {}

    static String describe(Object o) {
        return switch (o) {
            case Point(int x, int y) when x == y            -> "diagonal point " + x;
            case Point(int x, int y)                        -> "point " + x + "," + y;
            case Line(Point(var x1, var y1), Point(var x2, var y2)) ->
                    "line " + x1 + "," + y1 + " -> " + x2 + "," + y2;
            case null                                        -> "null";
            default                                          -> "other";
        };
    }

    public static void main(String[] args) {
        System.out.println(describe(new Point(3, 3)));
        System.out.println(describe(new Point(1, 4)));
        System.out.println(describe(new Line(new Point(0, 0), new Point(5, 5))));
        System.out.println(describe(null));
    }
}
