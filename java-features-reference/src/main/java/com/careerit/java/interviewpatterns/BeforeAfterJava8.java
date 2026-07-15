package com.careerit.java.interviewpatterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ============================================================================
 *  BEFORE/AFTER - how modern Java shortens everyday code
 * ============================================================================
 *
 *  A favorite interview question: "Show me how you would do X in Java 7 vs.
 *  how you would do it today." Each block below has the pre-Java 8 version
 *  and the modern version so you can see the delta.
 * ============================================================================
 */
public class BeforeAfterJava8 {

    record Person(String name, int age) {}

    public static void main(String[] args) {

        List<Person> people = List.of(
                new Person("Ravi", 30),
                new Person("Asha", 35),
                new Person("Kiran", 28),
                new Person("Mira", 40));

        // ---------------------------------------------------------------
        // 1. Sort by age
        // ---------------------------------------------------------------
        List<Person> copy1 = new ArrayList<>(people);
        //  BEFORE
        Collections.sort(copy1, new Comparator<Person>() {
            @Override public int compare(Person a, Person b) { return a.age() - b.age(); }
        });
        //  AFTER
        List<Person> copy2 = new ArrayList<>(people);
        copy2.sort(Comparator.comparingInt(Person::age));

        System.out.println("[1] sorted = " + copy2);

        // ---------------------------------------------------------------
        // 2. Filter adults and collect names
        // ---------------------------------------------------------------
        //  BEFORE
        List<String> namesBefore = new ArrayList<>();
        for (Person p : people) {
            if (p.age() >= 30) namesBefore.add(p.name());
        }

        //  AFTER
        List<String> namesAfter = people.stream()
                .filter(p -> p.age() >= 30)
                .map(Person::name)
                .collect(Collectors.toList());
        System.out.println("[2] before = " + namesBefore);
        System.out.println("[2] after  = " + namesAfter);

        // ---------------------------------------------------------------
        // 3. Build a map name -> age
        // ---------------------------------------------------------------
        //  BEFORE
        Map<String, Integer> ageByNameBefore = new HashMap<>();
        for (Person p : people) ageByNameBefore.put(p.name(), p.age());

        //  AFTER
        Map<String, Integer> ageByNameAfter = people.stream()
                .collect(Collectors.toMap(Person::name, Person::age));
        System.out.println("[3] after = " + ageByNameAfter);

        // ---------------------------------------------------------------
        // 4. Remove duplicates while preserving order
        // ---------------------------------------------------------------
        List<String> raw = List.of("a", "b", "a", "c", "b", "d");

        //  BEFORE
        List<String> distinctBefore = new ArrayList<>();
        for (String s : raw) if (!distinctBefore.contains(s)) distinctBefore.add(s);

        //  AFTER
        List<String> distinctAfter = raw.stream().distinct().collect(Collectors.toList());
        System.out.println("[4] distinct = " + distinctAfter);

        // ---------------------------------------------------------------
        // 5. Group by age bucket (under 30 / 30+)
        // ---------------------------------------------------------------
        //  BEFORE
        Map<String, List<Person>> byBucketBefore = new HashMap<>();
        for (Person p : people) {
            String key = p.age() < 30 ? "young" : "older";
            byBucketBefore.computeIfAbsent(key, k -> new ArrayList<>()).add(p);
        }

        //  AFTER
        Map<String, List<Person>> byBucketAfter = people.stream()
                .collect(Collectors.groupingBy(p -> p.age() < 30 ? "young" : "older"));
        System.out.println("[5] after = " + byBucketAfter);

        // ---------------------------------------------------------------
        // 6. Iterate and remove (in-place)
        // ---------------------------------------------------------------
        //  BEFORE
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        for (Iterator<Integer> it = nums.iterator(); it.hasNext(); ) {
            if (it.next() % 2 == 0) it.remove();
        }

        //  AFTER - removeIf (Java 8)
        List<Integer> nums2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        nums2.removeIf(n -> n % 2 == 0);

        System.out.println("[6] before=" + nums + " after=" + nums2);

        // ---------------------------------------------------------------
        // 7. Try-with-resources (Java 7) vs explicit close (Java 6)
        // ---------------------------------------------------------------
        // Java 7+:
        //   try (var reader = Files.newBufferedReader(path)) { ... }
        // Resources are closed automatically, in reverse order.
        System.out.println("[7] use try-with-resources for AutoCloseable");

        // ---------------------------------------------------------------
        // 8. Null-safe chaining - Optional vs manual null checks
        // ---------------------------------------------------------------
        record Address(String city) {}
        record User(String name, Address address) {}

        User userA = new User("Ravi", new Address("Hyderabad"));
        User userB = new User("Asha", null);

        //  BEFORE - deep null checks
        String cityBefore;
        if (userB != null && userB.address() != null && userB.address().city() != null) {
            cityBefore = userB.address().city();
        } else {
            cityBefore = "unknown";
        }

        //  AFTER - Optional chain
        String cityAfter = java.util.Optional.ofNullable(userB)
                .map(User::address)
                .map(Address::city)
                .orElse("unknown");

        System.out.println("[8] before=" + cityBefore + " after=" + cityAfter);
        System.out.println("[8] userA city = " + java.util.Optional.ofNullable(userA)
                .map(User::address).map(Address::city).orElse("unknown"));
    }
}
