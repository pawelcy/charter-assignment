package com.charter.commons.exception;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundRuntimeException class.
 *
 * @author pawelcy
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundRuntimeException extends RuntimeException {

  @Serial private static final long serialVersionUID = 1L;

  /** Constructor for NotFoundRuntimeException. */
  public NotFoundRuntimeException() {}

  /**
   * Constructor for NotFoundRuntimeException.
   *
   * @param message a {@link java.lang.String} object
   */
  public NotFoundRuntimeException(String message) {
    super(message);
  }

  /**
   * Constructor for NotFoundRuntimeException.
   *
   * @param message a {@link java.lang.String} object
   * @param cause a {@link java.lang.Throwable} object
   */
  public NotFoundRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for NotFoundRuntimeException.
   *
   * @param cause a {@link java.lang.Throwable} object
   */
  public NotFoundRuntimeException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor for NotFoundRuntimeException.
   *
   * @param message a {@link java.lang.String} object
   * @param cause a {@link java.lang.Throwable} object
   * @param enableSuppression a boolean
   * @param writableStackTrace a boolean
   */
  protected NotFoundRuntimeException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
