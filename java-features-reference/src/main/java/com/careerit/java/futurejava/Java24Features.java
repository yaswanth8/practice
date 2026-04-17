package com.careerit.java.futurejava;

/**
 * Java 24 - Reference notes (requires JDK 24+ to compile).
 *
 * Standard:
 *   JEP 485 - Stream Gatherers
 *     Permanent API for pluggable intermediate stream ops:
 *       var windowed = nums.stream().gather(Gatherers.windowFixed(3)).toList();
 *       var dedup    = nums.stream().gather(Gatherers.fold(...)).toList();
 *
 *   JEP 487 - Scoped Values (still preview as of 24 but stable API shape).
 *   JEP 491 - Synchronize Virtual Threads Without Pinning
 *     `synchronized` blocks no longer pin virtual threads to their carrier,
 *     removing a major virtual-thread footgun.
 *
 * Preview:
 *   JEP 488 - Primitive Types in Patterns, instanceof, switch (2nd preview).
 *   JEP 492 - Flexible Constructor Bodies (3rd preview).
 *   JEP 494 - Module Import Declarations (2nd preview).
 *   JEP 495 - Simple Source Files and Instance Main Methods (4th preview).
 *   JEP 499 - Structured Concurrency (4th preview).
 *
 * Security:
 *   JEP 493 - Linking Run-Time Images without JMODs.
 *   JEP 484 - Class-File API promoted.
 *   JEP 486 - Permanently Disable the Security Manager.
 */
public class Java24Features {
    public static void main(String[] args) {
        System.out.println("Java 24 reference notes - see source comments.");
    }
}
