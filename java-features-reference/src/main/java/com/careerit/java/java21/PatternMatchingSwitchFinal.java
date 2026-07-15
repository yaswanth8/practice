package com.careerit.java.java21;

import java.util.List;

/**
 * ============================================================================
 *  Java 21 : PATTERN MATCHING FOR SWITCH (permanent)   JEP 441
 *           + RECORD PATTERNS (permanent)               JEP 440
 * ============================================================================
 *
 *  BEFORE (pre-Java 17 style)
 *  --------------------------
 *   if      (obj instanceof Login l)    { ... }
 *   else if (obj instanceof Logout l)   { ... }
 *   else if (obj instanceof Purchase p) { ... }
 *
 *  NOW
 *  ---
 *   return switch (obj) {
 *       case Login(String user)               -> ...
 *       case Logout(String user)              -> ...
 *       case Purchase(String user, double a)  -> ...
 *   };
 *
 *  KEY FEATURES
 *  ------------
 *   1. Type patterns:       case Integer i        -> ...
 *   2. Record patterns:     case Point(int x,_)   -> ...
 *   3. Guarded patterns:    case String s when s.length() > 3 -> ...
 *   4. Null handling:       case null             -> ...
 *                           case null, default    -> ... (combine)
 *   5. Exhaustiveness:      over a sealed hierarchy, no default needed.
 *   6. Nested deconstruction:  case Line(Point(var x1,_), Point(var x2,_)) -> ...
 * ============================================================================
 */
public class PatternMatchingSwitchFinal {

    // ---------------------------------------------------------------
    // Demo 1: sealed + records + exhaustive switch
    // ---------------------------------------------------------------
    sealed interface Event permits Login, Logout, Purchase {}
    record Login(String user)                    implements Event {}
    record Logout(String user)                   implements Event {}
    record Purchase(String user, double amount)  implements Event {}

    static String summarize(Event e) {
        return switch (e) {
            case Login(String u)                     -> "login("  + u + ")";
            case Logout(String u)                    -> "logout(" + u + ")";
            case Purchase(String u, double a) when a > 1000 -> "big-sale(" + u + ", " + a + ")";
            case Purchase(String u, double a)        -> "sale("   + u + ", " + a + ")";
        };
    }

    // ---------------------------------------------------------------
    // Demo 2: type patterns with guards + null handling
    // ---------------------------------------------------------------
    static String describe(Object o) {
        return switch (o) {
            case null               -> "null";
            case Integer i when i<0 -> "negative int " + i;
            case Integer i          -> "int " + i;
            case String s when s.isBlank() -> "blank string";
            case String s           -> "string: " + s;
            case int[] arr          -> "int[] of length " + arr.length;
            case List<?> l          -> "list size " + l.size();
            default                 -> "other: " + o.getClass().getSimpleName();
        };
    }

    // ---------------------------------------------------------------
    // Demo 3: nested record deconstruction
    // ---------------------------------------------------------------
    record Point(int x, int y) {}
    record Line(Point from, Point to) {}

    static String classifyLine(Line l) {
        return switch (l) {
            case Line(Point(var x1, var y1), Point(var x2, var y2)) when x1 == x2 ->
                    "vertical at x=" + x1;
            case Line(Point(var x1, var y1), Point(var x2, var y2)) when y1 == y2 ->
                    "horizontal at y=" + y1;
            case Line(Point(var x1, var y1), Point(var x2, var y2)) ->
                    "diagonal " + x1 + "," + y1 + " -> " + x2 + "," + y2;
        };
    }

    // ---------------------------------------------------------------
    // Demo 4: traditional "statement-style" switch with patterns
    // ---------------------------------------------------------------
    static void handle(Event e) {
        switch (e) {
            case Login l    -> System.out.println("welcome " + l.user());
            case Logout l   -> System.out.println("bye "     + l.user());
            case Purchase p -> System.out.println("charge $" + p.amount() + " to " + p.user());
        }
    }

    public static void main(String[] args) {
        System.out.println("[1] " + summarize(new Login("ravi")));
        System.out.println("[1] " + summarize(new Purchase("asha", 250)));
        System.out.println("[1] " + summarize(new Purchase("kiran", 5000)));

        System.out.println("[2] " + describe(null));
        System.out.println("[2] " + describe(-5));
        System.out.println("[2] " + describe(42));
        System.out.println("[2] " + describe(""));
        System.out.println("[2] " + describe("hi"));
        System.out.println("[2] " + describe(new int[]{1,2,3}));
        System.out.println("[2] " + describe(List.of(1,2,3,4)));
        System.out.println("[2] " + describe(3.14));

        System.out.println("[3] " + classifyLine(new Line(new Point(0,0), new Point(0,5))));
        System.out.println("[3] " + classifyLine(new Line(new Point(0,0), new Point(5,0))));
        System.out.println("[3] " + classifyLine(new Line(new Point(0,0), new Point(3,4))));

        handle(new Login("demo"));
        handle(new Purchase("demo", 42));
        handle(new Logout("demo"));
    }
}
