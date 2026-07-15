package com.careerit.java.interviewpatterns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * ============================================================================
 *  equals / hashCode / Comparable - the classic interview topic
 * ============================================================================
 *
 *  THE CONTRACT
 *  ------------
 *   1. Reflexive:    x.equals(x)   == true
 *   2. Symmetric:    x.equals(y)   == y.equals(x)
 *   3. Transitive:   x==y, y==z    =>  x==z
 *   4. Consistent:   same result for repeated calls
 *   5. Null-safe:    x.equals(null) == false
 *   6. Hash rule:    x.equals(y)   =>  x.hashCode() == y.hashCode()
 *
 *  BREAK RULE 6 -> HashMap / HashSet lookups silently misbehave.
 *
 *  THIS FILE DEMONSTRATES
 *  ----------------------
 *   1. What happens without equals/hashCode (default identity behaviour).
 *   2. Correct implementation (manual).
 *   3. Correct implementation using Objects.equals / Objects.hash.
 *   4. Records give it to you for FREE.
 *   5. Common mistakes.
 * ============================================================================
 */
public class EqualsHashCode {

    // ---------------- 1. Broken: uses default Object.equals (identity) ----
    static class BrokenUser {
        final String id;
        BrokenUser(String id) { this.id = id; }
    }

    // ---------------- 2. Manual equals/hashCode ---------------------------
    static class ManualUser {
        final String id;
        final String email;
        ManualUser(String id, String email) { this.id = id; this.email = email; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;                         // reflexive fast-path
            if (!(o instanceof ManualUser other)) return false; // null-safe + type check
            return id.equals(other.id) && email.equals(other.email);
        }

        @Override
        public int hashCode() {
            int h = 17;
            h = 31 * h + id.hashCode();
            h = 31 * h + email.hashCode();
            return h;
        }

        @Override public String toString() { return "ManualUser(" + id + ", " + email + ")"; }
    }

    // ---------------- 3. Same, but idiomatic with java.util.Objects -------
    static class IdiomaticUser {
        final String id;
        final String email;
        IdiomaticUser(String id, String email) { this.id = id; this.email = email; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o instanceof IdiomaticUser u
                    && Objects.equals(id, u.id)
                    && Objects.equals(email, u.email);
        }

        @Override public int hashCode() { return Objects.hash(id, email); }
        @Override public String toString() { return "IdiomaticUser(" + id + ", " + email + ")"; }
    }

    // ---------------- 4. Records: free equals/hashCode/toString -----------
    record RecordUser(String id, String email) {}

    // ---------------- 5. Common mistake: equals but no hashCode -----------
    static class MistakeUser {
        final String id;
        MistakeUser(String id) { this.id = id; }
        @Override public boolean equals(Object o) {
            return o instanceof MistakeUser m && id.equals(m.id);
        }
        // NO hashCode! => equal-by-value objects will land in different buckets.
    }

    public static void main(String[] args) {

        // ---------------------------------------------------------------
        // 1. Default equals = identity => two logically-equal objects are NOT equal
        // ---------------------------------------------------------------
        Set<BrokenUser> broken = new HashSet<>();
        broken.add(new BrokenUser("u1"));
        broken.add(new BrokenUser("u1"));
        System.out.println("[1] broken set size = " + broken.size() + " (expected 1)");

        // ---------------------------------------------------------------
        // 2. Manual - equals correct, hashCode correct => works
        // ---------------------------------------------------------------
        Set<ManualUser> manual = new HashSet<>();
        manual.add(new ManualUser("u1", "a@a"));
        manual.add(new ManualUser("u1", "a@a"));
        System.out.println("[2] manual set size = " + manual.size() + " (expected 1)");

        // ---------------------------------------------------------------
        // 3. Idiomatic with Objects.hash - same result, shorter code
        // ---------------------------------------------------------------
        Map<IdiomaticUser, String> idiomatic = new HashMap<>();
        idiomatic.put(new IdiomaticUser("u1", "a@a"), "found");
        String hit = idiomatic.get(new IdiomaticUser("u1", "a@a"));
        System.out.println("[3] idiomatic lookup = " + hit);

        // ---------------------------------------------------------------
        // 4. Records - zero boilerplate
        // ---------------------------------------------------------------
        Set<RecordUser> records = new HashSet<>();
        records.add(new RecordUser("u1", "a@a"));
        records.add(new RecordUser("u1", "a@a"));
        System.out.println("[4] record set size = " + records.size() + " (expected 1)");

        // ---------------------------------------------------------------
        // 5. Broken contract: overrode equals but NOT hashCode
        //    Two "equal" objects land in different buckets - HashSet fails.
        // ---------------------------------------------------------------
        Set<MistakeUser> bad = new HashSet<>();
        MistakeUser a = new MistakeUser("u1");
        MistakeUser b = new MistakeUser("u1");
        bad.add(a);
        System.out.println("[5] a.equals(b)? " + a.equals(b));
        System.out.println("[5] set.contains(b)? " + bad.contains(b)
                + "   (returns false because hashCode differs!)");

        // ---------------------------------------------------------------
        // 6. Comparable - natural ordering
        // ---------------------------------------------------------------
        record Age(int years) implements Comparable<Age> {
            @Override
            public int compareTo(Age other) {
                return Integer.compare(this.years, other.years);
            }
        }
        java.util.List<Age> ages = new java.util.ArrayList<>(java.util.List.of(new Age(40), new Age(10), new Age(25)));
        java.util.Collections.sort(ages);
        System.out.println("[6] sorted by natural order = " + ages);

        // ---------------------------------------------------------------
        // 7. Comparable vs Comparator - one class, many orderings
        // ---------------------------------------------------------------
        record Employee(String name, double salary) {}
        java.util.List<Employee> emps = new java.util.ArrayList<>(java.util.List.of(
                new Employee("Ravi", 90), new Employee("Asha", 150), new Employee("Kiran", 60)));
        emps.sort(java.util.Comparator.comparing(Employee::name));
        System.out.println("[7] byName    = " + emps);
        emps.sort(java.util.Comparator.comparingDouble(Employee::salary).reversed());
        System.out.println("[7] bySalary  = " + emps);
    }
}
