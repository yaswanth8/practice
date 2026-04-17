package com.careerit.java.java08;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Java 8 - Built-in Functional Interfaces (java.util.function).
 *
 * Cheat sheet:
 *   Function<T,R>       R apply(T t)            - transform one value
 *   BiFunction<T,U,R>   R apply(T t, U u)       - two-arg transform
 *   Predicate<T>        boolean test(T t)       - filter / condition
 *   Consumer<T>         void accept(T t)        - side-effect / sink
 *   Supplier<T>         T get()                 - factory / lazy value
 *   UnaryOperator<T>    T apply(T t)            - Function<T,T>
 *   BinaryOperator<T>   T apply(T a, T b)       - reducers
 *
 * Use @FunctionalInterface to mark your own SAM interfaces - compiler then
 * enforces exactly one abstract method (default/static methods are allowed).
 */
public class FunctionalInterfaces {

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }

    public static void main(String[] args) {
        Function<String, Integer> length = String::length;
        Predicate<String> nonEmpty = s -> !s.isEmpty();
        Consumer<String> printer = System.out::println;
        Supplier<Long> now = System::currentTimeMillis;
        UnaryOperator<Integer> square = x -> x * x;
        BiFunction<Integer, Integer, Integer> add = Integer::sum;

        printer.accept("len=" + length.apply("java"));
        printer.accept("nonEmpty? " + nonEmpty.test("hi"));
        printer.accept("now=" + now.get());
        printer.accept("square(5)=" + square.apply(5));
        printer.accept("add(2,3)=" + add.apply(2, 3));

        Function<Integer, Integer> plus1 = x -> x + 1;
        Function<Integer, Integer> times2 = x -> x * 2;
        printer.accept("andThen: " + plus1.andThen(times2).apply(3)); // (3+1)*2 = 8
        printer.accept("compose: " + plus1.compose(times2).apply(3)); // (3*2)+1 = 7

        TriFunction<Integer, Integer, Integer, Integer> addThree = (a, b, c) -> a + b + c;
        printer.accept("addThree=" + addThree.apply(1, 2, 3));
    }
}
