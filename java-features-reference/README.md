# java-features-reference

Interview-focused reference covering Java 8 through Java 25. Each Java version
has its own package under `src/main/java/com/careerit/java/` with small,
self-contained, runnable examples and Javadoc that call out the key talking
points.

## Prerequisites

* Java 21 (parent `pom.xml` targets Java 21; the `java-features-reference`
  module inherits that).
* Maven 3.9+.

Examples for Java 22-25 live under `futurejava/` as **reference-only** classes
(feature summaries in Javadoc / comments). Compiling or running those features
verbatim requires the corresponding JDK and, for previews, `--enable-preview`.

## Build

```bash
mvn -pl java-features-reference -am compile
```

## Run a demo

Every file has a `main(...)`. From the module directory:

```bash
mvn -pl java-features-reference exec:java \
    -Dexec.mainClass=com.careerit.java.java08.StreamApi
```

Or from an IDE, right-click any file and run it.

## Feature index

### Java 8 (LTS, 2014)
| File | Feature |
| ---- | ------- |
| `java08/LambdaExpressions.java` | Lambdas, capture rules, `this` in lambdas |
| `java08/FunctionalInterfaces.java` | `Function`, `Predicate`, `Consumer`, `Supplier`, `UnaryOperator`, `BiFunction`, custom `@FunctionalInterface` |
| `java08/MethodReferences.java` | All four kinds of method references |
| `java08/StreamApi.java` | Streams, reductions, grouping, flatMap |
| `java08/CollectorsDemo.java` | `groupingBy`, `partitioningBy`, `mapping`, `collectingAndThen` |
| `java08/OptionalDemo.java` | Correct `Optional` usage |
| `java08/DefaultStaticMethods.java` | Default + static methods on interfaces |
| `java08/DateTimeApi.java` | `java.time` (Local/Zoned/Instant/Duration/Period) |
| `java08/CompletableFutureDemo.java` | Async composition, `thenCompose`, `allOf`, error handling |

### Java 9 (2017)
| File | Feature |
| ---- | ------- |
| `java09/CollectionFactories.java` | `List.of`, `Set.of`, `Map.of`, `Map.ofEntries` |
| `java09/StreamAndOptionalImprovements.java` | `takeWhile`, `dropWhile`, `iterate(3-arg)`, `Optional.ifPresentOrElse/or/stream` |
| `java09/PrivateInterfaceMethods.java` | `private` methods on interfaces (+ JPMS notes) |

### Java 10 (2018)
| File | Feature |
| ---- | ------- |
| `java10/VarLocalInference.java` | `var` local variables, rules and gotchas |

### Java 11 (LTS, 2018)
| File | Feature |
| ---- | ------- |
| `java11/StringMethods.java` | `isBlank`, `strip`, `lines`, `repeat` |
| `java11/VarInLambda.java` | `var` in lambda parameters |
| `java11/HttpClientDemo.java` | Standardized `java.net.http.HttpClient` |
| `java11/FilesReadWriteString.java` | `Files.readString` / `Files.writeString` |

### Java 12 (2019)
| File | Feature |
| ---- | ------- |
| `java12/SwitchExpressionsPreview.java` | Switch expressions, arrow labels, `yield` |
| `java12/CollectorsTeeing.java` | `Collectors.teeing` for two-in-one reductions |

### Java 13 (2019)
| File | Feature |
| ---- | ------- |
| `java13/TextBlocksPreview.java` | Text blocks, incidental whitespace, `\s`, `\<newline>` |

### Java 14 (2020)
| File | Feature |
| ---- | ------- |
| `java14/RecordsPreview.java` | Records (compact ctor, validation, factories) |
| `java14/PatternMatchingInstanceof.java` | Pattern matching for `instanceof` |
| `java14/HelpfulNpe.java` | Helpful NullPointerExceptions |

### Java 15 (2020)
| File | Feature |
| ---- | ------- |
| `java15/SealedClassesPreview.java` | Sealed interfaces + exhaustive switch |
| `java15/TextBlocksFinal.java` | Text blocks are permanent |

### Java 16 (2021)
| File | Feature |
| ---- | ------- |
| `java16/RecordsFinalized.java` | Records standard; local records/enums/interfaces |
| `java16/StreamToList.java` | `Stream.toList()` |

### Java 17 (LTS, 2021)
| File | Feature |
| ---- | ------- |
| `java17/SealedFinal.java` | Sealed classes permanent; `non-sealed` |
| `java17/PatternMatchingSwitchPreview.java` | Switch patterns, `case null`, guards |
| `java17/RandomGenerators.java` | Enhanced pseudo-random generators |

### Java 18 (2022)
| File | Feature |
| ---- | ------- |
| `java18/Utf8ByDefault.java` | UTF-8 is the default charset (JEP 400) |

### Java 19 (2022)
| File | Feature |
| ---- | ------- |
| `java19/VirtualThreadsPreview.java` | Virtual threads preview |
| `java19/RecordPatternsPreview.java` | Record deconstruction patterns |
| `java19/StructuredConcurrencyIncubator.java` | Structured concurrency notes |

### Java 20 (2023)
| File | Feature |
| ---- | ------- |
| `java20/ScopedValuesIncubator.java` | Scoped values + re-previews overview |

### Java 21 (LTS, 2023)
| File | Feature |
| ---- | ------- |
| `java21/VirtualThreadsFinal.java` | Virtual threads standard |
| `java21/SequencedCollections.java` | `SequencedCollection/Set/Map` |
| `java21/PatternMatchingSwitchFinal.java` | Switch patterns + record patterns final |
| `java21/StringTemplatesPreview.java` | String templates (preview) |
| `java21/ScopedValuesPreview.java` | Scoped values (preview) |
| `java21/UnnamedPatternsPreview.java` | Unnamed variables `_` (preview) |

### Future (reference notes only)

Under `com.careerit.java.futurejava`:

| File | Target version |
| ---- | -------------- |
| `Java22Features.java` | Unnamed vars/patterns final, FFM final, Stream Gatherers, Statements before super, Structured Concurrency, Class-File API |
| `Java23Features.java` | Markdown Javadoc, Module Imports, Primitive Patterns, Flexible Constructor Bodies |
| `Java24Features.java` | Stream Gatherers permanent, Virtual threads without pinning, Scoped Values stabilizing |
| `Java25Features.java` | LTS: Scoped Values final, Module Imports final, Compact Source Files + instance `main`, Flexible Constructor Bodies final |

## Interview prep tips

* Know **why** each feature was added (pain point it solves), not just syntax.
* Have one go-to example per feature you can write on a whiteboard.
* Expect follow-ups: e.g. "Stream.parallel() - when would you avoid it?",
  "Why does Optional.get() get flagged by linters?", "Virtual threads vs.
  reactive - pick one and justify."
* The `futurejava/*` Javadocs double as a LTS-upgrade checklist when
  consulting on Java 21 -> 25 migrations.
