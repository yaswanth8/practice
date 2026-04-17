package com.careerit.java.java13;

/**
 * Java 13 - Text blocks (preview; standard in Java 15).
 *
 * Use """...""" for multi-line string literals. Leading incidental whitespace
 * is stripped based on the least-indented line. Trailing whitespace on each
 * line is removed.
 *
 * Escape sequences introduced with text blocks:
 *   \<newline>   : suppresses the line terminator (line continuation)
 *   \s           : a single space that survives trailing-whitespace stripping
 */
public class TextBlocksPreview {

    public static void main(String[] args) {
        String json = """
                {
                  "name": "Ravi",
                  "role": "engineer",
                  "skills": ["java", "spring"]
                }
                """;
        System.out.println(json);

        String query = """
                SELECT id, name, email
                FROM   users
                WHERE  active = true
                ORDER  BY id
                """;
        System.out.println(query);

        String continued = """
                one \
                line\
                """;
        System.out.println("continued = [" + continued + "]");
    }
}
