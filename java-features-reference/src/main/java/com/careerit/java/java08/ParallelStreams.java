package com.careerit.java.java08;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 *  Java 8 : PARALLEL STREAMS
 * ============================================================================
 *
 *  .parallelStream()  or  .stream().parallel()
 *
 *  Runs the pipeline on the common ForkJoinPool. Can speed up CPU-heavy work,
 *  but is a performance FOOTGUN in many interview-relevant ways.
 *
 *  USE WHEN...
 *  -----------
 *   * The operation per element is expensive (math, hashing, parsing).
 *   * The source is cheap to split (ArrayList, int[], IntStream.range).
 *   * The pipeline is stateless and has no shared mutable state.
 *   * Order doesn't matter (or you accept the cost of forcing it).
 *
 *  AVOID WHEN...
 *  -------------
 *   * Element work is cheap (overhead > speedup).
 *   * Source is LinkedList / Stream.iterate (hard to split evenly).
 *   * You share a non-thread-safe collector (HashMap, ArrayList).
 *   * You're already inside a request thread / ForkJoinPool - you may starve it.
 *
 *  ORDER
 *  -----
 *  Parallel streams may PROCESS out of order, but ordered sources + ordered
 *  collectors preserve encounter order in the RESULT (at some cost). Use
 *  .unordered() to allow more parallelism when you don't need order.
 * ============================================================================
 */
public class ParallelStreams {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Sequential vs parallel - same answer, different speed profile
        // ---------------------------------------------------------------
        long seq = time(() -> IntStream.rangeClosed(1, 1_000_000)
                .filter(ParallelStreams::isPrime)
                .count());
        long par = time(() -> IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .filter(ParallelStreams::isPrime)
                .count());
        System.out.println("[1] sequential ms = " + seq);
        System.out.println("[1] parallel   ms = " + par);

        // ---------------------------------------------------------------
        // 2. Order preservation
        //    forEach         - parallel may print out of order
        //    forEachOrdered  - parallel preserves encounter order (slower)
        // ---------------------------------------------------------------
        System.out.print("[2] forEach        : ");
        List.of(1, 2, 3, 4, 5, 6, 7, 8).parallelStream().forEach(i -> System.out.print(i + " "));
        System.out.println();
        System.out.print("[2] forEachOrdered : ");
        List.of(1, 2, 3, 4, 5, 6, 7, 8).parallelStream().forEachOrdered(i -> System.out.print(i + " "));
        System.out.println();

        // ---------------------------------------------------------------
        // 3. SHARED MUTABLE STATE = bug
        // ---------------------------------------------------------------
        AtomicInteger safeCounter = new AtomicInteger();
        int plainCounter[] = {0};  // unsafe, demonstrates the bug
        IntStream.rangeClosed(1, 100_000).parallel().forEach(i -> {
            safeCounter.incrementAndGet();
            plainCounter[0]++;            // race condition - not guaranteed 100000
        });
        System.out.println("[3] atomic  = " + safeCounter.get());
        System.out.println("[3] unsafe  = " + plainCounter[0] + " (often < 100000)");

        // ---------------------------------------------------------------
        // 4. Correct way to aggregate in parallel: reduce or a concurrent collector
        // ---------------------------------------------------------------
        int sum = IntStream.rangeClosed(1, 100).parallel().reduce(0, Integer::sum);
        System.out.println("[4] sum 1..100 = " + sum);

        // groupingByConcurrent returns a ConcurrentMap and can merge in parallel
        var lengths = List.of("a", "bb", "ccc", "dd", "eeee").parallelStream()
                .collect(Collectors.groupingByConcurrent(String::length));
        System.out.println("[4] concurrent group = " + lengths);
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }

    private static long time(Runnable r) {
        long t0 = System.currentTimeMillis();
        r.run();
        return System.currentTimeMillis() - t0;
    }
}
