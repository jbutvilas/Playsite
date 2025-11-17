package lt.jonas.homework.playsite.exception.handler;

import lt.jonas.homework.playsite.exception.model.PlaySiteOutOfCapacityException;
import lt.jonas.homework.playsite.exception.model.ResourceNotFoundException;
import lt.jonas.homework.playsite.exception.model.TicketUsedException;
import lt.jonas.homework.playsite.model.api.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling exceptions across the application.
 * It provides consistent error response interface for different types of exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";
    private static final String TICKET_ALREADY_USED = "TICKET_ALREADY_USED";
    private static final String PLAYSITE_OUT_OF_CAPACITY = "PLAYSITE_OUT_OF_CAPACITY";
    private static final String METHOD_NOT_SUPPORTED = "METHOD_NOT_SUPPORTED";
    private static final String INVALID_REQUEST = "INVALID_REQUEST";

    /**
     * Handles {@link TicketUsedException}.
     *
     * @return a response entity with the error response and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(TicketUsedException.class)
    public ResponseEntity<ErrorResponseDto> handleTicketUsedException(TicketUsedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), TICKET_ALREADY_USED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles {@link ResourceNotFoundException}.
     *
     * @return a response entity with the error response and HTTP status 404 (Not Found)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), RESOURCE_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles {@link PlaySiteOutOfCapacityException}.
     *
     * @return a response entity with the error response and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(PlaySiteOutOfCapacityException.class)
    public ResponseEntity<ErrorResponseDto> handlePlaysiteOutOfCapacityException(PlaySiteOutOfCapacityException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), PLAYSITE_OUT_OF_CAPACITY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles {@link MethodArgumentNotValidException}.
     *
     * @return a response entity with the error response and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMessage.append(error.getField())
                .append(": ").append(error.getDefaultMessage()).append("; "));
        ErrorResponseDto errorResponse = new ErrorResponseDto(errorMessage.toString(), INVALID_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Handles {@link HttpRequestMethodNotSupportedException}.
     *
     * @return a response entity with the error response and HTTP status 405 (Method Not Allowed)
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), METHOD_NOT_SUPPORTED);
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handles {@link HttpMessageConversionException}.
     *
     * @return a response entity with the error response and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageConversionException(HttpMessageConversionException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(), INVALID_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all unexpected exceptions that are not explicitly handled.
     *
     * @return a response entity with the error response and HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalExceptions() {
        ErrorResponseDto errorResponse = new ErrorResponseDto("An unexpected error occurred", "INTERNAL_SERVER_ERROR");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
