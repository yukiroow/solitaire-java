package com.svi.solitaire.utils;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUtil {
    /**
     * This reads an {@code int} input using the given Scanner object.
     *
     * @param keyboardInput the given Scanner object
     * @return the {@code int} input
     */
    public static int readIntInput(Scanner keyboardInput) {
        try {
            System.out.print("> ");
            return Integer.parseInt(keyboardInput.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid value.");
        } catch (NoSuchElementException nsee) {
            System.out.println("The scanner object has been forcibly closed.");
            throw new RuntimeException(nsee);
        }
        return -1;
    }
}
