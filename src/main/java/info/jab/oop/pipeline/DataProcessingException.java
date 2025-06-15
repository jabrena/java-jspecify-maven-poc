package info.jab.oop.pipeline;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Runtime exception thrown when data processing operations fail.
 * Provides specific error types for different failure modes.
 *
 * <p>This is an unchecked exception since data processing errors typically represent:
 * <ul>
 *   <li>Programming errors (null inputs, invalid transformations)</li>
 *   <li>Data quality issues (validation failures)</li>
 *   <li>Configuration problems (invalid aggregation logic)</li>
 * </ul>
 *
 * <p>Callers can choose to handle these exceptions or let them bubble up.
 * The {@link DataProcessor#processPipeline} method provides a safe wrapper
 * that captures exceptions and returns them as part of {@link DataProcessor.ProcessingResult}.
 */
@NullMarked
public class DataProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Types of data processing errors that can occur.
     */
    public enum ErrorType {
        VALIDATION_ERROR,
        TRANSFORMATION_ERROR,
        AGGREGATION_ERROR,
        NULL_INPUT_ERROR
    }

    private final ErrorType errorType;

    public DataProcessingException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public DataProcessingException(ErrorType errorType, String message, @Nullable Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        return String.format("DataProcessingException[type=%s, message=%s]",
                           errorType, super.getMessage());
    }
}
