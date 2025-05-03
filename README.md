# Java JSpecify Maven POC

## Motivation

JSpecify aims to solve the problem of null reference errors (like NullPointerException) in Java code.

Here's how it addresses this with objects:

- Implicit Nullness: In standard Java, whether an object reference (like a parameter, return value, or field) can be null or not is often unspecified.
- Explicit Nullness with Annotations: JSpecify introduces standard annotations (like @Nullable, @NonNull, @NullMarked) to make the "nullness" of object types explicit in the code.

  - @Nullable: Indicates that a variable, parameter, or return value can hold null.
  - @NonNull: Indicates that a variable, parameter, or return value cannot hold null.
  - @NullMarked: Applied to a package or class, it sets the default to non-null, meaning any unannotated type usage within that scope is assumed to be non-null unless explicitly marked @Nullable.

- Static Analysis: These annotations allow static analysis tools (like NullAway, Error Prone, IntelliJ's inspector) to check the code before runtime (during compilation or in the IDE).

## How to build in local?

```bash
./mvnw clean verify

./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates
./mvnw versions:display-property-updates

```

## Preconditions

```java
//Java
Objects.requireNonNull(property1)

if(Objects.isNull(property1)) {
    throw new IllegalArgumentException("Not valid property1");
}

assert property1 != null : "property1 must not be null"; // Generally not recommended for parameter validation
//Guava
this.property1 = Preconditions.checkNotNull(property1, "property1 must not be null");
//Apache Commons Lang
this.property1 = Validate.notNull(property1, "property1 must not be null");

```

## References

- https://jspecify.dev/
- https://errorprone.info/
- https://errorprone.info/docs/flags
- https://github.com/uber/NullAway
- https://github.com/uber/NullAway/wiki
- https://en.wikipedia.org/wiki/Liskov_substitution_principle
