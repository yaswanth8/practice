package com.careerit.java.java11;

import java.util.List;

/**
 * Java 11 - `var` in lambda parameters.
 *
 * Main reason: you can attach annotations to lambda parameters.
 * Rules:
 *   - all parameters must use `var` (or all inferred without `var`) - no mixing
 *   - parentheses are required, even for single parameter
 */
public class VarInLambda {

    @interface NonNull {}

    public static void main(String[] args) {
        List<String> names = List.of("Ravi", "Asha");
        names.forEach((var n) -> System.out.println(n));
        names.stream().map((@NonNull var n) -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
