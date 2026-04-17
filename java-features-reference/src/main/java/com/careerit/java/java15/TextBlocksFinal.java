package com.careerit.java.java15;

/**
 * Java 15 - Text blocks are now a permanent feature.
 *
 * Common uses: SQL, JSON, HTML, log messages.
 */
public class TextBlocksFinal {

    public static void main(String[] args) {
        String html = """
                <html>
                  <body>
                    <h1>Hello, %s</h1>
                  </body>
                </html>
                """.formatted("World");
        System.out.println(html);

        // `\s` keeps the space (trailing whitespace would normally be stripped).
        String padded = """
                col1  col2\s
                a     b\s
                """;
        System.out.println("[" + padded + "]");
    }
}
