package rw.rutakayile.k8sproject.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException {

  private Map<String, String> errors;

  public ValidationException(String message, Map<String, String> errors) {
    super(message);
    this.errors = errors;
  }

  public ValidationException(String message) {
    super(message);
  }
}
