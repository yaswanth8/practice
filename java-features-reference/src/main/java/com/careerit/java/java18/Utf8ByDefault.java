package com.careerit.java.java18;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Java 18 - UTF-8 by default (JEP 400).
 *
 * The default charset of the standard java.io/java.nio APIs is now UTF-8,
 * regardless of OS locale. Use Charset.defaultCharset() to observe it.
 * Override with -Dfile.encoding=... if absolutely necessary (not recommended).
 *
 * Related Java 18 items: Simple Web Server (`jwebserver`) and @snippet in Javadoc.
 */
public class Utf8ByDefault {

    public static void main(String[] args) {
        System.out.println("default charset = " + Charset.defaultCharset());
        System.out.println("UTF-8 equals default? " + StandardCharsets.UTF_8.equals(Charset.defaultCharset()));
    }
}
