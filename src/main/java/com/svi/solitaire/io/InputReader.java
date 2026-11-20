package com.svi.solitaire.io;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.exceptions.InvalidInputException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputReader {
    public static int readDrawCount(Scanner keyboardInput) {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                    ENTER DRAW TYPE
                    1. Draw One
                    2. Draw Three""");
            try {
                int numInput = readIntInput(keyboardInput);
                choice = switch (numInput) {
                    case 1 -> 1;
                    case 2 -> 2;
                    default -> throw new InvalidInputException();
                };
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter a value from the given options.");
            }
        }
        return choice;
    }

    public static Shuffle readShuffleType(Scanner keyboardInput) {
        Shuffle choice = null;
        boolean validInput = false;
        while (!validInput) {
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
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please select a valid option.");
            }
        }
        return choice;
    }

    public static boolean readExitGameChoice(Scanner keyboardInput) {
        boolean choice = false;
        boolean validInput = false;
        while (!validInput) {
            try {
                int numInput = readIntInput(keyboardInput);
                choice = switch (numInput) {
                    case 1 -> true;
                    case 2 -> false;
                    default -> throw new InvalidInputException();
                };
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please select a valid option.");
            }
        }
        return choice;
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
