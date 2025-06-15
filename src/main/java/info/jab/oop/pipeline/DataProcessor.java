package info.jab.oop.pipeline;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A robust data processing pipeline that handles various data transformations
 * with comprehensive null safety using JSpecify annotations.
 *
 * This class provides a chain of data transformations including:
 * - Validation of input data
 * - Filtering based on predicates
 * - Mapping/transformation of data
 * - Aggregation of results
 */
@NullMarked
public class DataProcessor<T> {

    /**
     * Represents a data processing operation result.
     */
    public static class ProcessingResult<R> {
        private final List<R> data;
        private final List<String> warnings;
        private final boolean hasErrors;

        public ProcessingResult(List<R> data, List<String> warnings, boolean hasErrors) {
            this.data = List.copyOf(data);
            this.warnings = List.copyOf(warnings);
            this.hasErrors = hasErrors;
        }

        public List<R> getData() {
            return data;
        }

        public List<String> getWarnings() {
            return warnings;
        }

        public boolean hasErrors() {
            return hasErrors;
        }

        public boolean hasWarnings() {
            return !warnings.isEmpty();
        }
    }

    /**
     * Configuration for data processing operations.
     */
    public static class ProcessingConfig {
        private final boolean strictMode;
        private final boolean skipNullValues;
        private final int maxErrorCount;

        public ProcessingConfig(boolean strictMode, boolean skipNullValues, int maxErrorCount) {
            this.strictMode = strictMode;
            this.skipNullValues = skipNullValues;
            this.maxErrorCount = maxErrorCount;
        }

        public static ProcessingConfig defaultConfig() {
            return new ProcessingConfig(false, true, 10);
        }

        public static ProcessingConfig strictConfig() {
            return new ProcessingConfig(true, false, 0);
        }

        public boolean isStrictMode() {
            return strictMode;
        }

        public boolean shouldSkipNullValues() {
            return skipNullValues;
        }

        public int getMaxErrorCount() {
            return maxErrorCount;
        }
    }

    private final ProcessingConfig config;
    private final List<String> processingWarnings;

    public DataProcessor() {
        this(ProcessingConfig.defaultConfig());
    }

    public DataProcessor(ProcessingConfig config) {
        this.config = config;
        this.processingWarnings = new ArrayList<>();
    }

    /**
     * Validates a list of input data, handling null elements gracefully.
     *
     * @param input the input data to validate
     * @param validator the validation function that checks each non-null element
     * @return a list of valid, non-null elements
     * @throws DataProcessingException if validation fails in strict mode (unchecked)
     */
    public List<T> validate(List<T> input, Predicate<T> validator) {
        if (Objects.isNull(input)) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.NULL_INPUT_ERROR,
                "Input list cannot be null"
            );
        }

        List<T> validData = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            @Nullable T item = input.get(i);

            if (Objects.isNull(item)) {
                String error = "Null value found at index " + i;
                if (config.shouldSkipNullValues()) {
                    processingWarnings.add(error + " (skipped)");
                    continue;
                } else {
                    errors.add(error);
                    if (config.isStrictMode() || errors.size() >= config.getMaxErrorCount()) {
                        throw new DataProcessingException(
                            DataProcessingException.ErrorType.VALIDATION_ERROR,
                            "Validation failed: " + String.join(", ", errors)
                        );
                    }
                    continue;
                }
            }

            try {
                if (validator.test(item)) {
                    validData.add(item);
                } else {
                    String error = "Validation failed for item at index " + i + ": " + item;
                    errors.add(error);
                    if (config.isStrictMode() || errors.size() >= config.getMaxErrorCount()) {
                        throw new DataProcessingException(
                            DataProcessingException.ErrorType.VALIDATION_ERROR,
                            "Validation failed: " + String.join(", ", errors)
                        );
                    }
                }
            } catch (Exception e) {
                String error = "Validation error at index " + i + ": " + e.getMessage();
                errors.add(error);
                if (config.isStrictMode()) {
                    throw new DataProcessingException(
                        DataProcessingException.ErrorType.VALIDATION_ERROR,
                        error,
                        e
                    );
                }
            }
        }

        if (!errors.isEmpty() && !config.isStrictMode()) {
            processingWarnings.addAll(errors.stream()
                .map(error -> error + " (ignored in non-strict mode)")
                .collect(Collectors.toList()));
        }

        return validData;
    }

    /**
     * Filters data based on a predicate.
     *
     * @param input the input data to filter, must not be null
     * @param filter the filter predicate
     * @return filtered data
     * @throws DataProcessingException if input is null (unchecked)
     */
    public List<T> filter(List<T> input, Predicate<T> filter) {
        if (Objects.isNull(input)) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.NULL_INPUT_ERROR,
                "Input list cannot be null"
            );
        }

        try {
            return input.stream()
                    .filter(Objects::nonNull)
                    .filter(filter)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.TRANSFORMATION_ERROR,
                "Filtering failed: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * Maps/transforms data from one type to another.
     *
     * @param <R> the result type
     * @param input the input data to transform
     * @param mapper the transformation function
     * @return transformed data
     * @throws DataProcessingException if transformation fails (unchecked)
     */
    public <R> List<R> map(List<T> input, Function<T, R> mapper) {
        if (Objects.isNull(input)) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.NULL_INPUT_ERROR,
                "Input list cannot be null"
            );
        }

        List<R> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            T item = input.get(i);
            try {
                R result = mapper.apply(item);
                if (Objects.nonNull(result)) {
                    results.add(result);
                } else {
                    String warning = "Transformation returned null for item at index " + i;
                    if (config.shouldSkipNullValues()) {
                        processingWarnings.add(warning + " (skipped)");
                    } else {
                        errors.add(warning);
                        if (config.isStrictMode()) {
                            throw new DataProcessingException(
                                DataProcessingException.ErrorType.TRANSFORMATION_ERROR,
                                warning
                            );
                        }
                    }
                }
            } catch (Exception e) {
                String error = "Transformation failed for item at index " + i + ": " + e.getMessage();
                errors.add(error);
                if (config.isStrictMode()) {
                    throw new DataProcessingException(
                        DataProcessingException.ErrorType.TRANSFORMATION_ERROR,
                        error,
                        e
                    );
                }
            }
        }

        if (!errors.isEmpty() && !config.isStrictMode()) {
            processingWarnings.addAll(errors.stream()
                .map(error -> error + " (ignored in non-strict mode)")
                .collect(Collectors.toList()));
        }

        return results;
    }

    /**
     * Maps/transforms data from one type to another, supporting nullable results.
     * This is a private method used internally by the pipeline.
     *
     * @param <R> the result type
     * @param input the input data to transform
     * @param mapper the transformation function that may return null
     * @return transformed data
     * @throws DataProcessingException if transformation fails (unchecked)
     */
    private <R> List<R> mapWithNullable(List<T> input, Function<T, @Nullable R> mapper) {
        if (Objects.isNull(input)) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.NULL_INPUT_ERROR,
                "Input list cannot be null"
            );
        }

        List<R> results = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            T item = input.get(i);
            try {
                @Nullable R result = mapper.apply(item);
                if (Objects.nonNull(result)) {
                    results.add(result);
                } else {
                    String warning = "Transformation returned null for item at index " + i;
                    if (config.shouldSkipNullValues()) {
                        processingWarnings.add(warning + " (skipped)");
                    } else {
                        errors.add(warning);
                        if (config.isStrictMode()) {
                            throw new DataProcessingException(
                                DataProcessingException.ErrorType.TRANSFORMATION_ERROR,
                                warning
                            );
                        }
                    }
                }
            } catch (Exception e) {
                String error = "Transformation failed for item at index " + i + ": " + e.getMessage();
                errors.add(error);
                if (config.isStrictMode()) {
                    throw new DataProcessingException(
                        DataProcessingException.ErrorType.TRANSFORMATION_ERROR,
                        error,
                        e
                    );
                }
            }
        }

        if (!errors.isEmpty() && !config.isStrictMode()) {
            processingWarnings.addAll(errors.stream()
                .map(error -> error + " (ignored in non-strict mode)")
                .collect(Collectors.toList()));
        }

        return results;
    }

    /**
     * Aggregates data using a reduction operation.
     *
     * @param <R> the result type
     * @param input the input data to aggregate
     * @param identity the identity value for the reduction
     * @param accumulator the accumulation function
     * @return the aggregated result
     * @throws DataProcessingException if aggregation fails (unchecked)
     */
    public <R> R aggregate(List<T> input, R identity, BinaryOperator<R> accumulator, Function<T, R> mapper) {
        if (Objects.isNull(input)) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.NULL_INPUT_ERROR,
                "Input list cannot be null"
            );
        }

        try {
            return input.stream()
                    .filter(Objects::nonNull)
                    .map(mapper)
                    .filter(Objects::nonNull)
                    .reduce(identity, accumulator);
        } catch (Exception e) {
            throw new DataProcessingException(
                DataProcessingException.ErrorType.AGGREGATION_ERROR,
                "Aggregation failed: " + e.getMessage(),
                e
            );
        }
    }

    /**
     * Processes data through the complete pipeline: validate → filter → map → aggregate.
     *
     * @param <R> the result type
     * @param input the input data
     * @param validator validation predicate
     * @param filter filtering predicate
     * @param mapper transformation function
     * @param identity identity value for aggregation
     * @param accumulator accumulation function
     * @param aggregateMapper mapper for aggregation
     * @return processing result with data, warnings, and error status
     */
    public <R> ProcessingResult<R> processPipeline(
            List<T> input,
            Predicate<T> validator,
            Predicate<T> filter,
            Function<T, @Nullable R> mapper,
            R identity,
            BinaryOperator<R> accumulator,
            Function<T, R> aggregateMapper) {

        processingWarnings.clear();
        boolean hasErrors = false;
        List<R> resultData = new ArrayList<>();

        try {
            // Step 1: Validate
            List<T> validatedData = validate(input, validator);

            // Step 2: Filter
            List<T> filteredData = filter(validatedData, filter);

            // Step 3: Map
            List<R> mappedData = mapWithNullable(filteredData, mapper);
            resultData.addAll(mappedData);

            // Step 4: Aggregate (optional, adds aggregated result to the list)
            if (!filteredData.isEmpty()) {
                R aggregatedResult = aggregate(filteredData, identity, accumulator, aggregateMapper);
                resultData.add(aggregatedResult);
            }

        } catch (DataProcessingException e) {
            hasErrors = true;
            processingWarnings.add("Pipeline failed: " + e.getMessage());
        }

        return new ProcessingResult<>(resultData, new ArrayList<>(processingWarnings), hasErrors);
    }

    /**
     * Gets the current processing warnings.
     *
     * @return list of warning messages
     */
    public List<String> getWarnings() {
        return List.copyOf(processingWarnings);
    }

    /**
     * Clears all accumulated warnings.
     */
    public void clearWarnings() {
        processingWarnings.clear();
    }

    /**
     * Gets the current processing configuration.
     *
     * @return the processing configuration
     */
    public ProcessingConfig getConfig() {
        return config;
    }
}
