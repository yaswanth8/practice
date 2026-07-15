package com.careerit.java.java14;

/**
 * Java 14 - Pattern matching for instanceof (preview; standard in Java 16).
 *
 * Old:
 *   if (obj instanceof String) {
 *       String s = (String) obj;
 *       // use s
 *   }
 *
 * New:
 *   if (obj instanceof String s) {
 *       // s is in scope, already cast
 *   }
 *
 * The pattern variable is in scope wherever the compiler can prove the test
 * matched (including the negation: `if (!(o instanceof String s)) return; use(s);`).
 */
public class PatternMatchingInstanceof {

    static int lengthOrSize(Object obj) {
        if (obj instanceof String s) return s.length();
        if (obj instanceof java.util.Collection<?> c) return c.size();
        if (obj instanceof int[] arr) return arr.length;
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(lengthOrSize("hello"));
        System.out.println(lengthOrSize(java.util.List.of(1, 2, 3)));
        System.out.println(lengthOrSize(new int[]{1, 2}));
        System.out.println(lengthOrSize(42));
    }
}
