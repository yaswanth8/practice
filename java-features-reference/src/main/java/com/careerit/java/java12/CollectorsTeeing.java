package com.careerit.java.java12;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Java 12 - Collectors.teeing.
 *
 * Runs two downstream collectors in parallel and merges their results.
 * Very handy for "compute X and Y in a single pass" problems.
 */
public class CollectorsTeeing {

    public static void main(String[] args) {
        List<Integer> nums = List.of(10, 20, 30, 40, 50);

        double average = nums.stream().collect(Collectors.teeing(
                Collectors.summingInt(Integer::intValue),
                Collectors.counting(),
                (sum, count) -> (double) sum / count));
        System.out.println("average = " + average);

        record MinMax(int min, int max) {}
        MinMax range = nums.stream().collect(Collectors.teeing(
                Collectors.minBy(Integer::compareTo),
                Collectors.maxBy(Integer::compareTo),
                (lo, hi) -> new MinMax(lo.orElseThrow(), hi.orElseThrow())));
        System.out.println("range = " + range);
    }
}
