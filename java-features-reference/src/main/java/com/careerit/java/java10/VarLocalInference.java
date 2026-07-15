package com.careerit.java.java10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Java 10 - `var` local-variable type inference.
 *
 * `var` can be used for:
 *   - local variables with initializer
 *   - loop indexes in for / enhanced for
 *   - indexes in try-with-resources
 *
 * `var` CANNOT be used for:
 *   - method parameters, return types, fields
 *   - without an initializer
 *   - with `null` literal (type inference fails)
 *   - lambda parameters (that is Java 11)
 *
 * Guideline: use `var` when the type is obvious from the RHS, or when the type
 * is noisy but irrelevant (long generic types). Do not use it to hide types
 * the reader needs.
 */
public class VarLocalInference {

    public static void main(String[] args) {
        var list = new ArrayList<String>();
        list.add("one");
        list.add("two");

        var map = Map.of("a", 1, "b", 2);

        for (var i = 0; i < list.size(); i++) {
            System.out.println(i + " -> " + list.get(i));
        }

        for (var entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        var lengths = list.stream().mapToInt(String::length).toArray();
        System.out.println("sum=" + java.util.Arrays.stream(lengths).sum());

        List<String> typedList = list;
        System.out.println(typedList);
    }
}
