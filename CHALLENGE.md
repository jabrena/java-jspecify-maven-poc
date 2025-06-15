# JSpecify Challenge: Building Null-Safe Java Applications

## Overview

Welcome to the JSpecify Challenge! As a senior engineer, you'll dive deep into modern Java null safety using JSpecify annotations, integrated with Maven, ErrorProne, and NullAway for compile-time null safety verification.

## Learning Objectives

By completing this challenge, you will:

1. **Master JSpecify Annotations**: Learn to use `@Nullable`, `@NonNull`, `@NullMarked`, and `@NullUnmarked` effectively
2. **Understand Null Safety Patterns**: Implement robust null-safe APIs and internal logic
3. **Configure Build Tools**: Set up Maven with ErrorProne and NullAway for automated null safety checking
4. **Handle Complex Scenarios**: Deal with generics, inheritance, optional types, and legacy code integration
5. **Apply Best Practices**: Write production-ready null-safe code with proper error handling

## Project Setup

This project is pre-configured with:
- **Java 24** with modern language features
- **Maven 3.9.9** for build management
- **JSpecify 1.0.0** for null safety annotations
- **ErrorProne 2.38.0** for static analysis
- **NullAway 0.11.0** for null safety verification
- **JUnit 5** for testing with AssertJ for assertions

### Key Maven Configuration

The project includes ErrorProne and NullAway integration with these important settings:

```xml
<arg>-Xplugin:ErrorProne \
     -Xep:NullAway:ERROR \
     -XepOpt:NullAway:JSpecifyMode=true \
     -XepOpt:NullAway:TreatGeneratedAsUnannotated=true \
     -XepOpt:NullAway:CheckOptionalEmptiness=true \
     -XepOpt:NullAway:HandleTestAssertionLibraries=true \
     -XepOpt:NullAway:AssertsEnabled=true \
     -XepOpt:NullAway:AnnotatedPackages=info.jab.oop
</arg>
```

## Challenge Tasks

### Task 1: Data Processing Pipeline 🔥

Create a robust data processing pipeline that handles various data transformations with null safety.

**Requirements:**
- Create a `DataProcessor` class in `src/main/java/info/jab/oop/pipeline/`
- Implement a chain of data transformations (validation, filtering, mapping, aggregation)
- Handle nullable inputs and ensure non-null outputs where appropriate
- Use generics with proper null safety annotations
- Include comprehensive error handling

**Key Areas to Focus On:**
- Method parameter and return type annotations
- Generic type parameter nullability (`List<@Nullable String>` vs `@Nullable List<String>`)
- Null-safe operations with Optional
- Custom exception types for different failure modes

### Task 2: Configuration Management System 🔥

Build a type-safe configuration management system that loads settings from various sources.

**Requirements:**
- Create a `ConfigurationManager` class in `src/main/java/info/jab/oop/config/`
- Support multiple configuration sources (properties files, environment variables, command line)
- Implement a hierarchical configuration system with defaults and overrides
- Provide null-safe getter methods with proper return type annotations
- Handle missing configuration values gracefully

**Key Areas to Focus On:**
- Nullable vs non-null configuration values
- Default value handling
- Type-safe configuration access
- Builder pattern with null safety

### Task 3: Event-Driven Architecture 🔥🔥

Implement an event-driven system with publishers, subscribers, and event routing.

**Requirements:**
- Create an event system in `src/main/java/info/jab/oop/events/`
- Implement `EventPublisher`, `EventSubscriber`, and `EventRouter` classes
- Support different event types with proper null safety
- Handle subscription management and event delivery
- Implement async event processing with proper null safety

**Key Areas to Focus On:**
- Generic event types with null safety
- Callback interfaces with nullable parameters
- Thread-safe operations with null checking
- Error handling in async contexts

### Task 4: Legacy Code Integration 🔥🔥

Integrate with "legacy" code that doesn't use null safety annotations.

**Requirements:**
- Create a `LegacyAdapter` class in `src/main/java/info/jab/oop/legacy/`
- Simulate legacy APIs that return potentially null values
- Create safe wrapper methods that provide null-safe interfaces
- Implement defensive programming patterns
- Use `@NullUnmarked` appropriately for legacy integration

**Key Areas to Focus On:**
- Transitioning from unannotated to annotated code
- Using `@SuppressWarnings` judiciously
- Defensive null checking
- Documentation of null safety assumptions

### Task 5: Advanced Generic Patterns 🔥🔥🔥

Implement advanced patterns involving generics, inheritance, and null safety.

**Requirements:**
- Create a `Repository<T>` pattern in `src/main/java/info/jab/oop/repository/`
- Implement generic CRUD operations with proper null safety
- Support query builders with fluent interfaces
- Handle inheritance hierarchies with null safety
- Implement visitor pattern with null-safe operations

**Key Areas to Focus On:**
- Bounded type parameters with null annotations
- Variance and null safety (`? extends T` with nullability)
- Generic method null safety
- Intersection types and null safety

## Testing Requirements

For each task, create comprehensive tests that:

1. **Verify Null Safety**: Test that null inputs are handled appropriately
2. **Test Error Conditions**: Ensure proper exception handling
3. **Validate Annotations**: Verify that NullAway catches null safety violations
4. **Integration Testing**: Test components working together

### Test Structure Example:

```java
@NullMarked
class DataProcessorTest {

    @Test
    void shouldProcessValidData() {
        // Test with valid, non-null inputs
    }

    @Test
    void shouldHandleNullableInputs() {
        // Test with nullable inputs where allowed
    }

    @Test
    void shouldRejectNullInputsWhereRequired() {
        // Test that null inputs throw appropriate exceptions
    }
}
```

## Validation and Build

### Compile-Time Validation

Run these commands to validate your implementation:

```bash
# Clean build with null safety checking
mvn clean compile

# Run tests with null safety validation
mvn test

# Generate detailed error reports
mvn compile -X
```

### Expected Outcomes

- **Zero NullAway Violations**: Your code should compile without null safety warnings
- **All Tests Pass**: Comprehensive test coverage should pass
- **Clean Error Handling**: Proper exception messages and handling
- **Production Ready**: Code should be suitable for production use

## Advanced Challenges (Optional)

### Performance Optimization
- Benchmark null checking overhead
- Implement null-safe caching strategies
- Optimize hot paths while maintaining null safety

### Integration with External Libraries
- Integrate with Jackson for JSON processing
- Work with Spring Framework components
- Handle ORM entity null safety

### Documentation and Tooling
- Generate null safety documentation
- Create custom ErrorProne checks
- Implement IDE inspection rules

## Success Criteria

You've successfully completed the challenge when:

1. ✅ All code compiles without NullAway violations
2. ✅ All tests pass with comprehensive coverage
3. ✅ Proper use of JSpecify annotations throughout
4. ✅ Clean separation between nullable and non-null APIs
5. ✅ Robust error handling and defensive programming
6. ✅ Clear documentation of null safety contracts

## Resources

- [JSpecify User Guide](https://jspecify.dev/docs/user-guide)
- [NullAway Documentation](https://github.com/uber/NullAway)
- [ErrorProne Documentation](https://errorprone.info/)
- [Effective Java - Items on Null Safety](https://www.oreilly.com/library/view/effective-java-3rd/9780134686097/)

## Getting Started

1. Fork this repository
2. Choose a task to start with (Task 1 is recommended for beginners)
3. Create the required classes and tests
4. Run `mvn clean compile` frequently to check for null safety violations
5. Iterate until all violations are resolved
6. Submit your solution with comprehensive tests

Happy coding, and welcome to the world of null-safe Java! 🚀

---

**Note**: This challenge is designed to be completed incrementally. Start with simpler tasks and progressively work on more complex scenarios. The key is to understand the principles of null safety and apply them consistently throughout your codebase.
