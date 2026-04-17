package com.careerit.java.futurejava;

/**
 * Java 25 - Reference notes (LTS; requires JDK 25+ to compile).
 *
 * Java 25 is the next Long-Term Support (LTS) release after Java 21 and
 * rolls up the preview features that have been baking for several cycles.
 *
 * Permanent features:
 *   JEP 506 - Scoped Values
 *     Final form of ScopedValue.where(KEY, v).run(...).
 *
 *   JEP 511 - Module Import Declarations
 *     `import module java.sql;` now standard.
 *
 *   JEP 512 - Compact Source Files and Instance Main Methods
 *     Minimal program:
 *       void main() {
 *           IO.println("hi");
 *       }
 *     No class, no String[] args, no static. Ideal for teaching / scripts.
 *
 *   JEP 513 - Flexible Constructor Bodies
 *     Validation/prep statements before super(...) / this(...) are permanent.
 *
 *   JEP 519 - Compact Object Headers (production).
 *
 * Preview:
 *   JEP 507 - Primitive Types in Patterns, instanceof, switch (3rd preview).
 *   JEP 505 - Structured Concurrency (5th preview).
 *   JEP 502 - Stable Values (1st preview): lazy, one-shot, final-like values.
 *
 * Deprecations / removals:
 *   JEP 503 - Remove the 32-bit x86 port.
 */
public class Java25Features {
    public static void main(String[] args) {
        System.out.println("Java 25 reference notes - see source comments.");
    }
}
