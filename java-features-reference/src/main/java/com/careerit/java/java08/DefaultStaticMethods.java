package com.careerit.java.java08;

/**
 * Java 8 - default and static methods on interfaces.
 *
 * Before Java 8, adding a method to a published interface broke every implementation.
 * `default` methods supply a body, so interfaces can evolve without breaking clients.
 * `static` methods give interfaces a natural home for helper/factory methods.
 *
 * Diamond rule: when two interfaces provide the same default method, the implementing
 * class MUST override it (use Interface.super.method() to pick one).
 */
public class DefaultStaticMethods {

    interface Greeter {
        String name();

        default String greet() {
            return "Hello, " + name();
        }

        static Greeter of(String n) {
            return () -> n;
        }
    }

    interface Logger {
        default String greet() {
            return "[log] greet called";
        }
    }

    static class Friendly implements Greeter, Logger {
        private final String n;
        Friendly(String n) { this.n = n; }
        @Override public String name() { return n; }

        @Override
        public String greet() {
            return Greeter.super.greet() + " | " + Logger.super.greet();
        }
    }

    public static void main(String[] args) {
        Greeter g = Greeter.of("Ravi");
        System.out.println(g.greet());

        Friendly f = new Friendly("Asha");
        System.out.println(f.greet());
    }
}
