package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.exceptions.InvalidInputException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUtil {
    public static int readDrawCount(Scanner keyboardInput) {
        int choice;
        while (true) {
            System.out.println("""
                    ENTER DRAW TYPE
                    1. Draw 1
                    2. Draw 3""");
            System.out.print("\n> ");
            try {
                choice = Integer.parseInt(keyboardInput.nextLine().trim());
                if (choice < 1 || choice > 2) {
                    throw new InvalidInputException();
                }
                return choice;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter a value from the given options.");
            }
        }
    }

    public static Shuffle readShuffleType(Scanner keyboardInput) {
        Shuffle choice;
        while (true) {
            System.out.println("""
                    ENTER SHUFFLE TYPE
                    1. Out-Faro Shuffle
                    2. In-Faro Shuffle
                    3. Random Shuffle
                    4. Leave deck as-is""");
            try {
                int numInput = readIntInput(keyboardInput);
                choice = switch (numInput) {
                    case 1 -> Shuffle.OUT_FARO;
                    case 2 -> Shuffle.IN_FARO;
                    case 3 -> Shuffle.RANDOM;
                    case 4 -> Shuffle.AS_IS;
                    default -> throw new InvalidInputException();
                };
                return choice;
            } catch (InvalidInputException iie) {
                System.out.println("Please select a valid option.");
            }
        }
    }

    private static int readIntInput(Scanner keyboardInput) {
        try {
            System.out.print("> ");
            return Integer.parseInt(keyboardInput.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Please enter a valid value.");
        } catch (NoSuchElementException nsee) {
            System.out.println("The scanner object has been forcibly closed.");
            System.exit(1);
        }
        return -1;
    }
}
