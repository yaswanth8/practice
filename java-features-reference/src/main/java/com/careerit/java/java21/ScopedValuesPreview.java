package com.careerit.java.java21;

/**
 * Java 21 - Scoped Values (preview, JEP 446).
 *
 * Scoped values are an immutable, one-way alternative to ThreadLocal that
 * pair well with virtual threads + structured concurrency. A value is set for
 * the dynamic extent of a `ScopedValue.where(KEY, value).run(() -> {...})`
 * call; inside that block (and any thread it starts via StructuredTaskScope)
 * the value is visible; outside it, the binding is gone.
 *
 * Why not ThreadLocal?
 *   - mutable, pollutes worker threads in pools
 *   - expensive inheritance on many child threads
 *   - no clear "scope" — prone to leaks
 *
 * NOTE: API is `java.lang.ScopedValue`; still preview/incubating through 24.
 * Sketch below; actual API requires --enable-preview.
 */
public class ScopedValuesPreview {

    /*
      static final ScopedValue<String> USER = ScopedValue.newInstance();

      public static void main(String[] args) {
          ScopedValue.where(USER, "ravi").run(() -> {
              handleRequest();
          });
      }

      static void handleRequest() {
          System.out.println("handling as " + USER.get());
      }
    */

    public static void main(String[] args) {
        System.out.println("See source comments - ScopedValue requires preview flags.");
    }
}
