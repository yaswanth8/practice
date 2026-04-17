package com.careerit.java.java17;

/**
 * Java 17 - Pattern matching for switch (preview; standard in Java 21).
 *
 * Adds:
 *   - type patterns:       case Integer i ->
 *   - guarded patterns:    case String s when !s.isEmpty() ->
 *   - null case:           case null ->
 *   - exhaustiveness check for sealed hierarchies
 */
public class PatternMatchingSwitchPreview {

    sealed interface JsonValue permits JsonNull, JsonBool, JsonNumber, JsonString {}
    record JsonNull()             implements JsonValue {}
    record JsonBool(boolean b)    implements JsonValue {}
    record JsonNumber(double n)   implements JsonValue {}
    record JsonString(String s)   implements JsonValue {}

    static String render(Object o) {
        return switch (o) {
            case null               -> "null";
            case Integer i when i<0 -> "neg int " + i;
            case Integer i          -> "int "     + i;
            case String s           -> "str "     + s;
            case JsonValue v        -> "json "    + v;
            default                 -> "other";
        };
    }

    public static void main(String[] args) {
        System.out.println(render(null));
        System.out.println(render(-5));
        System.out.println(render(42));
        System.out.println(render("hi"));
        System.out.println(render(new JsonString("x")));
    }
}
