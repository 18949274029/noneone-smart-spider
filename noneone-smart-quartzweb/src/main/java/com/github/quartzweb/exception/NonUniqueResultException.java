
package com.github.quartzweb.exception;

/**
 * @author leisure
 * @className NonUniqueResultException

 */
public class NonUniqueResultException extends RuntimeException {

    public NonUniqueResultException() {
    }

    public NonUniqueResultException(String message) {
        super(message);
    }

    public NonUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqueResultException(Throwable cause) {
        super(cause);
    }
}
