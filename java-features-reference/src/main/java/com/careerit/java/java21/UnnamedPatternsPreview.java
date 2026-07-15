package com.careerit.java.java21;

/**
 * Java 21 - Unnamed patterns and variables (preview, JEP 443; standard in Java 22).
 *
 * Use `_` when a name is required by syntax but you don't care about the value.
 *
 * Locations:
 *   - record deconstruction:   case Point(_, int y) ->
 *   - pattern bindings:         case String _ ->
 *   - catch clauses:            catch (IOException _) -> ...
 *   - lambda parameters:        (x, _) -> x
 *   - for-each:                 for (var _ : list) { count++; }
 *
 * Sketch shown - real syntax requires --enable-preview on Java 21.
 */
public class UnnamedPatternsPreview {

    record Point(int x, int y) {}

    /*
      static int sumX(List<Point> pts) {
          int s = 0;
          for (Point(int x, _) : pts) s += x;   // ignoring y
          return s;
      }

      static String describe(Object o) {
          return switch (o) {
              case Integer _ -> "int";
              case String  _ -> "string";
              default        -> "other";
          };
      }
    */

    public static void main(String[] args) {
        System.out.println("See source comments - unnamed patterns require preview flags.");
    }
}
