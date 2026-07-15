package com.careerit.java.interviewpatterns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ============================================================================
 *  EXCEPTIONS - checked, unchecked, and modern patterns
 * ============================================================================
 *
 *  THE HIERARCHY (things you should be able to draw)
 *  -------------------------------------------------
 *   Throwable
 *    |-- Error                 (JVM problems - do NOT catch)
 *    |    |-- OutOfMemoryError
 *    |    |-- StackOverflowError
 *    |
 *    |-- Exception             (checked - compiler enforces)
 *         |-- IOException
 *         |-- SQLException
 *         |
 *         |-- RuntimeException (unchecked - programming errors)
 *              |-- NullPointerException
 *              |-- IllegalArgumentException
 *              |-- ClassCastException
 *
 *  RULES
 *  -----
 *   1. `throws` on a method = "I might throw a checked exception, you handle it".
 *   2. Catch specific first, generic last.
 *   3. Never swallow exceptions silently. At minimum, log.
 *   4. Try-with-resources auto-closes any AutoCloseable.
 *   5. Chain causes: throw new MyEx("msg", cause).
 *   6. Prefer unchecked exceptions in modern code (like Spring / most APIs)
 *      because checked exceptions clutter method signatures.
 * ============================================================================
 */
public class ExceptionHandling {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. try/catch/finally
        //    `finally` runs whether an exception was thrown or not.
        // ---------------------------------------------------------------
        try {
            System.out.println("[1] try");
            throw new RuntimeException("boom");
        } catch (RuntimeException e) {
            System.out.println("[1] caught: " + e.getMessage());
        } finally {
            System.out.println("[1] finally always runs");
        }

        // ---------------------------------------------------------------
        // 2. Multi-catch (Java 7+) - handle multiple types the same way
        // ---------------------------------------------------------------
        try {
            risky(1);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("[2] multi-catch: " + e.getClass().getSimpleName());
        }

        // ---------------------------------------------------------------
        // 3. Try-with-resources - auto-close
        //    Any AutoCloseable can go inside try(...). Multiple allowed.
        // ---------------------------------------------------------------
        try (BufferedReader r = new BufferedReader(new StringReader("hello\nworld"))) {
            r.lines().forEach(line -> System.out.println("[3] " + line));
        } catch (IOException e) {
            System.out.println("[3] " + e.getMessage());
        }

        // ---------------------------------------------------------------
        // 4. Exception chaining - preserve the root cause
        // ---------------------------------------------------------------
        try {
            loadConfig();
        } catch (RuntimeException e) {
            System.out.println("[4] top: " + e.getMessage() +
                    " / cause: " + (e.getCause() != null ? e.getCause().getMessage() : "-"));
        }

        // ---------------------------------------------------------------
        // 5. Custom exception - typed, meaningful
        // ---------------------------------------------------------------
        try {
            transfer(-1);
        } catch (InvalidAmountException e) {
            System.out.println("[5] custom: " + e.getMessage() + ", amount=" + e.amount());
        }

        // ---------------------------------------------------------------
        // 6. Suppressed exceptions - when close() also throws
        //    Try-with-resources reports the primary and attaches the close-time
        //    exception as "suppressed", so you don't lose either.
        // ---------------------------------------------------------------
        try (var res = new NoisyResource()) {
            throw new RuntimeException("primary");
        } catch (Exception e) {
            System.out.print("[6] primary=" + e.getMessage() + ", suppressed=");
            for (Throwable s : e.getSuppressed()) System.out.print(s.getMessage() + " ");
            System.out.println();
        }

        // ---------------------------------------------------------------
        // 7. Common ANTI-PATTERNS (what NOT to do)
        // ---------------------------------------------------------------
        //  BAD: catch (Exception e) {}   // silent swallow
        //  BAD: catch (Throwable t)      // hides Errors like OOM
        //  BAD: throw new RuntimeException(e.getMessage())   // loses cause + stack
        //  GOOD: throw new MyEx("context", e)                // preserves cause
        //  BAD: use exceptions for normal control flow
    }

    static void risky(int flag) {
        if (flag == 1) throw new IllegalArgumentException("bad arg");
        if (flag == 2) throw new IllegalStateException("bad state");
    }

    static void loadConfig() {
        try {
            Files.readString(Path.of("does-not-exist.txt"));
        } catch (IOException io) {
            throw new RuntimeException("failed to load config", io);   // chain
        }
    }

    // ---------------- Custom checked exception ---------------------------
    static class InvalidAmountException extends Exception {
        private final double amount;
        InvalidAmountException(String msg, double amount) {
            super(msg);
            this.amount = amount;
        }
        double amount() { return amount; }
    }

    static void transfer(double amt) throws InvalidAmountException {
        if (amt <= 0) throw new InvalidAmountException("amount must be positive", amt);
    }

    // ---------------- Resource whose close() throws ---------------------
    static class NoisyResource implements AutoCloseable {
        @Override public void close() { throw new RuntimeException("close-failure"); }
    }
}
