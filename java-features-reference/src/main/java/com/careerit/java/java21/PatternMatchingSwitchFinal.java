package com.careerit.java.java21;

/**
 * Java 21 - Pattern matching for switch is permanent (JEP 441).
 * Record patterns are also permanent (JEP 440).
 *
 * Highlights:
 *   - type patterns and record (deconstruction) patterns
 *   - guarded patterns with `when`
 *   - `case null` and `case null, default` combinations
 *   - exhaustive on sealed hierarchies (no default needed)
 */
public class PatternMatchingSwitchFinal {

    sealed interface Event permits Login, Logout, Purchase {}
    record Login(String user)              implements Event {}
    record Logout(String user)             implements Event {}
    record Purchase(String user, double amount) implements Event {}

    static String summarize(Event e) {
        return switch (e) {
            case Login(String u)                     -> "login("  + u + ")";
            case Logout(String u)                    -> "logout(" + u + ")";
            case Purchase(String u, double a) when a > 1000 -> "big-sale(" + u + ", " + a + ")";
            case Purchase(String u, double a)        -> "sale("   + u + ", " + a + ")";
        };
    }

    public static void main(String[] args) {
        System.out.println(summarize(new Login("ravi")));
        System.out.println(summarize(new Purchase("asha", 250)));
        System.out.println(summarize(new Purchase("kiran", 5000)));
        System.out.println(summarize(new Logout("ravi")));
    }
}
