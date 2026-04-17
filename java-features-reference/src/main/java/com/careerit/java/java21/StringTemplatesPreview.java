package com.careerit.java.java21;

/**
 * Java 21 - String Templates (preview, JEP 430).
 *
 * Syntax (preview): STR."Hello \{name}"
 * They let you embed expressions in string literals with a processor that
 * controls how the result is built. Examples:
 *   STR            : simple interpolation to String
 *   FMT (java.util.FormatProcessor) : printf-style formatting
 *
 * NOTE: This feature was withdrawn for redesign after Java 21/22. Because the
 * syntax uses a preview feature, code is shown here as a reference block inside
 * comments; the class is a placeholder so the module compiles under any JDK.
 */
public class StringTemplatesPreview {

    /*
      // Preview syntax (needs --enable-preview on Java 21/22):

      String name = "Ravi";
      int score   = 92;

      String greet = STR."Hello \{name}, your score is \{score}";
      // -> "Hello Ravi, your score is 92"

      String line  = FMT."[\{name}%10s] %03d\{score}";
      // printf-style formatting via the FMT processor
    */

    public static void main(String[] args) {
        String name = "Ravi";
        int score = 92;
        String greet = "Hello " + name + ", your score is " + score;
        System.out.println(greet + "  // (STR.\"...\" in Java 21 preview)");
    }
}
