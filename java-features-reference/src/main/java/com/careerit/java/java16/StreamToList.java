package com.careerit.java.java16;

import java.util.List;
import java.util.stream.Stream;

/**
 * Java 16 - Stream.toList().
 *
 * Shorter than `.collect(Collectors.toList())`. Returns an UNMODIFIABLE list
 * and tolerates null elements (unlike List.copyOf / Collectors.toUnmodifiableList).
 */
public class StreamToList {

    public static void main(String[] args) {
        List<String> upper = Stream.of("a", "b", "c")
                .map(String::toUpperCase)
                .toList();
        System.out.println(upper);

        try {
            upper.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("unmodifiable confirmed");
        }
    }
}
