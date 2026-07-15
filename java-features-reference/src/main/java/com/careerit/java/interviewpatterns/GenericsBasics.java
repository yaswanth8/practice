package com.careerit.java.interviewpatterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * ============================================================================
 *  GENERICS - the parts interviewers actually ask about
 * ============================================================================
 *
 *  Generics = compile-time type safety without casts.
 *
 *  KEY IDEAS
 *  ---------
 *   1. Type parameters:    <T>, <K,V>, <T extends Number>
 *   2. Wildcards:          <?>, <? extends T>, <? super T>
 *   3. PECS: Producer Extends, Consumer Super
 *   4. Type erasure - the JVM sees List, not List<String>. Consequences:
 *       - no `new T[10]`
 *       - no `x instanceof List<String>`  (only List<?>)
 *       - two methods differing only by generic parameter share the same erasure
 *   5. Generic methods can infer types from arguments.
 *
 *  READ THE EXAMPLES BOTTOM-UP: each one gets slightly more interesting.
 * ============================================================================
 */
public class GenericsBasics {

    // ---------------- Generic class ---------------------------------------
    static class Box<T> {
        private T value;
        public void set(T v)  { this.value = v; }
        public T get()        { return value; }
        @Override public String toString() { return "Box(" + value + ")"; }
    }

    // ---------------- Generic method (declaration <T> before return type)-
    static <T> T firstOrDefault(List<T> list, T fallback) {
        return list.isEmpty() ? fallback : list.get(0);
    }

    // Bounded type parameter: T must be a Number (or subtype)
    static <T extends Number> double sum(List<T> nums) {
        double total = 0;
        for (T n : nums) total += n.doubleValue();
        return total;
    }

    // Multiple bounds - class must come first, then interfaces.
    static <T extends Number & Comparable<T>> T maxOf(List<T> xs) {
        T best = xs.get(0);
        for (T x : xs) if (x.compareTo(best) > 0) best = x;
        return best;
    }

    // ---------------- PECS - Producer Extends ----------------------------
    // "I only read from source". source PRODUCES values -> ? extends
    static double sumAny(Collection<? extends Number> source) {
        double t = 0;
        for (Number n : source) t += n.doubleValue();
        return t;
    }

    // ---------------- PECS - Consumer Super ------------------------------
    // "I only write into target". target CONSUMES values -> ? super
    static void addInts(Collection<? super Integer> target, int n) {
        for (int i = 1; i <= n; i++) target.add(i);
    }

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Generic class
        // ---------------------------------------------------------------
        Box<String> b = new Box<>();
        b.set("hello");
        System.out.println("[1] " + b + " length=" + b.get().length());

        // ---------------------------------------------------------------
        // 2. Generic method with type inference
        // ---------------------------------------------------------------
        String s = firstOrDefault(List.of("a", "b"), "-");
        Integer n = firstOrDefault(List.<Integer>of(), 0);
        System.out.println("[2] " + s + " / " + n);

        // ---------------------------------------------------------------
        // 3. Bounded type parameter
        // ---------------------------------------------------------------
        System.out.println("[3] sum ints    = " + sum(List.of(1, 2, 3)));
        System.out.println("[3] sum doubles = " + sum(List.of(1.5, 2.5)));
        System.out.println("[3] max         = " + maxOf(List.of(3, 8, 1, 5)));

        // ---------------------------------------------------------------
        // 4. PECS in action
        // ---------------------------------------------------------------
        List<Integer> ints    = List.of(1, 2, 3);
        List<Double>  doubles = List.of(1.5, 2.5);
        System.out.println("[4] sumAny(ints)    = " + sumAny(ints));
        System.out.println("[4] sumAny(doubles) = " + sumAny(doubles));

        List<Number> numbers = new ArrayList<>();
        addInts(numbers, 3);
        System.out.println("[4] filled Number list = " + numbers);

        // ---------------------------------------------------------------
        // 5. Wildcard <?> - "some unknown type, I don't care"
        //    You can READ Object; you cannot ADD (except null).
        // ---------------------------------------------------------------
        List<?> anything = List.of(1, "two", 3.0);
        for (Object o : anything) System.out.print(o + " ");
        System.out.println("<-- [5] iterated as Object");

        // ---------------------------------------------------------------
        // 6. Comparator with PECS - accept a comparator for T or any supertype
        // ---------------------------------------------------------------
        //   <T> void sort(List<T> list, Comparator<? super T> c)   <-- consumer super
        List<Integer> ns = new ArrayList<>(List.of(5, 3, 1, 4, 2));
        Comparator<Number> byIntValue = Comparator.comparingInt(Number::intValue);
        ns.sort(byIntValue);  // Comparator<Number> works for List<Integer>
        System.out.println("[6] sorted = " + ns);

        // ---------------------------------------------------------------
        // 7. Type erasure demo
        //    Both lists have the SAME class at runtime (List). This works:
        // ---------------------------------------------------------------
        List<Integer> l1 = List.of(1);
        List<String>  l2 = List.of("a");
        System.out.println("[7] same runtime class? " + (l1.getClass() == l2.getClass()));

        // But this does NOT compile:
        //   if (l1 instanceof List<String>) { ... }
        // You can only do:
        System.out.println("[7] instanceof List = " + (l1 instanceof List<?>));

        // ---------------------------------------------------------------
        // 8. Arrays and generics don't mix well
        //    You CANNOT do:  T[] arr = new T[10];
        //    Workaround: pass a Class<T> or use a List<T> instead.
        // ---------------------------------------------------------------
        Integer[] arr = new Integer[5];
        Arrays.fill(arr, 1);
        System.out.println("[8] Arrays.fill works because Integer[] is a concrete type: " + Arrays.toString(arr));
    }
}
