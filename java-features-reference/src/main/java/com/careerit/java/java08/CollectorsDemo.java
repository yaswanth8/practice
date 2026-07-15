package com.careerit.java.java08;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Java 8 - Collectors cheat sheet.
 *
 *   toList / toSet / toMap
 *   joining(delim, prefix, suffix)
 *   counting / summingInt / averagingDouble
 *   groupingBy(classifier, downstream)
 *   partitioningBy(predicate)
 *   mapping / reducing / collectingAndThen
 */
public class CollectorsDemo {

    record Txn(String user, String type, double amount) {}

    public static void main(String[] args) {
        List<Txn> txns = List.of(
                new Txn("ravi",  "credit", 100),
                new Txn("ravi",  "debit",   40),
                new Txn("asha",  "credit", 250),
                new Txn("asha",  "debit",   20),
                new Txn("kiran", "credit",  10));

        String joined = txns.stream().map(Txn::user).distinct()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("users = " + joined);

        Map<String, Double> totalByUser = txns.stream()
                .collect(Collectors.groupingBy(Txn::user, Collectors.summingDouble(Txn::amount)));
        System.out.println("totalByUser = " + totalByUser);

        Map<Boolean, Long> creditVsDebit = txns.stream()
                .collect(Collectors.partitioningBy(t -> t.type().equals("credit"), Collectors.counting()));
        System.out.println("creditVsDebit = " + creditVsDebit);

        Map<String, List<Double>> amountsByUser = txns.stream()
                .collect(Collectors.groupingBy(Txn::user,
                        Collectors.mapping(Txn::amount, Collectors.toList())));
        System.out.println("amountsByUser = " + amountsByUser);

        TreeMap<String, Long> sortedCount = txns.stream()
                .collect(Collectors.groupingBy(Txn::user, TreeMap::new, Collectors.counting()));
        System.out.println("sortedCount = " + sortedCount);

        List<String> users = txns.stream()
                .map(Txn::user)
                .distinct()
                .collect(Collectors.collectingAndThen(Collectors.toList(), List::copyOf));
        System.out.println("immutable users = " + users);
    }
}
