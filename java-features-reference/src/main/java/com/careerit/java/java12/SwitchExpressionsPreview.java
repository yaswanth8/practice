package com.careerit.java.java12;

/**
 * Java 12 - Switch expressions (preview; became standard in Java 14).
 *
 * New form allows:
 *   - arrow labels (case A -> ...) with no fall-through
 *   - comma-separated case labels (case A, B ->)
 *   - yielding a value (multi-line body uses `yield`)
 *
 * The file shows the final syntax (valid in Java 14+).
 */
public class SwitchExpressionsPreview {

    enum Day { MON, TUE, WED, THU, FRI, SAT, SUN }

    static int lettersInDay(Day d) {
        return switch (d) {
            case MON, FRI, SUN -> 6;
            case TUE           -> 7;
            case THU, SAT      -> {
                int base = d.name().length();
                yield base + 1;
            }
            case WED           -> 9;
        };
    }

    public static void main(String[] args) {
        for (Day d : Day.values()) {
            System.out.println(d + " -> " + lettersInDay(d));
        }
    }
}
