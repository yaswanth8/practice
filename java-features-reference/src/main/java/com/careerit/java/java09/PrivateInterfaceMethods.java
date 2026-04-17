package com.careerit.java.java09;

import java.util.List;

/**
 * Java 9 - private (and private static) methods on interfaces.
 *
 * Lets multiple default methods share helper code without exposing it as API.
 */
public class PrivateInterfaceMethods {

    interface Reporter {
        default String short_() { return header() + format("short"); }
        default String long_()  { return header() + format("long"); }

        private String header() { return "[report] "; }
        private static String format(String kind) { return "kind=" + kind; }
    }

    interface ModuleNote {
        /*
         * Java 9 also introduced the module system (JPMS) via module-info.java:
         *
         *   module com.careerit.javaref {
         *       requires java.net.http;
         *       exports com.careerit.java.java09;
         *   }
         *
         * This demo module intentionally stays on the unnamed module / classpath
         * to avoid forcing every consumer to modularize. See Oracle JEP 261.
         */
    }

    public static void main(String[] args) {
        Reporter r = new Reporter() {};
        System.out.println(List.of(r.short_(), r.long_()));
    }
}
