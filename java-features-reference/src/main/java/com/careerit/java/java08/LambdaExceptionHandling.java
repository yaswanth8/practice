package com.careerit.java.java08;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ============================================================================
 *  Java 8 : CHECKED EXCEPTIONS IN LAMBDAS / STREAMS
 * ============================================================================
 *
 *  THE PROBLEM
 *  -----------
 *  Built-in functional interfaces (Function, Predicate, ...) do NOT declare
 *  checked exceptions. So this will NOT compile:
 *
 *      files.stream()
 *           .map(path -> Files.readString(path))   // IOException is checked!
 *           .collect(...);
 *
 *  THREE COMMON SOLUTIONS
 *  ----------------------
 *   A. try/catch INSIDE the lambda, convert to RuntimeException.
 *   B. Write a small "throwing" functional interface + wrapper helper.
 *   C. Use a library (vavr, Failsafe, Apache Commons) - but interviews care
 *      more about seeing that you can do A + B from scratch.
 *
 *  This file demonstrates A and B.
 * ============================================================================
 */
public class LambdaExceptionHandling {

    // -------- Option B: custom throwing functional interface ---------
    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    /**
     * Wrap a throwing function so it can be used as a normal Function.
     * Checked exceptions become RuntimeException (unchecked), which is
     * propagated by the stream machinery.
     */
    static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R, Exception> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                if (e instanceof RuntimeException re) throw re;
                throw new RuntimeException(e);
            }
        };
    }

    // A demo method that throws a checked exception
    static int parseStrict(String s) throws Exception {
        if (s == null || s.isBlank()) throw new Exception("empty input");
        return Integer.parseInt(s.trim());
    }

    public static void main(String[] args) {
        List<String> raw = List.of(" 10", "20 ", "30");

        // ---------------------------------------------------------------
        // A. try/catch inside the lambda
        // ---------------------------------------------------------------
        List<Integer> viaTryCatch = raw.stream()
                .map(s -> {
                    try {
                        return parseStrict(s);
                    } catch (Exception e) {
                        throw new RuntimeException("bad input: " + s, e);
                    }
                })
                .collect(Collectors.toList());
        System.out.println("[A] " + viaTryCatch);

        // ---------------------------------------------------------------
        // B. Wrap once, reuse everywhere
        // ---------------------------------------------------------------
        List<Integer> viaWrapper = raw.stream()
                .map(unchecked(LambdaExceptionHandling::parseStrict))
                .collect(Collectors.toList());
        System.out.println("[B] " + viaWrapper);

        // ---------------------------------------------------------------
        // Demonstrate propagation
        // ---------------------------------------------------------------
        try {
            List.of("x").stream()
                    .map(unchecked(LambdaExceptionHandling::parseStrict))
                    .collect(Collectors.toList());
        } catch (RuntimeException re) {
            System.out.println("[propagate] caught: " + re.getCause().getMessage());
        }
    }
}
