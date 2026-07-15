package com.careerit.java.java08;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ============================================================================
 *  Java 8 : Optional<T>
 * ============================================================================
 *
 *  WHY DOES IT EXIST?
 *  ------------------
 *  To make "maybe null" EXPLICIT in method return types, so callers are forced
 *  to decide what to do when a value is absent (instead of silently NPE'ing).
 *
 *  HOW TO CREATE
 *  -------------
 *   Optional.of(x)           - x must not be null
 *   Optional.ofNullable(x)   - wraps null as empty
 *   Optional.empty()
 *
 *  HOW TO CONSUME - SAFE WAYS
 *  --------------------------
 *   ifPresent(consumer)                - do something if present
 *   orElse(default)                    - always evaluates default
 *   orElseGet(supplier)                - lazy default
 *   orElseThrow() / orElseThrow(sup)   - assert present
 *   map / filter / flatMap             - transform while safe
 *   isPresent / isEmpty (Java 11)      - check flag (avoid in favor of the above)
 *
 *  INTERVIEW TRAP: .get()
 *  ----------------------
 *  .get() throws NoSuchElementException if empty. Almost never use it directly -
 *  prefer orElseXxx or map/ifPresent. Linters flag .get() without isPresent().
 *
 *  WHEN NOT TO USE Optional
 *  ------------------------
 *   - fields / entity attributes
 *   - method parameters
 *   - as elements of collections
 *   - for primitives (use OptionalInt/OptionalLong/OptionalDouble)
 *   Designed for RETURN TYPES of library methods.
 * ============================================================================
 */
public class OptionalDemo {

    record User(String name, String email) {}

    static Optional<User> findUser(String id) {
        return "u1".equals(id) ? Optional.of(new User("Ravi", "ravi@example.com"))
                               : Optional.empty();
    }

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Creation patterns
        // ---------------------------------------------------------------
        Optional<String> a = Optional.of("hi");
        Optional<String> b = Optional.ofNullable(null);      // empty
        Optional<String> c = Optional.empty();
        System.out.println("[1] " + a + " / " + b + " / " + c);

        // ---------------------------------------------------------------
        // 2. Safely react to presence
        // ---------------------------------------------------------------
        findUser("u1").ifPresent(u -> System.out.println("[2] found: " + u));
        findUser("u-missing").ifPresent(u -> System.out.println("should NOT print"));

        // ---------------------------------------------------------------
        // 3. Provide a default: orElse vs orElseGet
        //    orElse evaluates the default ALWAYS (even when present).
        //    orElseGet only evaluates the supplier when EMPTY.
        //    -> use orElseGet when computing the default is expensive.
        // ---------------------------------------------------------------
        String eagerDefault = findUser("u1")
                .map(User::email)
                .orElse(expensive("orElse-eager"));

        String lazyDefault = findUser("u1")
                .map(User::email)
                .orElseGet(() -> expensive("orElseGet-lazy"));

        System.out.println("[3a] eager=" + eagerDefault);
        System.out.println("[3b] lazy =" + lazyDefault);

        // ---------------------------------------------------------------
        // 4. Assert present - orElseThrow
        // ---------------------------------------------------------------
        String nameOrThrow = findUser("u1")
                .map(User::name)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        System.out.println("[4] name = " + nameOrThrow);

        // ---------------------------------------------------------------
        // 5. map vs flatMap
        //    map      : function returns a plain value   -> Optional<R>
        //    flatMap  : function returns an Optional     -> Optional<R> (no nesting)
        // ---------------------------------------------------------------
        Optional<Integer> emailLen = findUser("u1")
                .map(User::email)            // Optional<String>
                .map(String::length);        // Optional<Integer>
        System.out.println("[5] email length = " + emailLen.orElse(0));

        Optional<String> upperEmail = findUser("u1")
                .flatMap(u -> Optional.ofNullable(u.email())) // already Optional, no nesting
                .map(String::toUpperCase);
        System.out.println("[5] upperEmail = " + upperEmail);

        // ---------------------------------------------------------------
        // 6. filter - keep only if predicate holds
        // ---------------------------------------------------------------
        findUser("u1")
                .filter(u -> u.email().endsWith(".com"))
                .ifPresent(u -> System.out.println("[6] .com user = " + u.name()));

        // ---------------------------------------------------------------
        // 7. Chain multiple optional lookups (the "Service style")
        // ---------------------------------------------------------------
        String masked = findUser("u1")
                .map(User::email)
                .filter(e -> e.contains("@"))
                .map(e -> e.substring(0, 2) + "***" + e.substring(e.indexOf("@")))
                .orElse("n/a");
        System.out.println("[7] masked email = " + masked);

        // ---------------------------------------------------------------
        // 8. Common ANTI-PATTERNS (don't do these in interviews)
        // ---------------------------------------------------------------
        Optional<User> u = findUser("u1");

        //  BAD: if (u.isPresent()) { ... u.get() ... } else { ... }
        //  GOOD: ifPresentOrElse (Java 9) or u.map(...).orElse(...)

        //  BAD: return Optional.ofNullable(nullable) as a FIELD type.
        //  BAD: passing Optional as a method parameter. Just overload / allow null.

        // ---------------------------------------------------------------
        // 9. Working with a List of Optionals (collect the present values)
        // ---------------------------------------------------------------
        List<Optional<String>> lookups = List.of(
                Optional.of("alpha"),
                Optional.empty(),
                Optional.of("beta"),
                Optional.empty(),
                Optional.of("gamma"));

        // Java 8 style:
        List<String> presentValuesJ8 = lookups.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Java 9+ nicer style (flatMap + Optional.stream) - shown in java09/
        System.out.println("[9] present = " + presentValuesJ8);
    }

    private static String expensive(String tag) {
        System.out.println("    (computing " + tag + " ...)");
        return "expensive-" + tag;
    }
}
