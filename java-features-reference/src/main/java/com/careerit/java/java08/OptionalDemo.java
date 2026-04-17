package com.careerit.java.java08;

import java.util.Optional;

/**
 * Java 8 - Optional<T>.
 *
 * Optional is a container that either holds a value or is empty. It was designed
 * as a method return type to make "absence" explicit, so callers don't forget to
 * handle null.
 *
 * Do NOT use Optional for fields, method parameters, or collection elements.
 * Do NOT call .get() without checking isPresent() - prefer orElse/orElseGet/orElseThrow.
 *
 * See also Java 9 additions: ifPresentOrElse, or, stream.
 */
public class OptionalDemo {

    record User(String name, String email) {}

    static Optional<User> findUser(String id) {
        return "u1".equals(id) ? Optional.of(new User("Ravi", "ravi@example.com"))
                               : Optional.empty();
    }

    public static void main(String[] args) {
        Optional<User> u = findUser("u1");

        u.ifPresent(user -> System.out.println("found: " + user));

        String email = findUser("u-missing")
                .map(User::email)
                .orElse("no-email@example.com");
        System.out.println("email = " + email);

        String nameOrThrow = findUser("u1")
                .map(User::name)
                .orElseThrow(() -> new IllegalStateException("user not found"));
        System.out.println("name = " + nameOrThrow);

        Optional<String> value = Optional.ofNullable(System.getenv("NON_EXISTENT_VAR"));
        System.out.println("value present? " + value.isPresent());

        String computed = value.orElseGet(() -> expensiveDefault());
        System.out.println("computed = " + computed);
    }

    private static String expensiveDefault() {
        return "lazily-computed-default";
    }
}
