package com.example.spacecatsmarket.web.exception;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.service.exception.PermissionDeniedException;
import com.example.spacecatsmarket.service.exception.ProductListEmptyException;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;
import com.example.spacecatsmarket.util.ProblemDetailUtils;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation failed: {}", ex.getMessage(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("One or more fields failed validation.");
        String path = request.getDescription(false).replace("uri=", "");
        problemDetail.setInstance(URI.create(path));
        problemDetail.setProperty("timestamp", LocalDateTime.now().toString());

        List<Violation> violations = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> Violation.builder()
                .field(fieldError.getField())
                .message(fieldError.getDefaultMessage())
                .build())
            .collect(Collectors.toList());

        problemDetail.setProperty("violations", violations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleProductNotFound(ProductNotFoundException ex, WebRequest request) {
        log.error("Product not found: {}", ex.getMessage(), ex);
        return ProblemDetailUtils.createProblemDetailResponse(HttpStatus.NOT_FOUND, "Product Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(ProductListEmptyException.class)
    public ResponseEntity<ProblemDetail> handleProductListEmpty(ProductListEmptyException ex, WebRequest request) {
        log.error("Product list is empty: {}", ex.getMessage(), ex);
        return ProblemDetailUtils.createProblemDetailResponse(HttpStatus.NOT_FOUND, "No Products Available", ex.getMessage(), request);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ProblemDetail> handlePermissionDenied(PermissionDeniedException ex, WebRequest request) {
        log.error("Permission denied: {}", ex.getMessage(), ex);
        return ProblemDetailUtils.createProblemDetailResponse(HttpStatus.FORBIDDEN, "Permission Denied", ex.getMessage(), request);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCustomerNotFound(CustomerNotFoundException ex, WebRequest request) {
        log.error("Customer not found: {}", ex.getMessage(), ex);
        return ProblemDetailUtils.createProblemDetailResponse(HttpStatus.NOT_FOUND, "Customer Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        return ProblemDetailUtils.createProblemDetailResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", 
                                                            ex.getMessage(), request);
    }
}
