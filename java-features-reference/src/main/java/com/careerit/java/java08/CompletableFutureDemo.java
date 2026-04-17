package com.careerit.java.java08;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * Java 8 - CompletableFuture.
 *
 * Async + composable alternative to Future. Supports chaining with
 * thenApply / thenCompose / thenCombine and error handling via
 * exceptionally / handle.
 *
 * Interview talking points:
 *   - thenApply     : synchronous map (Function)
 *   - thenCompose   : flatMap (Function returning CompletableFuture)
 *   - thenCombine   : zip two independent CFs
 *   - exceptionally : recover from failure
 *   - Always supply an explicit Executor for blocking work - the default
 *     ForkJoinPool.commonPool is shared with the JVM.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var exec = Executors.newFixedThreadPool(4);
        try {
            CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> "Hello", exec);
            CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> "World", exec);

            String combined = hello.thenCombine(world, (h, w) -> h + " " + w).get();
            System.out.println("combined = " + combined);

            String pipeline = CompletableFuture.supplyAsync(() -> "42", exec)
                    .thenApply(Integer::parseInt)
                    .thenCompose(n -> CompletableFuture.supplyAsync(() -> "value=" + (n * 2), exec))
                    .exceptionally(ex -> "fallback: " + ex.getMessage())
                    .get();
            System.out.println("pipeline = " + pipeline);

            List<CompletableFuture<Integer>> tasks = List.of(
                    CompletableFuture.supplyAsync(() -> compute(1), exec),
                    CompletableFuture.supplyAsync(() -> compute(2), exec),
                    CompletableFuture.supplyAsync(() -> compute(3), exec));

            CompletableFuture<Void> all = CompletableFuture.allOf(tasks.toArray(CompletableFuture[]::new));
            all.get();
            int sum = tasks.stream().mapToInt(cf -> cf.join()).sum();
            System.out.println("sum = " + sum);
        } finally {
            exec.shutdown();
        }
    }

    private static int compute(int i) {
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return i * i;
    }
}
