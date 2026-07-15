package com.careerit.java.futurejava;

/**
 * Java 22 - Reference notes (requires JDK 22+ to compile).
 *
 * Permanent features:
 *   JEP 456 - Unnamed Variables and Patterns
 *     for (Point _ : pts) { ... }
 *     (x, _) -> x
 *     catch (IOException _) { ... }
 *
 *   JEP 454 - Foreign Function and Memory API
 *     Replaces JNI for calling native code:
 *       try (Arena a = Arena.ofConfined()) {
 *           MemorySegment cString = a.allocateUtf8String("hello");
 *           Linker linker = Linker.nativeLinker();
 *           MethodHandle strlen = linker.downcallHandle(
 *               linker.defaultLookup().find("strlen").orElseThrow(),
 *               FunctionDescriptor.of(JAVA_LONG, ADDRESS));
 *           long n = (long) strlen.invoke(cString);
 *       }
 *
 * Preview / incubating in 22:
 *   JEP 447 - Statements before super(...) / this(...) in constructors
 *     class Sub extends Base {
 *         Sub(int x) {
 *             if (x < 0) throw new IllegalArgumentException();
 *             super(x * 2);
 *         }
 *     }
 *
 *   JEP 461 - Stream Gatherers
 *     Stream.gather(...) - pluggable intermediate operations, similar to how
 *     Collectors plugs into terminal operations.
 *     Example: a sliding-window gatherer
 *       stream.gather(Gatherers.windowSliding(3)).forEach(System.out::println);
 *
 *   JEP 462 - Structured Concurrency (2nd preview)
 *     try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
 *         Subtask<String> a = scope.fork(() -> fetchA());
 *         Subtask<String> b = scope.fork(() -> fetchB());
 *         scope.join().throwIfFailed();
 *         return a.get() + b.get();
 *     }
 *
 *   JEP 459 - String Templates (second preview; later withdrawn).
 *   JEP 457 - Class-File API (preview).
 *   JEP 464 - Scoped Values (second preview).
 *   JEP 463 - Implicitly declared classes / instance main methods (2nd preview).
 *
 * This file is a placeholder for interview notes only.
 */
public class Java22Features {
    public static void main(String[] args) {
        System.out.println("Java 22 reference notes - see source comments.");
    }
}
