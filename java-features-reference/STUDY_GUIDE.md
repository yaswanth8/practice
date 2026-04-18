# STUDY GUIDE - How to use this repo

This guide turns the examples into a **learning path**. Each step tells you
which files to read, what to focus on, and a question to answer BEFORE you
read the code.

> Golden rule: try to predict the output first, then run, then explain to
> yourself in one sentence why you were right or wrong.

---

## How to run any example

```bash
# from repo root
mvn -pl java-features-reference -am compile

# run any file that has a main(...)
mvn -pl java-features-reference exec:java \
    -Dexec.mainClass=com.careerit.java.java08.StreamApi
```

In IntelliJ / VS Code, just click the green arrow next to `main`.

---

## Week 1 - Java 8 foundations (most important for interviews)

| Day | Files | Prompt to answer first |
| --- | ----- | ---------------------- |
| 1 | `java08/LambdaExpressions.java`, `java08/FunctionalInterfaces.java` | "What does 'effectively final' mean and why do lambdas require it?" |
| 2 | `java08/MethodReferences.java`, `java08/DefaultStaticMethods.java` | "Name the 4 kinds of method references." |
| 3 | `java08/StreamApi.java` | "What is a terminal operation, and why does a stream 'run' only after one?" |
| 4 | `java08/CollectorsDemo.java`, `java08/StreamAdvanced.java` | "What's the difference between `toMap` with and without a merge function?" |
| 5 | `java08/OptionalDemo.java`, `java08/LambdaExceptionHandling.java` | "When would you use `orElseGet` over `orElse`?" |
| 6 | `java08/DateTimeApi.java`, `java08/CompletableFutureDemo.java` | "When do you pick `Instant` vs `ZonedDateTime`?" |
| 7 | `java08/ParallelStreams.java`, `interviewpatterns/BeforeAfterJava8.java` | "Name two situations where a parallel stream is a bad idea." |

At the end of week 1 you should be able to solve any classic collection
manipulation problem (group by, count, top N, dedupe, partition) with streams.

---

## Week 2 - Modern Java (9 to 17)

| Day | Files | Prompt |
| --- | ----- | ------ |
| 8  | `java09/*`, `java10/VarLocalInference.java` | "When should you NOT use `var`?" |
| 9  | `java11/*` | "What changed about the default String trim behaviour in Java 11?" |
| 10 | `java12/*`, `java13/*` | "What does `yield` do in a switch expression?" |
| 11 | `java14/*` | "Why are records implicitly final?" |
| 12 | `java15/*`, `java16/*` | "What are the three modifiers allowed on a subtype of a sealed class?" |
| 13 | `java17/SealedFinal.java`, `java17/PatternMatchingSwitchPreview.java` | "How does pattern matching enable exhaustive switches?" |
| 14 | `java17/RandomGenerators.java` + revise | "What's the difference between `findFirst` and `findAny`?" |

---

## Week 3 - Current LTS features and concurrency

| Day | Files | Prompt |
| --- | ----- | ------ |
| 15 | `java18/*`, `java19/VirtualThreadsPreview.java` | "Why are virtual threads mostly useful for IO-bound work?" |
| 16 | `java19/RecordPatternsPreview.java`, `java20/*` | "Write a switch over a sealed type using record patterns." |
| 17 | `java21/VirtualThreadsFinal.java`, `java21/SequencedCollections.java` | "Name 3 existing collection types that are now `SequencedCollection`." |
| 18 | `java21/PatternMatchingSwitchFinal.java` | "How do guarded patterns interact with exhaustiveness?" |
| 19 | `interviewpatterns/ConcurrencyBasics.java` | "When would you prefer `thenCompose` over `thenApply`?" |
| 20 | `interviewpatterns/FunctionalPatterns.java` | "How would you implement memoization of a pure function?" |
| 21 | `interviewpatterns/ClassicProblems.java` | Solve any 5 without looking, from scratch. |

---

## Week 4 - Lookahead and polish

* Skim `futurejava/Java22Features.java` ... `Java25Features.java` for the LTS
  roadmap. These are reference notes, not runnable features (require JDK 22+).
* Re-run every example WITHOUT looking at the code; write a 1-line summary
  next to each `System.out.println(...)` output.
* Practice the prompts in the "Interview questions bank" below.

---

## Interview questions bank

**Lambdas / functional interfaces**
1. What is a functional interface? Name 6 from `java.util.function`.
2. Difference between `Function<T,R>` and `UnaryOperator<T>`?
3. What does `effectively final` mean?
4. Why can you use `this` inside a lambda to reach the enclosing class?
5. How would you convert a method that throws a checked exception into a `Function`?

**Streams**
6. Lazy vs eager evaluation - what's the distinction?
7. Difference between `map` and `flatMap`.
8. Why is `Stream.peek` "debug only"?
9. Give three short-circuiting operations.
10. Why is `parallelStream()` not always faster?
11. Difference between `reduce(identity, op)` and `reduce(op)`.
12. What happens if you call two terminal operations on the same stream?

**Collectors**
13. `groupingBy` vs `partitioningBy`.
14. How do you avoid `IllegalStateException` from `toMap` with duplicate keys?
15. What does `Collectors.teeing` solve?

**Optional**
16. When should you NOT return `Optional`?
17. Difference between `orElse` and `orElseGet`.
18. `Optional.get()` - when is it safe?

**Records**
19. What does the compiler generate for a record?
20. Why can't records have additional instance fields?
21. What is a compact constructor? What can you do inside it?

**Sealed / pattern matching**
22. What are the three modifiers allowed on a sealed subtype?
23. Why does pattern matching over a sealed type not require a `default`?
24. Show a switch that uses a guard `when` clause.

**Concurrency**
25. `Future.get()` vs `CompletableFuture.get()` - name two differences.
26. `thenApply` vs `thenCompose` vs `thenCombine` - when to use each?
27. What is a virtual thread, and when is it the right choice?
28. What problem do `SequencedCollection` methods solve?

**Date/Time**
29. Why is `Instant` preferred to `java.util.Date` for storing timestamps?
30. `Period` vs `Duration`.

---

## Tips for live interviews

* **Narrate your thinking.** Interviewers grade reasoning, not keystrokes.
* **Prefer clarity over cleverness.** A short for-loop is better than a wrong
  stream. Upgrade to a stream once the logic is right.
* **Name one pitfall per feature.** "Virtual threads are great for IO, but
  before Java 24 synchronized blocks could pin the carrier" - that level of
  detail earns points.
* **When stuck, fall back to types.** Ask "what type do I have, what type do
  I want, which transformation gets me there?" That narrows `map` vs `flatMap`
  vs `reduce` automatically.
