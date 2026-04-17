package com.careerit.java.futurejava;

/**
 * Java 23 - Reference notes (requires JDK 23+ to compile).
 *
 * Standard:
 *   JEP 467 - Markdown Documentation Comments
 *     Javadoc comments can now use Markdown:
 *       /// # Heading
 *       /// - bullet
 *       /// code: `example`
 *
 * Preview / Second previews:
 *   JEP 476 - Module Import Declarations
 *     `import module java.sql;` imports everything the module exports.
 *
 *   JEP 455 - Primitive Types in Patterns, instanceof, switch
 *     if (obj instanceof int i) { ... }      // unboxing pattern
 *     switch (obj) { case int i -> ...; case long l -> ...; }
 *
 *   JEP 482 - Flexible Constructor Bodies (renamed from JEP 447)
 *     Lets validation/preparation statements appear before super(...).
 *
 *   JEP 469 - Vector API (8th incubator).
 *   JEP 473 - Stream Gatherers (2nd preview).
 *   JEP 480 - Structured Concurrency (3rd preview).
 *   JEP 481 - Scoped Values (3rd preview).
 *   JEP 477 - Implicitly Declared Classes and Instance Main Methods (3rd preview).
 *
 * Notable removal:
 *   JEP 471 - Deprecate the Memory-Access Methods in sun.misc.Unsafe.
 */
public class Java23Features {
    public static void main(String[] args) {
        System.out.println("Java 23 reference notes - see source comments.");
    }
}
