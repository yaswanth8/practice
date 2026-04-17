package com.careerit.java.java11;

import java.util.stream.Collectors;

/**
 * Java 11 - String API additions.
 *
 *   isBlank()         : true if empty or only whitespace (Unicode-aware)
 *   strip() / stripLeading() / stripTrailing() : Unicode-aware trim
 *   lines()           : Stream<String> split by line terminators
 *   repeat(n)         : repeat string n times
 *   chars()           : IntStream of code units (inherited, but commonly used)
 *
 * trim() only strips US-ASCII whitespace; strip() follows Character.isWhitespace.
 */
public class StringMethods {

    public static void main(String[] args) {
        System.out.println("   ".isBlank());
        System.out.println("  hello  ".strip());
        System.out.println("abc".repeat(3));

        String multi = "one\ntwo\nthree";
        String joined = multi.lines()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" | "));
        System.out.println(joined);
    }
}
