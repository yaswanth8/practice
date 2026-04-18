package com.careerit.java.interviewpatterns;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 *  CLASSIC INTERVIEW PROBLEMS - solved with modern Java (8+)
 * ============================================================================
 *
 *  These are the problems that come up again and again. Each has a tiny,
 *  idiomatic solution using streams, records, pattern matching, etc.
 *
 *  Try to solve each one by hand BEFORE looking at the implementation.
 * ============================================================================
 */
public class ClassicProblems {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Find the second-highest salary
        // ---------------------------------------------------------------
        int[] salaries = {50, 90, 70, 90, 80};
        int secondHighest = Arrays.stream(salaries)
                .distinct()
                .boxed()
                .sorted((a, b) -> b - a)
                .skip(1)
                .findFirst()
                .orElseThrow();
        System.out.println("[1] second highest = " + secondHighest);

        // ---------------------------------------------------------------
        // 2. Frequency of each character in a string (order-preserving)
        // ---------------------------------------------------------------
        String word = "programming";
        Map<Character, Long> freq = word.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(
                        c -> c, LinkedHashMap::new, Collectors.counting()));
        System.out.println("[2] freq = " + freq);

        // ---------------------------------------------------------------
        // 3. First non-repeated character
        // ---------------------------------------------------------------
        Character firstNonRepeated = freq.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        System.out.println("[3] first non-repeated in '" + word + "' = " + firstNonRepeated);

        // ---------------------------------------------------------------
        // 4. Sum of digits of a number
        // ---------------------------------------------------------------
        int n = 12345;
        int sumOfDigits = String.valueOf(n).chars().map(c -> c - '0').sum();
        System.out.println("[4] sum of digits of " + n + " = " + sumOfDigits);

        // ---------------------------------------------------------------
        // 5. Reverse each word of a sentence
        // ---------------------------------------------------------------
        String sentence = "java is fun";
        String reversedWords = Arrays.stream(sentence.split(" "))
                .map(w -> new StringBuilder(w).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println("[5] reversed words = " + reversedWords);

        // ---------------------------------------------------------------
        // 6. Palindrome check (stream-free AND stream versions)
        // ---------------------------------------------------------------
        String s = "racecar";
        boolean palindromeClassic = new StringBuilder(s).reverse().toString().equals(s);
        boolean palindromeStream  = IntStream.range(0, s.length() / 2)
                .allMatch(i -> s.charAt(i) == s.charAt(s.length() - 1 - i));
        System.out.println("[6] palindrome? classic=" + palindromeClassic + ", stream=" + palindromeStream);

        // ---------------------------------------------------------------
        // 7. Fibonacci sequence of length 10 using Stream.iterate
        // ---------------------------------------------------------------
        List<Integer> fib = java.util.stream.Stream.iterate(new int[]{0, 1},
                        arr -> new int[]{arr[1], arr[0] + arr[1]})
                .limit(10)
                .map(arr -> arr[0])
                .collect(Collectors.toList());
        System.out.println("[7] fibonacci = " + fib);

        // ---------------------------------------------------------------
        // 8. Find duplicates in a list
        // ---------------------------------------------------------------
        List<Integer> data = List.of(1, 2, 3, 2, 4, 5, 3, 6);
        List<Integer> duplicates = data.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("[8] duplicates = " + duplicates);

        // ---------------------------------------------------------------
        // 9. Group anagrams
        // ---------------------------------------------------------------
        List<String> words = List.of("eat", "tea", "tan", "ate", "nat", "bat");
        Map<String, List<String>> anagrams = words.stream()
                .collect(Collectors.groupingBy(ClassicProblems::sortChars));
        System.out.println("[9] anagram groups = " + anagrams.values());

        // ---------------------------------------------------------------
        // 10. Max occurring element
        // ---------------------------------------------------------------
        Integer mostFrequent = data.stream()
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
        System.out.println("[10] most frequent = " + mostFrequent);

        // ---------------------------------------------------------------
        // 11. FizzBuzz using switch expression
        // ---------------------------------------------------------------
        IntStream.rangeClosed(1, 15).forEach(i -> System.out.print(fizzbuzz(i) + " "));
        System.out.println();
    }

    private static String sortChars(String s) {
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        return new String(ch);
    }

    private static String fizzbuzz(int i) {
        int key = (i % 3 == 0 ? 1 : 0) + (i % 5 == 0 ? 2 : 0);
        return switch (key) {
            case 1  -> "Fizz";
            case 2  -> "Buzz";
            case 3  -> "FizzBuzz";
            default -> String.valueOf(i);
        };
    }
}
