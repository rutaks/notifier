package rw.rutakayile.k8sproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rw.rutakayile.k8sproject.dto.ApiResponse;
import rw.rutakayile.k8sproject.exception.EntityNotFoundException;
import rw.rutakayile.k8sproject.exception.ValidationException;

import javax.validation.constraints.Null;

/**
 * This {@link GlobalExceptionHandler} handles all Api exceptions
 *
 * @author Samuel Rutakayile
 * @version 1.0
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Detailed error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiResponse<Object>> handleNullPointerError(NullPointerException ex) {
        ApiResponse<Object> body =
                new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(ValidationException ex) {
        ApiResponse<Object> body =
                new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrors());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDBErrors(DataIntegrityViolationException ex) {
        String[] errorMessage = ex.getMostSpecificCause().getMessage().split("\n");
        ApiResponse<Object> body;

        if(errorMessage[1] != null) {
            body = new ApiResponse<>(HttpStatus.BAD_REQUEST, errorMessage[1], null);
        } else {
            body = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMostSpecificCause().getMessage(), null);
        }

        return ResponseEntity.badRequest().body(body);
    }
}
