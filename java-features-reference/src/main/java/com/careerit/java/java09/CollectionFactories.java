package com.careerit.java.java09;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Java 9 - Immutable collection factory methods.
 *
 *   List.of(...), Set.of(...), Map.of(k,v,...), Map.ofEntries(entry, entry, ...)
 *
 * Differences from Collections.unmodifiableList(new ArrayList<>(...)):
 *   - null elements/keys/values are rejected
 *   - duplicate set elements / map keys throw IllegalArgumentException at creation
 *   - structurally immutable (no set/add/remove)
 *   - space-efficient (specialized classes for small sizes)
 */
public class CollectionFactories {

    public static void main(String[] args) {
        List<String> list = List.of("a", "b", "c");
        Set<Integer> set   = Set.of(1, 2, 3);
        Map<String, Integer> map = Map.of("one", 1, "two", 2);
        Map<String, Integer> bigMap = Map.ofEntries(
                Map.entry("jan", 1), Map.entry("feb", 2), Map.entry("mar", 3));

        System.out.println(list);
        System.out.println(set);
        System.out.println(map);
        System.out.println(bigMap);

        try {
            list.add("d");
        } catch (UnsupportedOperationException e) {
            System.out.println("immutable: cannot add");
        }
    }
}
