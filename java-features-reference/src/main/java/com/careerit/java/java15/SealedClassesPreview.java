package com.careerit.java.java15;

/**
 * Java 15 - Sealed classes/interfaces (preview; standard in Java 17).
 *
 * `sealed` restricts which classes/interfaces may extend or implement it.
 * Permitted subtypes must be declared with `final`, `sealed`, or `non-sealed`.
 *
 * Sealed + records = algebraic data types, and the compiler can verify
 * exhaustiveness of switch expressions over the type hierarchy.
 */
public class SealedClassesPreview {

    sealed interface Shape permits Circle, Square, Triangle {}
    record Circle(double r) implements Shape {}
    record Square(double side) implements Shape {}
    record Triangle(double base, double height) implements Shape {}

    static double area(Shape s) {
        return switch (s) {
            case Circle c      -> Math.PI * c.r() * c.r();
            case Square sq     -> sq.side() * sq.side();
            case Triangle t    -> 0.5 * t.base() * t.height();
        };
    }

    public static void main(String[] args) {
        System.out.println(area(new Circle(2)));
        System.out.println(area(new Square(3)));
        System.out.println(area(new Triangle(4, 5)));
    }
}
