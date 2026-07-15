package com.careerit.java.interviewpatterns;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ============================================================================
 *  FUNCTIONAL-STYLE PATTERNS used in real Java codebases
 * ============================================================================
 *
 *   1. Memoization with computeIfAbsent
 *   2. Strategy map (replace if/else-if ladders)
 *   3. Pipeline with Function.andThen
 *   4. Builder-free "with*" style on records
 *   5. Higher-order functions: functions that return functions
 *   6. Currying
 *   7. Filtering with composed predicates
 * ============================================================================
 */
public class FunctionalPatterns {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Memoization using computeIfAbsent + a closure
        // ---------------------------------------------------------------
        Map<Integer, Long> cache = new ConcurrentHashMap<>();
        Function<Integer, Long> slowSquare = n -> {
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            return (long) n * n;
        };
        Function<Integer, Long> memoized = n -> cache.computeIfAbsent(n, slowSquare);

        long t = System.currentTimeMillis();
        memoized.apply(7); memoized.apply(7); memoized.apply(7);
        System.out.println("[1] 3 calls took " + (System.currentTimeMillis() - t) + "ms (cached)");

        // ---------------------------------------------------------------
        // 2. Strategy map - replace a chain of if/else-if
        // ---------------------------------------------------------------
        Map<String, Function<Double, Double>> taxRules = Map.of(
                "standard", amt -> amt * 0.18,
                "reduced",  amt -> amt * 0.05,
                "zero",     amt -> 0.0);

        double tax = taxRules.getOrDefault("reduced", a -> a * 0.18).apply(1000.0);
        System.out.println("[2] tax = " + tax);

        // ---------------------------------------------------------------
        // 3. Building a processing pipeline with Function.andThen
        // ---------------------------------------------------------------
        Function<String, String> trim     = String::trim;
        Function<String, String> toLower  = String::toLowerCase;
        Function<String, String> dashes   = s -> s.replace(' ', '-');

        Function<String, String> slugify = trim.andThen(toLower).andThen(dashes);
        System.out.println("[3] slug = " + slugify.apply("  Hello World  "));

        // ---------------------------------------------------------------
        // 4. Builder-free "with*" updaters on records (copy-and-mutate)
        // ---------------------------------------------------------------
        record Person(String name, int age, String city) {
            Person withAge(int a)       { return new Person(name, a, city); }
            Person withCity(String c)   { return new Person(name, age, c); }
        }

        Person p = new Person("Ravi", 30, "Hyderabad");
        Person older = p.withAge(31);
        Person moved = older.withCity("Bengaluru");
        System.out.println("[4] " + moved);

        // ---------------------------------------------------------------
        // 5. Higher-order function: function that RETURNS a function
        // ---------------------------------------------------------------
        Function<Integer, Function<Integer, Integer>> adder = x -> y -> x + y;
        Function<Integer, Integer> add10 = adder.apply(10);
        System.out.println("[5] add10(5) = " + add10.apply(5));

        // ---------------------------------------------------------------
        // 6. Currying: turn f(a, b, c) into f(a)(b)(c)
        // ---------------------------------------------------------------
        Function<Double, Function<Double, Function<Double, Double>>> pricer =
                base -> taxRate -> discount -> base * (1 + taxRate) * (1 - discount);

        double finalPrice = pricer.apply(1000.0).apply(0.18).apply(0.10);
        System.out.println("[6] finalPrice = " + finalPrice);

        // ---------------------------------------------------------------
        // 7. Filtering with composed predicates
        // ---------------------------------------------------------------
        record Employee(String name, String dept, double salary) {}
        List<Employee> emps = List.of(
                new Employee("Ravi",  "ENG", 90_000),
                new Employee("Asha",  "ENG", 120_000),
                new Employee("Kiran", "HR",  60_000),
                new Employee("Mira",  "HR",  75_000));

        Predicate<Employee> isEng        = e -> e.dept().equals("ENG");
        Predicate<Employee> earnsAlot    = e -> e.salary() > 100_000;
        Predicate<Employee> seniorEng    = isEng.and(earnsAlot);
        Predicate<Employee> nonEngs      = isEng.negate();

        System.out.println("[7] senior-eng = " +
                emps.stream().filter(seniorEng).map(Employee::name).collect(Collectors.toList()));
        System.out.println("[7] non-engs   = " +
                emps.stream().filter(nonEngs).map(Employee::name).collect(Collectors.toList()));

        // Optional chaining as a safer null-handling pipeline
        String uppercased = Optional.of("  hello  ")
                .map(String::trim)
                .filter(x -> !x.isBlank())
                .map(String::toUpperCase)
                .orElse("EMPTY");
        System.out.println("[7] uppercased = " + uppercased);
    }
}
