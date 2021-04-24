package rw.rutakayile.k8sproject.exception;

/**
 * A Custom {@link RuntimeException} class that should be thrown on invalid token event
 *
 * @author Samuel Rutakayile
 * @version 1.0
 * @since 1.0
 */
public class EntityNotFoundException extends RuntimeException {
  public EntityNotFoundException(String message) {
    super(message);
  }
}
