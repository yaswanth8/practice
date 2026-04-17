package com.careerit.java.java08;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Java 8 - Lambda Expressions.
 *
 * Syntax: (params) -> expression | { statements; }
 *
 * A lambda is an anonymous implementation of a functional interface (one abstract method).
 * Common interview points:
 *   - Captured local variables must be effectively final
 *   - `this` inside a lambda refers to the enclosing class (unlike anonymous classes)
 *   - Lambdas do not introduce a new scope for local variables
 */
public class LambdaExpressions {

    public static void main(String[] args) {
        Runnable r = () -> System.out.println("Running in a thread via lambda");
        new Thread(r).start();

        List<String> names = Arrays.asList("Ravi", "Kiran", "Asha");
        names.forEach(n -> System.out.println("Hello, " + n));

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("2 + 3 = " + add.apply(2, 3));

        int base = 10;
        names.stream()
             .map(n -> n + " [base=" + base + "]")
             .forEach(System.out::println);
    }
}
