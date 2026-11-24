package com.svi.solitaire.io;

import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.exceptions.InvalidInputException;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Utility class for reading User Keyboard Input
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class InputReader {
    private static final int GAME_LIMIT = 10_000;

    /**
     * This reads the Draw Count setting for the Game using the given Scanner object
     *
     * <p>
     * The user input must be of type {@code int} with a value of {@code 1} or {@code 2}.
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return the int draw count setting
     */
    public static int readDrawCount(Scanner keyboardInput) {
        int choice = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                    -----------------------------------------
                    ENTER DRAW TYPE
                    1. Draw One
                    2. Draw Three""");
            try {
                int numInput = readIntInput(keyboardInput);
                choice = switch (numInput) {
                    case 1 -> 1;
                    case 2 -> 3;
                    default -> throw new InvalidInputException();
                };
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter a value from the given options.");
            }
        }
        return choice;
    }

    /**
     * This reads and parses the Game Type setting for the Game using the given Scanner object
     *
     * <p>
     * The user input must be of type {@code int} with a value of {@code 1} or {@code 2}.
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return the parsed Game Type
     */
    public static GameType readGameType(Scanner keyboardInput) {
        GameType choice = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                    -----------------------------------------
                    ENTER GAME TYPE
                    1. Single Game
                    2. Multi Game""");
            try {
                int numInput = readIntInput(keyboardInput);
                choice = switch (numInput) {
                    case 1 -> GameType.SINGLE;
                    case 2 -> GameType.MULTI;
                    default -> throw new InvalidInputException();
                };
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter a value from the given options.");
            }
        }
        return choice;
    }

    /**
     * This reads the number of Games to be run by the program using the given Scanner object.
     *
     * <p>
     * The user input must be of type {@code int} and be greater than {@code 0}
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return the number of Games
     */
    public static int readGameCount(Scanner keyboardInput) {
        int games = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                    -----------------------------------------
                    ENTER AMOUNT OF GAMES (Max. 10,000)""");
            try {
                games = readIntInput(keyboardInput);
                if (games < 1 || games > GAME_LIMIT) {
                    throw new InvalidInputException();
                }
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter an amount from 1 to 10,000");
            }
        }
        return games;
    }

    /**
     * This reads the Shuffle Type setting for the Game using the given Scanner object
     *
     * <p>
     * The user input must be of type {@code int} with a value of {@code 1}, {@code 2}, {@code 3}, or {@code 4}.
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return the parsed ShuffleType
     */
    public static Shuffle readShuffleType(Scanner keyboardInput) {
        Shuffle choice = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("""
                    -----------------------------------------
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

    /**
     * This reads the exit Game choice for the Game using the given Scanner object
     *
     * <p>
     * The user input must be of type {@code int} with a value of {@code 1} or {@code 2}.
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return {@code true} if the input is {@code 1}, {@code false} otherwise
     */
    public static boolean readExitGameChoice(Scanner keyboardInput) {
        boolean choice = false;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("""
                    -----------------------------------------
                    1. Play Again
                    2. Exit Game""");
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

    /**
     * This reads an {@code int} input using the given Scanner object.
     *
     * @param keyboardInput the given Scanner object
     * @return the {@code int} input
     */
    private static int readIntInput(Scanner keyboardInput) {
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
