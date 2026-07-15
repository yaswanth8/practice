package com.careerit.java.interviewpatterns;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ============================================================================
 *  CONCURRENCY BASICS you'll face in interviews
 * ============================================================================
 *
 *   1. ExecutorService + Future vs. CompletableFuture
 *   2. Composing async work (thenCombine, thenCompose)
 *   3. Timeout + fallback
 *   4. Atomic counters vs synchronized
 *   5. Virtual threads (Java 21) for many IO-bound tasks
 *
 *  Always shut down your executors. Use try-with-resources for
 *  ExecutorService (available on Java 19+: AutoCloseable).
 * ============================================================================
 */
public class ConcurrencyBasics {

    public static void main(String[] args) throws Exception {

        // ---------------------------------------------------------------
        // 1. Basic ExecutorService + Future
        // ---------------------------------------------------------------
        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
            var futures = List.of(
                    pool.submit(() -> slowSquare(2)),
                    pool.submit(() -> slowSquare(3)),
                    pool.submit(() -> slowSquare(4)));
            int sum = 0;
            for (var f : futures) sum += f.get();
            System.out.println("[1] sum of squares = " + sum);
        }

        // ---------------------------------------------------------------
        // 2. Compose async results without blocking
        // ---------------------------------------------------------------
        CompletableFuture<String> user  = CompletableFuture.supplyAsync(() -> "Ravi");
        CompletableFuture<Integer> age  = CompletableFuture.supplyAsync(() -> 30);

        String greet = user.thenCombine(age, (u, a) -> u + " (" + a + ")").get();
        System.out.println("[2] greet = " + greet);

        // thenCompose: when the next step also returns a CompletableFuture
        CompletableFuture<Integer> profileAge = CompletableFuture
                .supplyAsync(() -> "user-42")
                .thenCompose(ConcurrencyBasics::fetchAgeAsync);
        System.out.println("[2] age = " + profileAge.get());

        // ---------------------------------------------------------------
        // 3. Timeout + fallback
        // ---------------------------------------------------------------
        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "slow-value";
        });
        String withFallback = slow
                .completeOnTimeout("timeout-default", 100, TimeUnit.MILLISECONDS)
                .get();
        System.out.println("[3] with timeout = " + withFallback);

        // ---------------------------------------------------------------
        // 4. Atomic counters vs synchronized
        // ---------------------------------------------------------------
        AtomicLong atomic = new AtomicLong();
        long[] shared   = {0};
        Object lock     = new Object();

        Runnable atomicInc  = () -> { for (int i=0;i<100_000;i++) atomic.incrementAndGet(); };
        Runnable syncInc    = () -> { for (int i=0;i<100_000;i++) synchronized (lock) { shared[0]++; } };

        var t1 = new Thread(atomicInc);  var t2 = new Thread(atomicInc);
        t1.start(); t2.start(); t1.join(); t2.join();

        var t3 = new Thread(syncInc);    var t4 = new Thread(syncInc);
        t3.start(); t4.start(); t3.join(); t4.join();

        System.out.println("[4] atomic      = " + atomic.get());
        System.out.println("[4] synchronized= " + shared[0]);

        // ---------------------------------------------------------------
        // 5. Virtual threads (Java 21) - scale to many IO-bound tasks cheaply
        // ---------------------------------------------------------------
        long t0 = System.currentTimeMillis();
        try (var vt = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 1000; i++) {
                vt.submit(() -> { sleep(20); return null; });
            }
        } // close() waits for all tasks to finish
        System.out.println("[5] 1000 IO-bound vt tasks done in " +
                (System.currentTimeMillis() - t0) + "ms");
    }

    private static int slowSquare(int n) {
        sleep(100);
        return n * n;
    }

    private static CompletableFuture<Integer> fetchAgeAsync(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(50);
            return userId.length() + 20;
        });
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }
}
