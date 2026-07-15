package com.careerit.java.java08;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * ============================================================================
 *  Java 8 : STREAM API - PATTERNS FROM REAL INTERVIEWS
 * ============================================================================
 *
 *  A collection of small, self-contained problems you're likely to be asked.
 *  Each one is labelled with the pattern that solves it.
 * ============================================================================
 */
public class StreamAdvanced {

    record Order(String customer, String product, String category, double amount) {}

    static final List<Order> ORDERS = List.of(
            new Order("Ravi",  "Laptop",   "Electronics", 1200),
            new Order("Ravi",  "Mouse",    "Electronics",   25),
            new Order("Asha",  "Book",     "Books",         40),
            new Order("Asha",  "Headset",  "Electronics",  150),
            new Order("Kiran", "Book",     "Books",         30),
            new Order("Kiran", "Book",     "Books",         30),
            new Order("Mira",  "Notebook", "Stationery",    12));

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // Problem 1: total spend per customer
        // Pattern: groupingBy + summingDouble
        // ---------------------------------------------------------------
        Map<String, Double> totalPerCustomer = ORDERS.stream()
                .collect(Collectors.groupingBy(
                        Order::customer,
                        Collectors.summingDouble(Order::amount)));
        System.out.println("[1] totalPerCustomer = " + totalPerCustomer);

        // ---------------------------------------------------------------
        // Problem 2: top customer (highest total spend)
        // Pattern: groupingBy + summingDouble + max by value
        // ---------------------------------------------------------------
        String topCustomer = totalPerCustomer.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("n/a");
        System.out.println("[2] topCustomer = " + topCustomer);

        // ---------------------------------------------------------------
        // Problem 3: count orders per category
        // Pattern: groupingBy + counting
        // ---------------------------------------------------------------
        Map<String, Long> ordersPerCategory = ORDERS.stream()
                .collect(Collectors.groupingBy(Order::category, Collectors.counting()));
        System.out.println("[3] ordersPerCategory = " + ordersPerCategory);

        // ---------------------------------------------------------------
        // Problem 4: customers who bought from more than one category
        // Pattern: groupingBy + mapping + toSet + filter
        // ---------------------------------------------------------------
        Map<String, Set<String>> catsPerCustomer = ORDERS.stream()
                .collect(Collectors.groupingBy(
                        Order::customer,
                        Collectors.mapping(Order::category, Collectors.toSet())));

        List<String> diverseShoppers = catsPerCustomer.entrySet().stream()
                .filter(e -> e.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("[4] diverseShoppers = " + diverseShoppers);

        // ---------------------------------------------------------------
        // Problem 5: frequency count of "product" words
        // Pattern: groupingBy(identity) + counting
        // ---------------------------------------------------------------
        Map<String, Long> wordFreq = ORDERS.stream()
                .map(Order::product)
                .collect(Collectors.groupingBy(java.util.function.Function.identity(),
                        Collectors.counting()));
        System.out.println("[5] wordFreq = " + wordFreq);

        // ---------------------------------------------------------------
        // Problem 6: most expensive order per category
        // Pattern: groupingBy + collectingAndThen + maxBy
        // ---------------------------------------------------------------
        Map<String, Order> maxPerCat = ORDERS.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Order::amount)),
                                opt -> opt.orElseThrow())));
        System.out.println("[6] maxPerCat = " + maxPerCat);

        // ---------------------------------------------------------------
        // Problem 7: sorted output - TreeMap grouping
        // Pattern: groupingBy(classifier, TreeMap::new, downstream)
        // ---------------------------------------------------------------
        TreeMap<String, Double> totalPerCategorySorted = ORDERS.stream()
                .collect(Collectors.groupingBy(
                        Order::category,
                        TreeMap::new,
                        Collectors.summingDouble(Order::amount)));
        System.out.println("[7] totalPerCategorySorted = " + totalPerCategorySorted);

        // ---------------------------------------------------------------
        // Problem 8: CSV-style output
        // Pattern: joining(delimiter, prefix, suffix)
        // ---------------------------------------------------------------
        String csv = ORDERS.stream()
                .map(Order::product)
                .distinct()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("[8] csv = " + csv);

        // ---------------------------------------------------------------
        // Problem 9: partition into free-shipping-eligible (amount >= 50)
        // Pattern: partitioningBy
        // ---------------------------------------------------------------
        Map<Boolean, List<Order>> eligible = ORDERS.stream()
                .collect(Collectors.partitioningBy(o -> o.amount() >= 50));
        System.out.println("[9] free-shipping = " + eligible.get(true));
        System.out.println("[9] paid-shipping = " + eligible.get(false));

        // ---------------------------------------------------------------
        // Problem 10: dedupe by a field (keep first) - common trick
        // Pattern: toMap with merge function
        // ---------------------------------------------------------------
        Map<String, Order> firstPerCustomer = ORDERS.stream()
                .collect(Collectors.toMap(
                        Order::customer,
                        o -> o,
                        (first, second) -> first,   // keep first
                        java.util.LinkedHashMap::new));
        System.out.println("[10] firstPerCustomer = " + firstPerCustomer);
    }
}
