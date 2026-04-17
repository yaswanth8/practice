package com.careerit.java.java14;

/**
 * Java 14 - Helpful NullPointerExceptions (JEP 358).
 *
 * When an NPE is thrown, the JVM adds a message that pinpoints which
 * variable/field was null in a chained expression.
 *
 * Run with: java -XX:+ShowCodeDetailsInExceptionMessages ... (on by default in 15+)
 *
 * Example: a.b.c.d can now say
 *   "Cannot invoke ... because the return value of 'A.b()' is null"
 * instead of just "NullPointerException".
 */
public class HelpfulNpe {

    record Address(String city) {}
    record User(String name, Address address) {}

    public static void main(String[] args) {
        User u = new User("Ravi", null);
        try {
            int len = u.address().city().length();
            System.out.println(len);
        } catch (NullPointerException e) {
            System.out.println("NPE: " + e.getMessage());
        }
    }
}
