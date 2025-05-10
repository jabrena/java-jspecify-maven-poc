# Java JSpecify Maven POC

## Motivation

JSpecify aims to solve the problem of null reference errors (like NullPointerException) in Java code.

Here's how it addresses this with objects:

- Implicit Nullness: In standard Java, whether an object reference (like a parameter, return value, or field) can be null or not is often unspecified.
- Explicit Nullness with Annotations: JSpecify introduces standard annotations (like @Nullable, @NonNull, @NullMarked) to make the "nullness" of object types explicit in the code.
  - @Nullable: Indicates that a variable, parameter, or return value can hold null.
  - @NonNull: Indicates that a variable, parameter, or return value cannot hold null.
  - @NullMarked: Applied to a package or class, it sets the default to non-null, meaning any unannotated type usage within that scope is assumed to be non-null unless explicitly marked @Nullable.
- Static Analysis

## How to build in local?

```bash
./mvnw clean verify

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
./mvnw versions:display-property-updates

```

## References

- https://jcp.org/en/jsr/detail?id=305
- https://jspecify.dev/
- https://jspecify.dev/docs/user-guide/
- https://errorprone.info/
- https://errorprone.info/docs/flags
- https://github.com/uber/NullAway
- https://github.com/uber/NullAway/wiki
- https://en.wikipedia.org/wiki/Liskov_substitution_principle
- https://docs.spring.io/spring-framework/reference/7.0/core/null-safety.html
- https://spring.io/blog/2025/03/10/null-safety-in-spring-apps-with-jspecify-and-null-away
- https://github.com/sdeleuze/jspecify-nullway-demo
- https://www.moderne.ai/blog/mass-migration-of-nullability-annotations-to-jspecify
- https://github.com/google/guava/wiki/PreconditionsExplained

Powered by [Cursor](https://www.cursor.com/)