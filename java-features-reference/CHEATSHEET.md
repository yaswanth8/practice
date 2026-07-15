# CHEATSHEET - one-page-per-topic quick reference

Use this the night before an interview. Every section is short on purpose.
Full runnable examples live in the packages the section points at.

---

## 1. Lambda expressions (Java 8)

```java
Runnable r     = ()      -> System.out.println("hi");   // no args
Function<Integer,Integer> sq = x -> x * x;              // 1 arg
BiFunction<Integer,Integer,Integer> add = (a, b) -> a + b;
Function<Integer,Integer> block = x -> {
    int y = x + 1;
    return y * 2;
};
```

**Rules to remember**
- Captured local variables must be **effectively final**.
- `this` inside a lambda = the enclosing class instance.
- Lambda targets any **functional interface** (one abstract method).

**Files:** `java08/LambdaExpressions.java`, `java08/FunctionalInterfaces.java`

---

## 2. Built-in functional interfaces

| Interface | Method | Meaning |
| --- | --- | --- |
| `Function<T,R>`       | `R apply(T)`         | transform |
| `BiFunction<T,U,R>`   | `R apply(T,U)`       | 2-arg transform |
| `Predicate<T>`        | `boolean test(T)`    | condition |
| `Consumer<T>`         | `void accept(T)`     | side effect |
| `Supplier<T>`         | `T get()`            | factory |
| `UnaryOperator<T>`    | `T apply(T)`         | `Function<T,T>` |
| `BinaryOperator<T>`   | `T apply(T,T)`       | reducer |

Chain: `p1.and(p2)`, `p1.or(p2)`, `p1.negate()`, `f.andThen(g)`, `f.compose(g)`.

---

## 3. Method references

| Kind | Example | Same as |
| --- | --- | --- |
| Static             | `Integer::parseInt`   | `s -> Integer.parseInt(s)` |
| Bound instance     | `System.out::println` | `x -> System.out.println(x)` |
| Unbound instance   | `String::toUpperCase` | `s -> s.toUpperCase()` |
| Constructor        | `ArrayList::new`      | `() -> new ArrayList<>()` |

---

## 4. Stream API

**Pipeline**: `source -> intermediate ops (lazy) -> terminal op`

**Intermediate:** `filter`, `map`, `flatMap`, `distinct`, `sorted`, `peek`, `limit`, `skip`, `takeWhile`, `dropWhile`.

**Terminal:** `collect`, `forEach`, `count`, `reduce`, `min/max`, `findFirst/findAny`, `anyMatch/allMatch/noneMatch`, `toArray`, `toList` (Java 16).

**5 facts you MUST know**
1. Intermediate ops are **lazy**.
2. Streams are **consumed once**.
3. Do not mutate the source.
4. `parallel()` uses the common ForkJoinPool; measure before using.
5. `findAny` may return anything in parallel; `findFirst` respects order.

**One-liners you should memorise**

```java
// group by dept, sum salary
Map<String,Double> total = emps.stream()
    .collect(groupingBy(Employee::dept, summingDouble(Employee::salary)));

// dedupe by name, keep first
Map<String,Employee> byName = emps.stream()
    .collect(toMap(Employee::name, e -> e, (a,b) -> a, LinkedHashMap::new));

// top 3 by salary
emps.stream().sorted(comparingDouble(Employee::salary).reversed()).limit(3).toList();

// frequency count
words.stream().collect(groupingBy(identity(), counting()));
```

**Files:** `java08/StreamApi.java`, `java08/StreamAdvanced.java`, `java08/ParallelStreams.java`.

---

## 5. Optional

```java
Optional.of(x)          // x must not be null
Optional.ofNullable(x)  // null becomes empty
Optional.empty()

opt.ifPresent(v -> ...);          // do if present
opt.map(f).filter(p).orElse(d);   // pipeline
opt.orElse(default);              // ALWAYS evaluates default
opt.orElseGet(supplier);          // ONLY on empty (lazy)
opt.orElseThrow(() -> new ...);   // assert present
```

**Do**: return `Optional` from lookups.
**Don't**: use it as a field, parameter, or in a collection.

---

## 6. Records (Java 16)

```java
record Point(int x, int y) {
    public Point { if (x<0||y<0) throw new IllegalArgumentException(); }  // compact ctor
    public Point translate(int dx,int dy) { return new Point(x+dx,y+dy); }
    public static Point origin() { return new Point(0,0); }
}
```

Compiler generates: private final fields, canonical constructor, accessors,
`equals`, `hashCode`, `toString`. Records are **final**, can't add instance fields.

---

## 7. Sealed classes (Java 17)

```java
sealed interface Shape permits Circle, Square, Triangle {}
record Circle(double r)   implements Shape {}
record Square(double s)   implements Shape {}
record Triangle(double b, double h) implements Shape {}

double area(Shape s) {                          // exhaustive - no default
    return switch (s) {
        case Circle c   -> Math.PI * c.r() * c.r();
        case Square sq  -> sq.s() * sq.s();
        case Triangle t -> 0.5 * t.b() * t.h();
    };
}
```

Every subtype must be `final`, `sealed`, or `non-sealed`.

---

## 8. Pattern matching for switch (Java 21)

```java
switch (obj) {
    case null                        -> "null";
    case Integer i when i < 0        -> "neg int " + i;
    case Integer i                   -> "int "     + i;
    case String s when s.isBlank()   -> "blank";
    case String s                    -> "str " + s;
    default                          -> "other";
}
```

Also: record deconstruction (`case Point(int x, int y) ->`) and nested
(`case Line(Point(var x1,_), Point(var x2,_)) ->`).

---

## 9. Virtual threads (Java 21)

```java
try (var ex = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10_000; i++) {
        ex.submit(() -> { doIO(); return null; });
    }
} // waits for all
```

- Great for IO-bound work at scale.
- Not faster for CPU-bound work.
- Before Java 24: `synchronized` could pin the carrier - use `ReentrantLock`.

---

## 10. Sequenced collections (Java 21)

```java
list.addFirst(x);      list.addLast(y);
list.getFirst();       list.getLast();
list.removeFirst();    list.removeLast();
list.reversed();       // view

map.firstEntry();      map.lastEntry();
map.putFirst("a",1);   map.putLast("z",26);
```

Adds structure to `List`, `Deque`, `LinkedHashSet/Map`, `TreeSet/Map`.

---

## 11. `var` (Java 10) - allowed and not allowed

Allowed: local vars with initializer, for-loop indexes, try-with-resources.
NOT allowed: fields, method params, return types, `null` initializer,
un-initialized declarations, lambda params before Java 11.

---

## 12. Text blocks (Java 15)

```java
String json = """
        {
          "name": "Ravi"
        }
        """;   // indentation is stripped relative to the least-indented line

String noNewline = """
        one \
        line""";        // \<newline> = line continuation
String space     = "col1  col2\s ";   // \s survives trim
```

---

## 13. CompletableFuture

```java
CompletableFuture.supplyAsync(this::fetchUser)      // async source
    .thenApply(User::name)                          // sync map
    .thenCompose(this::loadProfileAsync)            // async flatMap
    .thenCombine(fetchOrdersAsync(), Dashboard::of) // zip
    .exceptionally(ex -> Dashboard.EMPTY)           // recover
    .completeOnTimeout(Dashboard.EMPTY, 2, SECONDS); // timeout
```

`thenApply` vs `thenCompose`:
- `thenApply(f)`   -> `f` returns a value.
- `thenCompose(f)` -> `f` returns another `CompletableFuture` (no nesting).

---

## 14. `java.time` (Java 8)

| Type | Use for |
| --- | --- |
| `LocalDate`      | dates without time or zone |
| `LocalTime`      | time without date or zone |
| `LocalDateTime`  | date + time, no zone |
| `ZonedDateTime`  | user-facing wall-clock time |
| `Instant`        | machine timestamps (UTC epoch) - **storage/logs** |
| `Period`         | date-based amount (years/months/days) |
| `Duration`       | time-based amount (seconds/nanos) |

All types are immutable + thread-safe.

---

## 15. `equals` / `hashCode` contract

1. Reflexive: `x.equals(x) == true`.
2. Symmetric: `x.equals(y) == y.equals(x)`.
3. Transitive: if `x==y` and `y==z`, then `x==z`.
4. Consistent: multiple calls return same result unless data changes.
5. `x.equals(null) == false`.
6. **If `x.equals(y)` then `x.hashCode() == y.hashCode()`**.

Break rule 6 -> `HashMap` / `HashSet` misbehave silently.

---

## 16. Generics + PECS

```java
List<? extends Number> readOnly;   // Producer Extends - can only READ
List<? super Integer>  writeOnly;  // Consumer Super   - can only WRITE
List<?>                unknown;    // read Object only
```

**PECS**: **P**roducer **E**xtends, **C**onsumer **S**uper.

---

## 17. Checked vs unchecked exceptions

- **Checked**: extends `Exception` (not `RuntimeException`). Compiler forces
  `throws` or `try/catch`. E.g. `IOException`, `SQLException`.
- **Unchecked**: extends `RuntimeException`. Programming errors, no compiler
  enforcement. E.g. `NullPointerException`, `IllegalArgumentException`.
- **Errors**: `OutOfMemoryError`, `StackOverflowError`. Don't catch.

**try-with-resources** (Java 7): auto-closes any `AutoCloseable`.

---

## 18. Concurrency shortlist

- `AtomicInteger`, `AtomicLong`, `LongAdder`.
- `ConcurrentHashMap` (put + compute atomically).
- `CopyOnWriteArrayList` (read-heavy).
- `ReentrantLock`, `Semaphore`, `CountDownLatch`, `CyclicBarrier`.
- `ExecutorService`, `ScheduledExecutorService`, `ForkJoinPool`.
- `CompletableFuture` (composable async).
- Virtual threads (Java 21+) for IO scale.

---

## 19. HashMap facts you're probably asked

- Uses hashing + buckets. Java 8+ converts a long collision chain (>8) into
  a red-black tree; back to list when it shrinks to <=6.
- Default load factor 0.75, capacity always a power of 2.
- `null` key allowed once; `null` values allowed.
- Not thread-safe. `ConcurrentHashMap` is.

---

## 20. Java 8 -> 25 milestone highlights

| Version | Headline features |
| --- | --- |
| 8  (LTS) | lambdas, streams, Optional, `java.time`, `CompletableFuture` |
| 9        | modules, `List.of`, private interface methods |
| 10       | `var` |
| 11 (LTS) | `String.strip/lines/repeat`, `HttpClient`, `Files.readString` |
| 12-13    | switch expression, text block (preview) |
| 14       | records (preview), pattern instanceof, helpful NPE |
| 15       | text blocks final, sealed (preview) |
| 16       | records final, `Stream.toList`, local records |
| 17 (LTS) | sealed final, pattern switch (preview), enhanced RNG |
| 18       | UTF-8 default |
| 19-20    | virtual threads (preview), record patterns (preview) |
| 21 (LTS) | virtual threads, sequenced collections, pattern switch final |
| 22       | unnamed vars/patterns final, FFM, stream gatherers (preview) |
| 23       | Markdown Javadoc, module imports (preview), primitive patterns (preview) |
| 24       | stream gatherers final, no more virtual-thread pinning |
| 25 (LTS) | scoped values final, module imports final, compact source files, flexible ctor bodies |

---

## Interview-day mindset

1. **Say why the feature exists, not just what it does.** ("Optional forces
   callers to think about absence.")
2. **Name one pitfall per feature.** ("`orElse` evaluates always - use
   `orElseGet` for expensive defaults.")
3. **When stuck, follow the types.** "I have `Stream<List<X>>`; I want
   `Stream<X>`; that's `flatMap`."
4. **Prefer a correct for-loop over a wrong stream.** Refactor after it works.
