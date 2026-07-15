# INTERVIEW Q&A - 60 questions with short, honest answers

Every answer is deliberately compact so you can re-read them all in 30 minutes.
When the answer says "see X" the deeper example is in the module.

---

## Java 8: lambdas, functional interfaces, method references

**Q1. What is a functional interface?**
An interface with exactly one abstract method. It can still have `default` and
`static` methods. `@FunctionalInterface` makes the compiler enforce the rule.

**Q2. Why does a lambda need "effectively final" variables?**
The lambda captures the variable's *value* at that point. If it could change
later, the lambda would either see stale data or need shared-memory
synchronization. Java forbids both by requiring the variable to never change.

**Q3. Inside a lambda, what does `this` refer to?**
The enclosing class instance - NOT the lambda. That's different from an
anonymous class where `this` is the anonymous instance.

**Q4. Difference between `Function` and `UnaryOperator`?**
`Function<T,R>` maps T to R. `UnaryOperator<T>` is a `Function<T,T>` - same
type in and out. Same idea: `BinaryOperator<T>` is `BiFunction<T,T,T>`.

**Q5. Difference between `Predicate.and` and `&&`?**
`&&` is a Java operator; `Predicate.and` returns a **new Predicate**. It lets
you compose reusable, named conditions.

**Q6. Name the four kinds of method references.**
Static (`Integer::parseInt`), bound (`out::println`), unbound
(`String::toUpperCase`), constructor (`ArrayList::new`).

**Q7. Can a lambda throw a checked exception?**
Not from a built-in functional interface (they don't declare `throws`).
Solutions: try/catch inside, or write your own throwing functional interface
+ a wrapper that converts checked to unchecked. See
`java08/LambdaExceptionHandling.java`.

---

## Java 8: Streams

**Q8. What is a Stream?**
A pipeline over a data source. It does not store data, does not modify the
source, and is consumed once.

**Q9. Lazy vs eager?**
Intermediate ops (`filter`, `map`, `sorted`) are lazy - they only build a
plan. The terminal op (`collect`, `count`, `forEach`) runs the plan.

**Q10. `map` vs `flatMap`?**
`map` turns each element into one output. `flatMap` turns each element into
a stream of outputs and flattens them. Use `flatMap` when you have
`Stream<List<X>>` and want `Stream<X>`.

**Q11. Why is `.peek()` "debug only"?**
`peek` runs during pipeline execution; if no terminal op is called, nothing
runs. Also, in parallel streams the ordering of `peek` calls is unpredictable.

**Q12. What short-circuits a stream?**
`limit`, `findFirst`, `findAny`, `anyMatch`, `allMatch`, `noneMatch`. They
let you use infinite streams safely.

**Q13. When is `parallelStream()` a bad idea?**
- Very small streams (overhead > gain).
- IO-bound operations (better: async or virtual threads).
- Non-thread-safe collectors or shared mutable state.
- Sources that don't split well (`LinkedList`, `Stream.iterate`).
- Code already inside a request thread pool.

**Q14. What happens if you use the same Stream twice?**
Throws `IllegalStateException`. Streams are single-use. Wrap the source in a
`Supplier<Stream<T>>` if you need to run the pipeline multiple times.

**Q15. `reduce(identity, accumulator)` vs `reduce(accumulator)`?**
With identity, you always get a value back. Without, you get `Optional<T>`
(empty when the stream is empty).

**Q16. `collect(Collectors.toList())` vs `stream.toList()` (Java 16)?**
`toList()` is shorter, returns an **unmodifiable** list, tolerates nulls.
`Collectors.toList()` returns a modifiable `ArrayList`.

**Q17. How do you avoid `IllegalStateException` from `toMap` when keys collide?**
Provide a merge function: `toMap(keyFn, valFn, (existing, next) -> ...)`.

**Q18. Difference between `findFirst` and `findAny`?**
`findFirst` respects encounter order; `findAny` can return any matched
element - useful for extra parallelism.

---

## Optional

**Q19. When should you return `Optional`?**
For method return types where "no value" is a normal outcome (lookups). Not
for fields, parameters, or collection elements.

**Q20. `orElse` vs `orElseGet`?**
`orElse(x)` always evaluates `x`. `orElseGet(supplier)` only evaluates when
empty. Use `orElseGet` for expensive defaults.

**Q21. When is `Optional.get()` safe?**
Almost never on its own. Prefer `orElseThrow`, `orElseGet`, or `map/ifPresent`.

**Q22. Difference between `Optional.map` and `Optional.flatMap`?**
`map(f)` where `f` returns a value -> `Optional<R>`. `flatMap(f)` where `f`
already returns `Optional<R>` -> avoids `Optional<Optional<R>>` nesting.

---

## Collections

**Q23. `ArrayList` vs `LinkedList`?**
`ArrayList` = resizable array. O(1) random access, O(n) middle insertion.
`LinkedList` = doubly linked list. O(1) at head/tail, O(n) random access.
Real code almost always uses `ArrayList`.

**Q24. `HashMap` internals?**
Bucket array indexed by `hash(key) % capacity`. Java 8+ converts long
collision chains (>=8) to red-black trees for O(log n) lookups. Default
capacity 16, load factor 0.75.

**Q25. What can go wrong if `hashCode` is broken?**
Two "equal" objects with different hash codes will land in different buckets.
`HashMap.get` won't find them, `HashSet.add` accepts duplicates. Silent bug.

**Q26. `HashMap` vs `Hashtable` vs `ConcurrentHashMap`?**
- `Hashtable`: legacy, fully synchronized (slow).
- `HashMap`: not thread-safe, allows one null key.
- `ConcurrentHashMap`: thread-safe, no null keys/values, high throughput via
  fine-grained locks / CAS.

**Q27. `HashSet` vs `TreeSet` vs `LinkedHashSet`?**
`HashSet` unordered O(1). `TreeSet` sorted O(log n). `LinkedHashSet`
insertion-ordered O(1).

**Q28. `fail-fast` vs `fail-safe` iterators?**
Fail-fast (most `java.util` collections) throws
`ConcurrentModificationException` if the collection changes during iteration.
Fail-safe (`ConcurrentHashMap`, `CopyOnWriteArrayList`) iterates over a
snapshot.

**Q29. `Collection.removeIf` vs iterator removal?**
`removeIf(pred)` (Java 8) is cleaner and doesn't need an explicit iterator.

**Q30. How do you make a collection unmodifiable vs immutable?**
`List.copyOf(list)` = truly immutable copy. `Collections.unmodifiableList(l)`
= a view; if you modify the underlying list, the view changes too.

---

## Records, sealed, pattern matching

**Q31. What does the compiler generate for a record?**
Private final fields, canonical constructor, accessors named after
components, `equals`, `hashCode`, `toString`. And the class is implicitly
`final`.

**Q32. Why can't records have additional instance fields?**
They're transparent data carriers. All state must come from components so
`equals`, `hashCode`, `toString`, and serialization can be well-defined.

**Q33. What is a compact constructor?**
A constructor without a parameter list inside a record - used for validation
and normalization. You can reassign the parameters; the assignment to fields
is done automatically at the end.

**Q34. What does `sealed` give you?**
It restricts which classes can extend/implement a type. Enables exhaustive
switch and safe modelling of closed hierarchies (algebraic data types).

**Q35. What are the three modifiers allowed on a subtype of a sealed class?**
`final`, `sealed`, or `non-sealed`.

**Q36. Why does pattern-matched switch over a sealed type not need `default`?**
The compiler can prove every subtype is handled, so it's exhaustive.

**Q37. Give a switch with a guarded pattern.**
```java
switch (obj) {
    case Integer i when i < 0 -> "neg";
    case Integer i            -> "int";
    default                   -> "other";
}
```

---

## Concurrency

**Q38. `Runnable` vs `Callable`?**
`Runnable.run()` returns void, no checked exceptions. `Callable.call()`
returns `V`, can throw checked exceptions.

**Q39. `Future.get()` vs `CompletableFuture.get()`?**
`Future.get()` only blocks. `CompletableFuture` adds composition
(`thenApply`, `thenCompose`, `thenCombine`), completion callbacks, error
recovery, and timeouts.

**Q40. `thenApply` vs `thenCompose` vs `thenCombine`?**
- `thenApply(f)`: `f` returns a value.
- `thenCompose(f)`: `f` returns another `CompletableFuture`.
- `thenCombine(other, biFunc)`: zip two futures into one result.

**Q41. `synchronized` vs `ReentrantLock`?**
`synchronized`: simpler, JVM-level, no fairness or `tryLock`. `ReentrantLock`:
explicit lock/unlock (needs try/finally), supports fairness, timed waits,
interruptible acquires, multiple conditions.

**Q42. `volatile` - what does it guarantee?**
Reads/writes to the variable are visible to other threads (no CPU cache
tricks). It does NOT make compound operations (`x++`) atomic.

**Q43. What is a virtual thread and when to use it?**
A user-mode thread multiplexed onto a small number of platform carrier
threads. Ideal for many concurrent IO-bound tasks. Not faster for CPU-bound
work.

**Q44. Why not pool virtual threads?**
They ARE the pool. Just create a new one per task. Prefer
`Executors.newVirtualThreadPerTaskExecutor()`.

**Q45. What's a `ThreadLocal` and what's the modern alternative?**
Per-thread storage. Fine, but easy to leak in thread pools and doesn't scale
to millions of virtual threads. `ScopedValue` (Java 21+) is the modern,
immutable, scope-bounded alternative.

---

## `equals`, `hashCode`, `Comparable`

**Q46. State the equals contract.**
Reflexive, symmetric, transitive, consistent, and `x.equals(null) == false`.

**Q47. Why must equal objects have equal hash codes?**
`HashMap`/`HashSet` first find the bucket via `hashCode`, then compare with
`equals`. Break the rule and lookups silently fail.

**Q48. `Comparable` vs `Comparator`?**
`Comparable<T>` = natural ordering, defined on the class (`compareTo`).
`Comparator<T>` = external ordering, one per sort. Records don't get one for
free; you write your own.

---

## Generics

**Q49. What is type erasure?**
At runtime the JVM sees `List` instead of `List<String>`. Generics are a
compile-time check. That's why you can't do `new T[10]` or
`x instanceof List<String>`.

**Q50. PECS - what does it mean?**
"Producer Extends, Consumer Super." Use `? extends T` when you only read
(produce) `T`. Use `? super T` when you only write (consume) `T`.

**Q51. Difference between `List<Object>` and `List<?>`.**
`List<Object>` accepts anything but is NOT a supertype of `List<String>`.
`List<?>` is a supertype of every `List<X>`, but you can only read `Object`
from it.

---

## Exceptions

**Q52. Checked vs unchecked?**
Checked: subclasses of `Exception` (not `RuntimeException`) - compiler
enforces handling. Unchecked: `RuntimeException` subclasses - not enforced.

**Q53. `throw` vs `throws`?**
`throw` triggers an exception. `throws` declares that a method might throw a
checked exception.

**Q54. What does try-with-resources give you?**
Automatic close of any `AutoCloseable` at end of block, even on exception.
Suppressed exceptions from `close()` are attached to the primary one.

**Q55. Should you swallow exceptions?**
Only if you have a specific reason and log it. Empty catch blocks are one of
the top interview red flags.

---

## Java version highlights

**Q56. Why was `Optional` added?**
To make "absent value" explicit in method return types, so callers must
handle it, instead of forgetting and getting NPE.

**Q57. Why were records added?**
To eliminate boilerplate for data-carrier classes and provide correct
`equals`/`hashCode`/`toString` for free.

**Q58. Why sealed classes?**
For modelling closed hierarchies (ADTs) with compiler-verified exhaustive
switches.

**Q59. Why virtual threads?**
So you can write plain synchronous, blocking IO code and scale to hundreds
of thousands of concurrent tasks without the callback / reactive complexity.

**Q60. What's new in Java 21 (current LTS) versus Java 17 (previous LTS)?**
- Virtual threads (JEP 444).
- Sequenced Collections (JEP 431).
- Pattern matching for switch is permanent (JEP 441).
- Record patterns permanent (JEP 440).
- Preview: string templates, scoped values, unnamed patterns.

---

## Interview strategy

- Use **short answers** first, expand only when asked.
- When answering, name **one concrete example** you have used (from this repo
  counts).
- If you don't know, say so and offer to reason from first principles.
- Prefer **types over prose** when stuck: describe input type and desired
  output type and ask "which operation gets me there?"
