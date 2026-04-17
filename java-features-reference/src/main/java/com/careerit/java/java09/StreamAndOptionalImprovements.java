package com.careerit.java.java09;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java 9 - small but high-value additions.
 *
 * Stream:
 *   takeWhile(pred)   - prefix while predicate holds
 *   dropWhile(pred)   - drop prefix while predicate holds, keep the rest
 *   ofNullable(t)     - empty stream if null, else stream of one
 *   iterate(seed, hasNext, next) - bounded iterate (3-arg)
 *
 * Optional:
 *   ifPresentOrElse(onValue, onEmpty)
 *   or(Supplier<Optional>)    - alternative Optional if empty
 *   stream()                  - 0/1 element stream for flatMap-style chaining
 */
public class StreamAndOptionalImprovements {

    public static void main(String[] args) {
        System.out.println(Stream.of(1, 2, 3, 10, 4, 5)
                .takeWhile(n -> n < 5).collect(Collectors.toList()));

        System.out.println(Stream.of(1, 2, 3, 10, 4, 5)
                .dropWhile(n -> n < 5).collect(Collectors.toList()));

        System.out.println(Stream.ofNullable((String) null).count());
        System.out.println(Stream.ofNullable("x").count());

        System.out.println(Stream.iterate(1, n -> n < 100, n -> n * 2)
                .collect(Collectors.toList()));

        Optional<String> empty = Optional.empty();
        empty.ifPresentOrElse(
                v -> System.out.println("got " + v),
                () -> System.out.println("nothing to see"));

        String val = empty.or(() -> Optional.of("fallback")).orElseThrow();
        System.out.println("val = " + val);

        long count = Stream.of(Optional.of("a"), Optional.<String>empty(), Optional.of("c"))
                .flatMap(Optional::stream)
                .count();
        System.out.println("non-empty count = " + count);
    }
}
