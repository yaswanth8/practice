package com.careerit.java.java17;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * Java 17 - Enhanced pseudo-random number generators (JEP 356).
 *
 * A unified interface RandomGenerator replaces the ad-hoc Random / SplittableRandom /
 * ThreadLocalRandom APIs. You can pick an algorithm by name and get well-defined
 * statistical properties.
 */
public class RandomGenerators {

    public static void main(String[] args) {
        RandomGenerator rng = RandomGeneratorFactory.of("L64X128MixRandom").create(42L);
        rng.ints(5, 0, 100).forEach(i -> System.out.print(i + " "));
        System.out.println();

        RandomGeneratorFactory.all()
                .limit(5)
                .forEach(f -> System.out.println(f.name() + " period=" + f.period()));
    }
}
