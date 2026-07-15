package com.careerit.java.java21;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Java 21 - Virtual threads are a permanent feature (JEP 444).
 *
 * Key idioms:
 *   Thread.startVirtualThread(runnable)
 *   Executors.newVirtualThreadPerTaskExecutor()
 *   Thread.ofVirtual().name("x").start(runnable)
 *
 * Use virtual threads for IO-bound work where you'd otherwise size a huge
 * platform-thread pool. Don't pool virtual threads - they are the pool.
 */
public class VirtualThreadsFinal {

    public static void main(String[] args) throws Exception {
        try (var ex = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<String>> tasks = List.of(
                    () -> fetch("https://example.com/a"),
                    () -> fetch("https://example.com/b"),
                    () -> fetch("https://example.com/c"));
            List<String> results = ex.invokeAll(tasks)
                    .stream()
                    .map(f -> { try { return f.get(); } catch (Exception e) { return "err"; } })
                    .toList();
            System.out.println(results);
        }
    }

    static String fetch(String url) throws InterruptedException {
        Thread.sleep(50);
        return url + " [ok on " + Thread.currentThread() + "]";
    }
}
