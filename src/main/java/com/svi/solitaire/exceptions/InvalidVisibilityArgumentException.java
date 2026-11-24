package com.svi.solitaire.exceptions;

/**
 * Exception thrown when the Visibility flag in the program arguments is invalid.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class InvalidVisibilityArgumentException extends RuntimeException {
    /**
     * Construct a new Invalid Visibility Argument Exception with the given message.
     *
     * @param message the given message
     */
    public InvalidVisibilityArgumentException(String message) {
        super(message);
    }
}
