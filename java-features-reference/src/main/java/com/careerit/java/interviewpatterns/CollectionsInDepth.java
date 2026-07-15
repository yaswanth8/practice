package com.careerit.java.interviewpatterns;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ============================================================================
 *  COLLECTIONS - INTERVIEW-READY TOUR
 * ============================================================================
 *
 *  This file is a "which collection when?" reference. Run it to see behaviour.
 *
 *  The hierarchy you must be able to draw on a whiteboard:
 *
 *    Collection
 *     |-- List       (ordered, index access, allows duplicates)
 *     |    |-- ArrayList     : resizable array, best default
 *     |    |-- LinkedList    : doubly-linked list, rarely the right pick
 *     |
 *     |-- Set        (no duplicates)
 *     |    |-- HashSet       : unordered, O(1)
 *     |    |-- LinkedHashSet : insertion-ordered, O(1)
 *     |    |-- TreeSet       : sorted, O(log n)
 *     |
 *     |-- Queue / Deque
 *          |-- ArrayDeque    : stack + queue, preferred over Stack/LinkedList
 *          |-- PriorityQueue : min-heap by default
 *
 *    Map (NOT a Collection)
 *     |-- HashMap       : unordered, O(1)
 *     |-- LinkedHashMap : insertion-ordered, O(1)
 *     |-- TreeMap       : sorted by key, O(log n)
 *     |-- ConcurrentHashMap : thread-safe
 *
 *  Rule of thumb: if you don't have a reason, pick ArrayList / HashSet / HashMap.
 * ============================================================================
 */
public class CollectionsInDepth {

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. ArrayList vs LinkedList - short demo
        //    Both implement List. ArrayList is nearly always faster in practice.
        // ---------------------------------------------------------------
        List<Integer> arr = new ArrayList<>();
        List<Integer> lnk = new LinkedList<>();
        for (int i = 0; i < 100_000; i++) { arr.add(i); lnk.add(i); }

        long tA = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) arr.get(i);
        long randA = System.currentTimeMillis() - tA;

        long tL = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) lnk.get(i);
        long randL = System.currentTimeMillis() - tL;
        System.out.println("[1] random get: ArrayList=" + randA + "ms, LinkedList=" + randL + "ms");

        // ---------------------------------------------------------------
        // 2. HashSet vs LinkedHashSet vs TreeSet - ordering
        // ---------------------------------------------------------------
        Set<String> hs  = new HashSet<>(List.of("b", "a", "c"));
        Set<String> lhs = new LinkedHashSet<>(List.of("b", "a", "c"));
        Set<String> ts  = new TreeSet<>(List.of("b", "a", "c"));
        System.out.println("[2] HashSet         = " + hs);
        System.out.println("[2] LinkedHashSet   = " + lhs);
        System.out.println("[2] TreeSet         = " + ts);

        // ---------------------------------------------------------------
        // 3. Map variants - ordering
        // ---------------------------------------------------------------
        Map<String, Integer> hm  = new HashMap<>();
        Map<String, Integer> lhm = new LinkedHashMap<>();
        Map<String, Integer> tm  = new TreeMap<>();
        List.of("b","a","c").forEach(k -> { hm.put(k,1); lhm.put(k,1); tm.put(k,1); });
        System.out.println("[3] HashMap         = " + hm);
        System.out.println("[3] LinkedHashMap   = " + lhm);
        System.out.println("[3] TreeMap         = " + tm);

        // ---------------------------------------------------------------
        // 4. Queue / Deque - stack and queue in one class
        // ---------------------------------------------------------------
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1); stack.push(2); stack.push(3);
        System.out.println("[4] stack pop = " + stack.pop());   // 3 (LIFO)

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(1); q.offer(2); q.offer(3);
        System.out.println("[4] queue poll= " + q.poll());      // 1 (FIFO)

        // ---------------------------------------------------------------
        // 5. PriorityQueue - min-heap by default; use comparator for max-heap
        // ---------------------------------------------------------------
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        List.of(5, 2, 8, 1, 6).forEach(minHeap::offer);
        System.out.print("[5] min-heap order: ");
        while (!minHeap.isEmpty()) System.out.print(minHeap.poll() + " ");
        System.out.println();

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        List.of(5, 2, 8, 1, 6).forEach(maxHeap::offer);
        System.out.print("[5] max-heap order: ");
        while (!maxHeap.isEmpty()) System.out.print(maxHeap.poll() + " ");
        System.out.println();

        // ---------------------------------------------------------------
        // 6. TreeMap operations you don't get from HashMap
        // ---------------------------------------------------------------
        TreeMap<Integer, String> scores = new TreeMap<>(Map.of(10, "a", 20, "b", 30, "c"));
        System.out.println("[6] firstKey    = " + scores.firstKey());
        System.out.println("[6] lastKey     = " + scores.lastKey());
        System.out.println("[6] floorKey(25)= " + scores.floorKey(25));
        System.out.println("[6] ceilingKey(25) = " + scores.ceilingKey(25));
        System.out.println("[6] headMap(<20)= " + scores.headMap(20));
        System.out.println("[6] tailMap(>=20)= " + scores.tailMap(20));

        // ---------------------------------------------------------------
        // 7. Fail-fast iterators - ConcurrentModificationException demo
        // ---------------------------------------------------------------
        List<Integer> data = new ArrayList<>(List.of(1, 2, 3, 4));
        try {
            for (Integer i : data) {
                if (i == 2) data.remove(i);        // structural mod during iteration
            }
        } catch (Exception e) {
            System.out.println("[7] fail-fast: " + e.getClass().getSimpleName());
        }

        // Correct way: iterator.remove() or removeIf
        data.removeIf(i -> i == 2);
        System.out.println("[7] after removeIf = " + data);

        // ---------------------------------------------------------------
        // 8. Thread-safe collections
        // ---------------------------------------------------------------
        Map<String, Integer> cmap = new ConcurrentHashMap<>();
        cmap.computeIfAbsent("k", k -> 42);        // atomic
        cmap.merge("k", 1, Integer::sum);          // atomic
        System.out.println("[8] concurrent map = " + cmap);

        List<String> cowl = new CopyOnWriteArrayList<>(List.of("a", "b"));
        // Iterating while modifying is safe here (snapshot semantics).
        Iterator<String> it = cowl.iterator();
        cowl.add("c");
        while (it.hasNext()) System.out.print(it.next() + " ");
        System.out.println("  <-- snapshot from before add");

        // ---------------------------------------------------------------
        // 9. Unmodifiable views vs immutable copies
        // ---------------------------------------------------------------
        List<Integer> src   = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> view  = Collections.unmodifiableList(src);
        List<Integer> copy  = List.copyOf(src);
        src.add(99);
        System.out.println("[9] view (changes) = " + view);
        System.out.println("[9] copy (frozen)  = " + copy);

        // ---------------------------------------------------------------
        // 10. Common Map idioms
        // ---------------------------------------------------------------
        Map<String, Integer> m = new HashMap<>();
        // count
        List.of("a", "b", "a", "c", "b", "a").forEach(k -> m.merge(k, 1, Integer::sum));
        // default get
        int v = m.getOrDefault("x", 0);
        // put-if-absent (also builds initial value)
        m.computeIfAbsent("new", k -> 100);
        System.out.println("[10] counts = " + m + ", default get x=" + v);
    }
}
