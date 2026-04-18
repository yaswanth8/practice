package com.careerit.java.java08;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ============================================================================
 *  Java 8 : STREAM API - the BIG one for interviews
 * ============================================================================
 *
 *  MENTAL MODEL
 *  ------------
 *  A Stream is a PIPELINE, not a data structure:
 *
 *     source  -->  0..n intermediate ops  -->  1 terminal op
 *     (List,      (filter/map/sorted/...)     (collect/forEach/
 *      array,                                   count/reduce/...)
 *      Stream.of,
 *      IntStream.range,
 *      Files.lines,
 *      ...)
 *
 *  FOUR FACTS YOU MUST KNOW
 *  ------------------------
 *   1. Intermediate ops are LAZY. Nothing runs until a terminal op is called.
 *   2. Streams are consumed ONCE. After a terminal op, the stream is closed.
 *   3. Streams do NOT modify the source collection.
 *   4. Streams are NOT data structures. Don't "store" them.
 *
 *  INTERMEDIATE vs TERMINAL (most common ones)
 *  -------------------------------------------
 *   Intermediate: filter, map, flatMap, distinct, sorted, peek, limit, skip
 *   Terminal    : collect, forEach, reduce, count, min/max, findFirst,
 *                 findAny, anyMatch, allMatch, noneMatch, toArray, toList (Java 16)
 *
 *  SHORT-CIRCUITING TERMINAL OPS
 *  -----------------------------
 *   findFirst, findAny, anyMatch, allMatch, noneMatch, limit.
 *   These stop as soon as the answer is known - great for infinite streams.
 *
 *  PRIMITIVE STREAMS
 *  -----------------
 *   IntStream, LongStream, DoubleStream - avoid boxing, add sum/avg/etc.
 *   Convert with mapToInt / boxed().
 * ============================================================================
 */
public class StreamApi {

    record Employee(String name, String dept, double salary, int age) {}

    public static void main(String[] args) {

        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Employee> employees = List.of(
                new Employee("Ravi",  "ENG", 90_000,  28),
                new Employee("Asha",  "ENG", 120_000, 35),
                new Employee("Kiran", "HR",  60_000,  30),
                new Employee("Mira",  "HR",  75_000,  40),
                new Employee("Sam",   "SALES", 85_000, 25));

        // ---------------------------------------------------------------
        // 1. Classic filter + map + collect
        // ---------------------------------------------------------------
        List<String> engineerNames = employees.stream()
                .filter(e -> e.dept().equals("ENG"))
                .map(Employee::name)
                .collect(Collectors.toList());
        System.out.println("[1] engineers = " + engineerNames);

        // ---------------------------------------------------------------
        // 2. mapToInt -> sum/avg (primitive stream, no boxing)
        // ---------------------------------------------------------------
        int sumOfSquaresOfEvens = nums.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(n -> n * n)
                .sum();
        System.out.println("[2] sum of squares of evens = " + sumOfSquaresOfEvens);

        double avgSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0);
        System.out.println("[2] avg salary = " + avgSalary);

        // ---------------------------------------------------------------
        // 3. reduce - fold the stream down to a single value
        //    3 flavours: identity+accumulator, accumulator only, combiner
        // ---------------------------------------------------------------
        int factorial5 = IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b);
        System.out.println("[3] 5! = " + factorial5);

        Optional<String> longest = employees.stream()
                .map(Employee::name)
                .reduce((a, b) -> a.length() >= b.length() ? a : b);
        longest.ifPresent(n -> System.out.println("[3] longest name = " + n));

        // ---------------------------------------------------------------
        // 4. Sorting inside a stream
        //    Natural, reverse, custom, multi-level
        // ---------------------------------------------------------------
        List<Employee> top3 = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("[4] top 3 paid = " + top3);

        // ---------------------------------------------------------------
        // 5. distinct + count
        // ---------------------------------------------------------------
        long deptCount = employees.stream().map(Employee::dept).distinct().count();
        System.out.println("[5] distinct departments = " + deptCount);

        // ---------------------------------------------------------------
        // 6. anyMatch / allMatch / noneMatch - short-circuit
        // ---------------------------------------------------------------
        boolean anyMinor   = employees.stream().anyMatch(e -> e.age() < 18);
        boolean allAdults  = employees.stream().allMatch(e -> e.age() >= 18);
        boolean noneOver99 = employees.stream().noneMatch(e -> e.age() > 99);
        System.out.println("[6] anyMinor=" + anyMinor + ", allAdults=" + allAdults + ", noneOver99=" + noneOver99);

        // ---------------------------------------------------------------
        // 7. findFirst vs findAny
        //    findFirst respects encounter order; findAny may be faster in parallel.
        // ---------------------------------------------------------------
        Optional<Employee> first = employees.stream()
                .filter(e -> e.salary() > 80_000)
                .findFirst();
        first.ifPresent(e -> System.out.println("[7] first > 80k = " + e.name()));

        // ---------------------------------------------------------------
        // 8. flatMap - "flatten" a Stream of Streams into one Stream
        //    Classic: words in a sentence, or values of nested lists.
        // ---------------------------------------------------------------
        long uniqueWords = Stream.of("to be", "or not", "to be")
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .distinct()
                .count();
        System.out.println("[8] unique words = " + uniqueWords);

        List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
        List<Integer> flat = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("[8] flat = " + flat);

        // ---------------------------------------------------------------
        // 9. peek - debugging/logging WITHOUT changing the stream
        //    Only runs if there's a terminal op that actually consumes items.
        // ---------------------------------------------------------------
        long seen = Stream.of("a", "b", "c")
                .peek(v -> System.out.println("[9] peek: " + v))
                .count();
        System.out.println("[9] seen = " + seen);

        // ---------------------------------------------------------------
        // 10. groupingBy - a whole sub-API. Here: grouping + counting + averaging
        // ---------------------------------------------------------------
        Map<String, Double> avgByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::dept,
                        Collectors.averagingDouble(Employee::salary)));
        System.out.println("[10] avgByDept = " + avgByDept);

        // ---------------------------------------------------------------
        // 11. partitioningBy - a true/false split in a single pass
        // ---------------------------------------------------------------
        Map<Boolean, List<Employee>> youngVsOld = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.age() < 30));
        System.out.println("[11] young = " + youngVsOld.get(true));
        System.out.println("[11] rest  = " + youngVsOld.get(false));

        // ---------------------------------------------------------------
        // 12. toMap - be careful about duplicate keys
        // ---------------------------------------------------------------
        Map<String, Double> salaryByName = employees.stream()
                .collect(Collectors.toMap(Employee::name, Employee::salary));
        System.out.println("[12] salaryByName = " + salaryByName);

        // If keys can collide, supply a merge function:
        Map<String, Double> totalByDept = employees.stream()
                .collect(Collectors.toMap(Employee::dept, Employee::salary, Double::sum));
        System.out.println("[12] totalByDept = " + totalByDept);

        // ---------------------------------------------------------------
        // 13. Stream generators - iterate / generate
        //    Infinite streams! Remember to call limit() somewhere.
        // ---------------------------------------------------------------
        List<Integer> firstFivePowers = Stream.iterate(1, i -> i * 2)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("[13] 1,2,4,8,16 = " + firstFivePowers);

        // ---------------------------------------------------------------
        // 14. Streams are consumed ONCE - demonstrate the common bug
        // ---------------------------------------------------------------
        Stream<Integer> once = Stream.of(1, 2, 3);
        System.out.println("[14] count=" + once.count());
        try {
            once.forEach(System.out::println); // throws IllegalStateException
        } catch (IllegalStateException ex) {
            System.out.println("[14] cannot reuse stream: " + ex.getMessage());
        }
    }
}
