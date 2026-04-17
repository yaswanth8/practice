package com.careerit.java.java16;

import java.util.List;

/**
 * Java 16 - Records become a permanent feature (JEP 395).
 *
 * Also in Java 16: pattern matching for instanceof is final, Stream.toList()
 * is added, and local records/enums/interfaces are allowed inside methods.
 */
public class RecordsFinalized {

    record Money(long amount, String currency) {
        public Money {
            if (amount < 0) throw new IllegalArgumentException("negative");
            currency = currency.toUpperCase();
        }
        public Money add(Money other) {
            if (!currency.equals(other.currency))
                throw new IllegalArgumentException("mismatched currency");
            return new Money(amount + other.amount, currency);
        }
    }

    public static void main(String[] args) {
        // Local record inside a method (allowed since Java 16).
        record Sale(String item, Money price) {}

        List<Sale> sales = List.of(
                new Sale("book",  new Money(500,  "inr")),
                new Sale("pen",   new Money(100,  "inr")),
                new Sale("mouse", new Money(1200, "inr")));

        Money total = sales.stream()
                .map(Sale::price)
                .reduce(new Money(0, "INR"), Money::add);
        System.out.println("total = " + total);
    }
}
