package com.careerit.java.interviewpatterns;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ============================================================================
 *  STRING PROBLEMS - the ones you'll get asked
 * ============================================================================
 *
 *  Each problem has:
 *    - one-line description
 *    - modern-Java solution
 *    - brief comment on the trick used
 *
 *  Practice: predict the output before you run the file.
 * ============================================================================
 */
public class StringProblems {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Reverse a string
        // ---------------------------------------------------------------
        String s1 = "hello";
        String rev = new StringBuilder(s1).reverse().toString();
        System.out.println("[1] reverse(hello) = " + rev);

        // ---------------------------------------------------------------
        // 2. Check palindrome (ignore case + non-letters)
        // ---------------------------------------------------------------
        String s2 = "A man, a plan, a canal, Panama";
        String cleaned = s2.toLowerCase().replaceAll("[^a-z]", "");
        boolean palin = cleaned.contentEquals(new StringBuilder(cleaned).reverse());
        System.out.println("[2] palindrome? " + palin);

        // ---------------------------------------------------------------
        // 3. Count occurrences of each character (preserve order)
        // ---------------------------------------------------------------
        String s3 = "banana";
        Map<Character, Long> freq = s3.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()));
        System.out.println("[3] freq = " + freq);

        // ---------------------------------------------------------------
        // 4. First non-repeating character
        // ---------------------------------------------------------------
        Character first = freq.entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        System.out.println("[4] first non-repeating = " + first);

        // ---------------------------------------------------------------
        // 5. Check anagram
        // ---------------------------------------------------------------
        System.out.println("[5] anagram? " + isAnagram("listen", "silent"));
        System.out.println("[5] anagram? " + isAnagram("hello", "world"));

        // ---------------------------------------------------------------
        // 6. Count vowels and consonants
        // ---------------------------------------------------------------
        String s6 = "beautiful";
        long vowels = s6.chars().filter(c -> "aeiouAEIOU".indexOf(c) >= 0).count();
        long consonants = s6.chars().filter(Character::isLetter).count() - vowels;
        System.out.println("[6] vowels=" + vowels + ", consonants=" + consonants);

        // ---------------------------------------------------------------
        // 7. Remove duplicates but keep order
        // ---------------------------------------------------------------
        String s7 = "programming";
        String noDup = s7.chars().distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
        System.out.println("[7] no dup = " + noDup);

        // ---------------------------------------------------------------
        // 8. Longest word in a sentence
        // ---------------------------------------------------------------
        String s8 = "The quick brown fox jumped over the lazy dog";
        String longest = Arrays.stream(s8.split("\\s+"))
                .max((a, b) -> a.length() - b.length())
                .orElse("");
        System.out.println("[8] longest word = " + longest);

        // ---------------------------------------------------------------
        // 9. Word count in a sentence (case-insensitive)
        // ---------------------------------------------------------------
        Map<String, Long> wordCount = Arrays.stream(s8.toLowerCase().split("\\s+"))
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        System.out.println("[9] wordCount = " + wordCount);

        // ---------------------------------------------------------------
        // 10. Reverse each word in a sentence (order kept)
        // ---------------------------------------------------------------
        String reversedWords = Arrays.stream(s8.split(" "))
                .map(w -> new StringBuilder(w).reverse().toString())
                .collect(Collectors.joining(" "));
        System.out.println("[10] reversed words = " + reversedWords);

        // ---------------------------------------------------------------
        // 11. Check if two strings are rotations of each other
        //     Trick: s1+s1 contains s2 iff s2 is a rotation of s1.
        // ---------------------------------------------------------------
        System.out.println("[11] rotation? " + isRotation("abcde", "cdeab"));
        System.out.println("[11] rotation? " + isRotation("abcde", "abced"));

        // ---------------------------------------------------------------
        // 12. Reverse only vowels: "hello" -> "holle"
        // ---------------------------------------------------------------
        System.out.println("[12] reverseVowels(hello) = " + reverseVowels("hello"));
        System.out.println("[12] reverseVowels(leetcode) = " + reverseVowels("leetcode"));

        // ---------------------------------------------------------------
        // 13. Longest common prefix of a list of strings
        // ---------------------------------------------------------------
        List<String> words = List.of("flower", "flow", "flight");
        System.out.println("[13] longestCommonPrefix = " + longestCommonPrefix(words));

        // ---------------------------------------------------------------
        // 14. Compress a string: "aabbbccdaa" -> "a2b3c2d1a2"
        // ---------------------------------------------------------------
        System.out.println("[14] compress = " + runLengthEncode("aabbbccdaa"));

        // ---------------------------------------------------------------
        // 15. Check if a string has all unique chars (assume ASCII)
        // ---------------------------------------------------------------
        System.out.println("[15] unique(abcdef)? " + hasUniqueChars("abcdef"));
        System.out.println("[15] unique(hello)? "  + hasUniqueChars("hello"));

        // ---------------------------------------------------------------
        // 16. Sum of digits inside a string: "a1b22c333" -> 1+2+2+3+3+3 = 14
        // ---------------------------------------------------------------
        int digitSum = "a1b22c333".chars().filter(Character::isDigit).map(c -> c - '0').sum();
        System.out.println("[16] digitSum = " + digitSum);

        // ---------------------------------------------------------------
        // 17. Convert snake_case to camelCase
        // ---------------------------------------------------------------
        System.out.println("[17] snake -> camel: " + snakeToCamel("hello_world_from_java"));

        // ---------------------------------------------------------------
        // 18. Longest substring without repeating chars (sliding window)
        // ---------------------------------------------------------------
        System.out.println("[18] longestUniqueSubstring(abcabcbb) = " +
                longestUniqueSubstring("abcabcbb"));
    }

    static boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        char[] ca = a.toCharArray(), cb = b.toCharArray();
        Arrays.sort(ca); Arrays.sort(cb);
        return Arrays.equals(ca, cb);
    }

    static boolean isRotation(String a, String b) {
        return a.length() == b.length() && (a + a).contains(b);
    }

    static String reverseVowels(String s) {
        char[] c = s.toCharArray();
        String vowels = "aeiouAEIOU";
        int i = 0, j = c.length - 1;
        while (i < j) {
            while (i < j && vowels.indexOf(c[i]) < 0) i++;
            while (i < j && vowels.indexOf(c[j]) < 0) j--;
            char tmp = c[i]; c[i] = c[j]; c[j] = tmp;
            i++; j--;
        }
        return new String(c);
    }

    static String longestCommonPrefix(List<String> words) {
        if (words.isEmpty()) return "";
        String prefix = words.get(0);
        for (String w : words) {
            while (!w.startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }

    static String runLengthEncode(String s) {
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            int count = 0;
            while (i < s.length() && s.charAt(i) == c) { count++; i++; }
            out.append(c).append(count);
        }
        return out.toString();
    }

    static boolean hasUniqueChars(String s) {
        return s.chars().distinct().count() == s.length();
    }

    static String snakeToCamel(String s) {
        String[] parts = s.split("_");
        return parts[0] + IntStream.range(1, parts.length)
                .mapToObj(i -> Character.toUpperCase(parts[i].charAt(0)) + parts[i].substring(1))
                .collect(Collectors.joining());
    }

    /** Classic sliding-window problem. */
    static int longestUniqueSubstring(String s) {
        int[] lastIndex = new int[256];
        Arrays.fill(lastIndex, -1);
        int start = 0, best = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (lastIndex[c] >= start) start = lastIndex[c] + 1;
            lastIndex[c] = i;
            best = Math.max(best, i - start + 1);
        }
        return best;
    }
}
