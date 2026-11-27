package com.svi.solitaire.utils;

import com.svi.solitaire.exceptions.InvalidDeckFileException;
import com.svi.solitaire.io.FileLoader;

import java.io.InputStream;

/**
 * Utility class for validating a Deck input stream.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class DeckUtil {
    /**
     * This validates the given Deck Input Stream.
     *
     * @param inputStream the given Input Stream
     * @return {@code true} if the Input Stream is valid, {@code false} otherwise
     */
    public static boolean validateDeckFile(InputStream inputStream) {
        boolean valid = false;
        try {
            FileLoader.loadDeckFromFile(inputStream);
            valid = true;
        } catch (InvalidDeckFileException idfe) {
            System.out.println(idfe);
        }
        return valid;
    }
}
