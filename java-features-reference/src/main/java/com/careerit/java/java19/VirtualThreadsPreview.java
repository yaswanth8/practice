package com.careerit.java.java19;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Java 19 - Virtual threads (preview; standard in Java 21).
 *
 * Virtual threads are lightweight threads scheduled on a small pool of carrier
 * platform threads. Blocking a virtual thread unmounts it from its carrier, so
 * you can have millions of them doing synchronous, blocking IO cheaply.
 *
 * Prefer:
 *   Executors.newVirtualThreadPerTaskExecutor()
 * or
 *   Thread.ofVirtual().start(runnable)
 *
 * Pitfalls:
 *   - before Java 24, synchronized blocks could pin the carrier; prefer
 *     java.util.concurrent.Lock in that era.
 *   - virtual threads are NOT faster for CPU-bound work.
 */
public class VirtualThreadsPreview {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                try { Thread.sleep(Duration.ofMillis(50)); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                return i;
            }));
        }
        System.out.println("done in " + (System.currentTimeMillis() - start) + "ms");

        Thread t = Thread.ofVirtual().name("vt-demo").start(() ->
                System.out.println("running on " + Thread.currentThread()));
        t.join();
    }
}
