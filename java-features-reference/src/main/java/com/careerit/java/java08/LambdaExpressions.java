package com.careerit.java.java08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ============================================================================
 *  Java 8 : LAMBDA EXPRESSIONS
 * ============================================================================
 *
 *  WHAT IS A LAMBDA?
 *  -----------------
 *  A lambda is just a shorter way to write an implementation of an interface
 *  that has exactly ONE abstract method (a "functional interface").
 *
 *  Anonymous class style (pre-Java 8):
 *      Runnable r = new Runnable() {
 *          public void run() { System.out.println("hi"); }
 *      };
 *
 *  Lambda style (Java 8+):
 *      Runnable r = () -> System.out.println("hi");
 *
 *  SYNTAX OPTIONS
 *  --------------
 *   ()             -> expr                         // no args
 *   x              -> expr                         // one arg, no parentheses needed
 *   (x, y)         -> expr                         // multiple args
 *   (int x, int y) -> expr                         // explicit types
 *   (x, y)         -> { stmt1; stmt2; return v; }  // block body
 *
 *  RULES YOU MUST KNOW FOR INTERVIEWS
 *  ----------------------------------
 *   1. Lambdas target "functional interfaces" (one abstract method).
 *   2. Captured local variables must be EFFECTIVELY FINAL
 *        - you can read them, but you can't reassign them after capture.
 *   3. `this` inside a lambda refers to the enclosing CLASS instance,
 *      NOT the lambda itself (different from anonymous classes).
 *   4. A lambda does NOT introduce a new scope for local variables.
 *      This means you cannot re-declare a variable from the enclosing method.
 * ============================================================================
 */
public class LambdaExpressions {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. The simplest lambda: replace a Runnable
        // ---------------------------------------------------------------
        Runnable r = () -> System.out.println("[1] Hello from a lambda");
        new Thread(r).start();

        // ---------------------------------------------------------------
        // 2. Lambda with one parameter - parentheses optional
        // ---------------------------------------------------------------
        List<String> names = Arrays.asList("Ravi", "Kiran", "Asha");
        names.forEach(n -> System.out.println("[2] Hello, " + n));

        // ---------------------------------------------------------------
        // 3. Lambda with multiple parameters (BiFunction)
        //    Takes two inputs, returns one output.
        // ---------------------------------------------------------------
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println("[3] 2 + 3 = " + add.apply(2, 3));

        // ---------------------------------------------------------------
        // 4. Block body with multiple statements and an explicit return
        // ---------------------------------------------------------------
        Function<String, String> banner = text -> {
            String line = "=".repeat(text.length() + 4);
            return line + "\n| " + text + " |\n" + line;
        };
        System.out.println("[4]\n" + banner.apply("Java 8"));

        // ---------------------------------------------------------------
        // 5. Sorting with Comparator - compact forms
        // ---------------------------------------------------------------
        List<String> people = new ArrayList<>(List.of("Ravi", "Asha", "Kiran", "Bob"));

        // 5a. Sort alphabetically
        people.sort((a, b) -> a.compareTo(b));

        // 5b. Sort by length, then alphabetically (comparator chaining)
        people.sort(Comparator
                .comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder()));
        System.out.println("[5] sorted by length then name = " + people);

        // 5c. Reverse order
        people.sort(Comparator.reverseOrder());
        System.out.println("[5] reversed = " + people);

        // ---------------------------------------------------------------
        // 6. Predicate composition (and / or / negate)
        // ---------------------------------------------------------------
        Predicate<String> startsWithA = s -> s.startsWith("A");
        Predicate<String> longerThan3 = s -> s.length() > 3;

        names.stream()
                .filter(startsWithA.and(longerThan3))
                .forEach(n -> System.out.println("[6a] A-name longer than 3: " + n));

        names.stream()
                .filter(startsWithA.negate())
                .forEach(n -> System.out.println("[6b] NOT starting with A: " + n));

        // ---------------------------------------------------------------
        // 7. Function composition (andThen / compose)
        //    andThen: run me, then the next
        //    compose: run the OTHER first, then me
        // ---------------------------------------------------------------
        Function<Integer, Integer> times2 = x -> x * 2;
        Function<Integer, Integer> plus3  = x -> x + 3;
        System.out.println("[7a] (times2 andThen plus3)(5) = " + times2.andThen(plus3).apply(5)); // (5*2)+3 = 13
        System.out.println("[7b] (times2 compose plus3)(5) = " + times2.compose(plus3).apply(5)); // (5+3)*2 = 16

        // ---------------------------------------------------------------
        // 8. Capturing local variables - must be effectively final
        // ---------------------------------------------------------------
        String greeting = "Hi";           // never reassigned => effectively final
        Runnable greeter = () -> System.out.println("[8] " + greeting + "!");
        greeter.run();

        // The line below would FAIL to compile:
        //   greeting = "Hello"; // Variable used in lambda should be final or effectively final
        //   Runnable greeter2 = () -> System.out.println(greeting);

        // ---------------------------------------------------------------
        // 9. `this` inside a lambda = the enclosing class, not the lambda
        // ---------------------------------------------------------------
        new LambdaExpressions().demoThisReference();

        // ---------------------------------------------------------------
        // 10. Lambda vs anonymous class - a quick comparison
        // ---------------------------------------------------------------
        Runnable anon = new Runnable() {
            @Override
            public void run() { System.out.println("[10] anonymous class"); }
        };
        Runnable lamb = () -> System.out.println("[10] lambda");
        anon.run();
        lamb.run();
    }

    private final String name = "LambdaExpressions";

    private void demoThisReference() {
        Runnable r = () -> System.out.println("[9] this.name from lambda = " + this.name);
        r.run();
    }
}
