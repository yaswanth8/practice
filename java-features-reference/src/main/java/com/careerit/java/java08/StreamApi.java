package com.careerit.java.java08;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java 8 - Stream API.
 *
 * A Stream is a pipeline: source -> 0..n intermediate ops -> terminal op.
 * Intermediate ops (filter/map/sorted/distinct/...) are lazy.
 * Terminal ops (collect/forEach/reduce/count/...) trigger execution.
 *
 * Streams do not store data, do not modify the source, and are consumed once.
 * Prefer sequential; go parallel only after measuring - parallelism has overhead
 * and ordering/thread-safety implications.
 */
public class StreamApi {

    record Employee(String name, String dept, double salary) {}

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sumOfSquaresOfEvens = nums.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
        System.out.println("sumOfSquaresOfEvens = " + sumOfSquaresOfEvens);

        List<String> upper = Stream.of("a", "b", "c")
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(upper);

        int factorial5 = IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b);
        System.out.println("5! = " + factorial5);

        List<Employee> employees = List.of(
                new Employee("Ravi",  "ENG", 90_000),
                new Employee("Asha",  "ENG", 120_000),
                new Employee("Kiran", "HR",   60_000),
                new Employee("Mira",  "HR",   75_000));

        Map<String, Double> avgByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept,
                        Collectors.averagingDouble(Employee::salary)));
        System.out.println("avgByDept = " + avgByDept);

        String topEarnerPerDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept,
                        Collectors.collectingAndThen(
                                Collectors.maxBy((a, b) -> Double.compare(a.salary(), b.salary())),
                                e -> e.map(Employee::name).orElse("-"))))
                .toString();
        System.out.println("topEarnerPerDept = " + topEarnerPerDept);

        long uniqueWords = Stream.of("to be", "or not", "to be")
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .distinct()
                .count();
        System.out.println("uniqueWords = " + uniqueWords);
    }
}
