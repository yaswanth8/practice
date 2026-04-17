package com.careerit.java.java21;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * Java 21 - Sequenced Collections (JEP 431).
 *
 * Three new interfaces unify the "has an order" concept:
 *   SequencedCollection<E> adds: addFirst / addLast / getFirst / getLast /
 *                                removeFirst / removeLast / reversed()
 *   SequencedSet<E>         extends it
 *   SequencedMap<K,V>       adds: firstEntry / lastEntry / putFirst / putLast /
 *                                  pollFirstEntry / pollLastEntry / reversed / sequencedKeySet / ...
 *
 * Existing types retrofit them: List, Deque, LinkedHashSet, LinkedHashMap, TreeSet, TreeMap.
 */
public class SequencedCollections {

    public static void main(String[] args) {
        SequencedCollection<String> list = new ArrayList<>(List.of("b", "c"));
        list.addFirst("a");
        list.addLast("d");
        System.out.println("list = " + list);
        System.out.println("first=" + list.getFirst() + " last=" + list.getLast());
        System.out.println("reversed = " + list.reversed());

        SequencedSet<String> set = new LinkedHashSet<>();
        set.add("y"); set.add("x"); set.addFirst("z");
        System.out.println("set = " + set + " reversed=" + set.reversed());

        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1); map.put("two", 2); map.putFirst("zero", 0);
        System.out.println("map = " + map + " first=" + map.firstEntry() + " last=" + map.lastEntry());
    }
}
