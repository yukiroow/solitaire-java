package com.svi.solitaire.exceptions;

/**
 * Exception thrown when the indicated Deck file in the program arguments is invalid.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class InvalidDeckFileException extends RuntimeException {
    /**
     * Construct a new Invalid Deck File Exception with a given message
     *
     * @param message the given message
     */
    public InvalidDeckFileException(String message) {
        super(message);
    }
}
