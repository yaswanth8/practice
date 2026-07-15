package com.careerit.java.java08;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Java 8 - Method References (shorthand for lambdas).
 *
 * Four kinds:
 *   1. Static:            ClassName::staticMethod     (Integer::parseInt)
 *   2. Bound instance:    instance::method            (System.out::println)
 *   3. Unbound instance:  ClassName::method           (String::toUpperCase)
 *   4. Constructor:       ClassName::new              (ArrayList::new)
 */
public class MethodReferences {

    record Book(String title) {
        static Book of(String t) { return new Book(t); }
    }

    public static void main(String[] args) {
        Function<String, Integer> parseStatic = Integer::parseInt;
        System.out.println(parseStatic.apply("42"));

        StringBuilder sb = new StringBuilder("hi");
        Supplier<String> boundToSb = sb::toString;
        System.out.println(boundToSb.get());

        Function<String, String> unboundUpper = String::toUpperCase;
        System.out.println(unboundUpper.apply("java"));

        Function<String, Book> ctorRef = Book::new;
        System.out.println(ctorRef.apply("Effective Java"));

        Function<String, Book> factory = Book::of;
        System.out.println(factory.apply("Clean Code"));

        List<String> names = Arrays.asList("Ravi", "Asha", "Kiran");
        names.sort(String::compareToIgnoreCase);
        names.forEach(System.out::println);

        BiFunction<String, String, Boolean> startsWith = String::startsWith;
        System.out.println(startsWith.apply("interview", "inter"));
    }
}
