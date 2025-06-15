package info.jab.oop.pipeline;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Comprehensive tests for DataProcessor that verify null safety,
 * error handling, and processing pipeline functionality.
 */
@NullMarked
class DataProcessorTest {

    private DataProcessor<String> processor;
    private DataProcessor<String> strictProcessor;

    @BeforeEach
    void setUp() {
        processor = new DataProcessor<>();
        strictProcessor = new DataProcessor<>(DataProcessor.ProcessingConfig.strictConfig());
    }

    @Test
    @DisplayName("Should process valid data successfully")
    void shouldProcessValidData() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "cherry");

        // When
        List<String> result = processor.validate(input, s -> s.length() > 3);

        // Then
        assertThat(result).containsExactly("apple", "banana", "cherry");
        assertThat(processor.getWarnings()).isEmpty();
    }

    @Test
    @DisplayName("Should handle nullable inputs in default mode")
    void shouldHandleNullableInputsInDefaultMode() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana", null, "cherry");

        // When
        List<String> result = processor.validate(input, s -> s.length() > 3);

        // Then
        assertThat(result).containsExactly("apple", "banana", "cherry");
        assertThat(processor.getWarnings())
            .hasSize(2)
            .allMatch(warning -> warning.contains("Null value found") && warning.contains("(skipped)"));
    }

    @Test
    @DisplayName("Should reject null inputs in strict mode")
    void shouldRejectNullInputsInStrictMode() {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana");

        // When & Then
        assertThatThrownBy(() -> strictProcessor.validate(input, s -> s.length() > 3))
            .isInstanceOf(DataProcessingException.class)
            .hasMessageContaining("Null value found at index 1")
            .extracting(ex -> ((DataProcessingException) ex).getErrorType())
            .isEqualTo(DataProcessingException.ErrorType.VALIDATION_ERROR);
    }

    @Test
    @DisplayName("Should reject null input list")
    @SuppressWarnings("NullAway")
    void shouldRejectNullInputList() {
        // When & Then
        assertThatThrownBy(() -> processor.validate(null, s -> s != null && s.length() > 0))
            .isInstanceOf(DataProcessingException.class)
            .hasMessageContaining("Input list cannot be null")
            .extracting(ex -> ((DataProcessingException) ex).getErrorType())
            .isEqualTo(DataProcessingException.ErrorType.NULL_INPUT_ERROR);
    }

    @Test
    @DisplayName("Should handle validation failures gracefully")
    void shouldHandleValidationFailuresGracefully() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("a", "bb", "ccc", "dddd");

        // When
        List<String> result = processor.validate(input, s -> s.length() >= 3);

        // Then
        assertThat(result).containsExactly("ccc", "dddd");
        assertThat(processor.getWarnings())
            .hasSize(2)
            .anyMatch(warning -> warning.contains("Validation failed for item at index 0"))
            .anyMatch(warning -> warning.contains("Validation failed for item at index 1"));
    }

    @Test
    @DisplayName("Should filter data correctly")
    void shouldFilterDataCorrectly() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "apricot", "banana", "avocado");

        // When
        List<String> result = processor.filter(input, s -> s.startsWith("a"));

        // Then
        assertThat(result).containsExactly("apple", "apricot", "avocado");
    }

    @Test
    @DisplayName("Should handle null filter input")
    @SuppressWarnings("NullAway")
    void shouldHandleNullFilterInput() {
        // When & Then
        assertThatThrownBy(() -> processor.filter(null, s -> s != null && s.length() > 0))
            .isInstanceOf(DataProcessingException.class)
            .hasMessageContaining("Input list cannot be null")
            .extracting(ex -> ((DataProcessingException) ex).getErrorType())
            .isEqualTo(DataProcessingException.ErrorType.NULL_INPUT_ERROR);
    }

    @Test
    @DisplayName("Should map data with transformations")
    void shouldMapDataWithTransformations() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "cherry");

        // When
        List<Integer> result = processor.map(input, String::length);

        // Then
        assertThat(result).containsExactly(5, 6, 6);
    }

    @Test
    @DisplayName("Should handle null mapping results")
    @SuppressWarnings("NullAway")
    void shouldHandleNullMappingResults() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "cherry");

        // When
        List<String> result = processor.map(input, s -> s.length() > 5 ? s.toUpperCase(Locale.ROOT) : null);

        // Then
        assertThat(result).containsExactly("BANANA", "CHERRY");
        assertThat(processor.getWarnings())
            .hasSize(1)
            .allMatch(warning -> warning.contains("Transformation returned null") && warning.contains("(skipped)"));
    }

    @Test
    @DisplayName("Should aggregate data correctly")
    void shouldAggregateDataCorrectly() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "cherry");

        // When
        Integer totalLength = processor.aggregate(input, 0, Integer::sum, String::length);

        // Then
        assertThat(totalLength).isEqualTo(17); // 5 + 6 + 6
    }

    @Test
    @DisplayName("Should handle empty list aggregation")
    void shouldHandleEmptyListAggregation() throws DataProcessingException {
        // Given
        List<String> input = Collections.emptyList();

        // When
        Integer result = processor.aggregate(input, 42, Integer::sum, String::length);

        // Then
        assertThat(result).isEqualTo(42); // Identity value
    }

    @Test
    @DisplayName("Should process complete pipeline successfully")
    @SuppressWarnings("NullAway")
    void shouldProcessCompletePipelineSuccessfully() {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana", "a", "cherry", "apricot");

        // When
        DataProcessor.ProcessingResult<Integer> result = processor.processPipeline(
            input,
            s -> s.length() >= 3,           // validator
            s -> s.startsWith("a"),         // filter
            s -> s.length(),                // mapper - use non-nullable version
            0,                              // identity for aggregation
            Integer::sum,                   // accumulator
            String::length                  // aggregate mapper
        );

        // Then
        assertThat(result.hasErrors()).isFalse();
        assertThat(result.hasWarnings()).isTrue();
        assertThat(result.getData()).contains(5, 7, 12); // apple(5), apricot(7), sum(5+7)
        assertThat(result.getWarnings()).isNotEmpty();
    }

    @Test
    @DisplayName("Should handle pipeline failures")
    @SuppressWarnings("NullAway")
    void shouldHandlePipelineFailures() {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana");

        // When
        DataProcessor.ProcessingResult<Integer> result = strictProcessor.processPipeline(
            input,
            s -> s.length() >= 3,
            s -> true,
            s -> s.length(),
            0,
            Integer::sum,
            String::length
        );

        // Then
        assertThat(result.hasErrors()).isTrue();
        assertThat(result.getWarnings()).isNotEmpty();
        assertThat(result.getData()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("configurationTestCases")
    @DisplayName("Should respect different processing configurations")
    void shouldRespectDifferentConfigurations(DataProcessor.ProcessingConfig config,
                                             List<String> input,
                                             boolean expectError,
                                             int expectedWarnings) throws DataProcessingException {
        // Given
        DataProcessor<String> configuredProcessor = new DataProcessor<>(config);

        // When & Then
        if (expectError) {
            assertThatThrownBy(() -> configuredProcessor.validate(input, s -> s.length() > 2))
                .isInstanceOf(DataProcessingException.class);
        } else {
            configuredProcessor.validate(input, s -> s.length() > 2);
            assertThat(configuredProcessor.getWarnings()).hasSize(expectedWarnings);
        }
    }

    static Stream<Arguments> configurationTestCases() {
        return Stream.of(
            Arguments.of(
                DataProcessor.ProcessingConfig.defaultConfig(),
                Arrays.asList("apple", null, "ba"),
                false,
                2 // null skipped + validation failure ignored
            ),
            Arguments.of(
                DataProcessor.ProcessingConfig.strictConfig(),
                Arrays.asList("apple", null, "banana"),
                true,
                0 // fails immediately
            ),
            Arguments.of(
                new DataProcessor.ProcessingConfig(false, false, 1),
                Arrays.asList("apple", null, "ba"),
                true, // exceeds max error count
                0
            )
        );
    }

    @Test
    @DisplayName("Should clear warnings correctly")
    void shouldClearWarningsCorrectly() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana");
        processor.validate(input, s -> true);
        assertThat(processor.getWarnings()).isNotEmpty();

        // When
        processor.clearWarnings();

        // Then
        assertThat(processor.getWarnings()).isEmpty();
    }

    @Test
    @DisplayName("Should provide immutable warning list")
    void shouldProvideImmutableWarningList() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", null, "banana");
        processor.validate(input, s -> true);

        // When
        List<String> warnings = processor.getWarnings();

        // Then
        assertThatThrownBy(() -> warnings.add("new warning"))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Should handle mapper exceptions gracefully")
    void shouldHandleMapperExceptionsGracefully() throws DataProcessingException {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "");

        // When
        List<Integer> result = processor.map(input, s -> {
            if (s.isEmpty()) {
                throw new RuntimeException("Empty string not allowed");
            }
            return s.length();
        });

        // Then
        assertThat(result).containsExactly(5, 6);
        assertThat(processor.getWarnings())
            .hasSize(1)
            .anyMatch(warning -> warning.contains("Transformation failed") &&
                               warning.contains("Empty string not allowed"));
    }

    @Test
    @DisplayName("Should validate processing result immutability")
    @SuppressWarnings("NullAway")
    void shouldValidateProcessingResultImmutability() {
        // Given
        List<String> input = Arrays.asList("apple", "banana", "cherry");

        // When
        DataProcessor.ProcessingResult<Integer> result = processor.processPipeline(
            input,
            s -> s.length() >= 3,
            s -> true,
            s -> s.length(),
            0,
            Integer::sum,
            String::length
        );

        // Then
        assertThat(result.getData()).isNotNull();
        assertThat(result.getWarnings()).isNotNull();
        assertThatThrownBy(() -> result.getData().clear())
            .isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> result.getWarnings().clear())
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("Should create default and strict configurations correctly")
    void shouldCreateConfigurationsCorrectly() {
        // When
        DataProcessor.ProcessingConfig defaultConfig = DataProcessor.ProcessingConfig.defaultConfig();
        DataProcessor.ProcessingConfig strictConfig = DataProcessor.ProcessingConfig.strictConfig();

        // Then
        assertThat(defaultConfig.isStrictMode()).isFalse();
        assertThat(defaultConfig.shouldSkipNullValues()).isTrue();
        assertThat(defaultConfig.getMaxErrorCount()).isEqualTo(10);

        assertThat(strictConfig.isStrictMode()).isTrue();
        assertThat(strictConfig.shouldSkipNullValues()).isFalse();
        assertThat(strictConfig.getMaxErrorCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should format exception toString correctly")
    void shouldFormatExceptionToStringCorrectly() {
        // Given
        DataProcessingException exception = new DataProcessingException(
            DataProcessingException.ErrorType.VALIDATION_ERROR,
            "Test error message"
        );

        // When
        String result = exception.toString();

        // Then
        assertThat(result)
            .contains("DataProcessingException")
            .contains("VALIDATION_ERROR")
            .contains("Test error message");
    }
}
