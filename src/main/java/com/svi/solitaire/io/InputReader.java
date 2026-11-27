package com.svi.solitaire.io;

import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.exceptions.InvalidInputException;
import com.svi.solitaire.utils.InputUtil;

import java.util.Scanner;

/**
 * Utility class for reading User Keyboard Input
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class InputReader {
    private static final int GAME_LIMIT = 10_000;
    private static final int OUT_SHUFFLE_LIMIT = 8;
    private static final int IN_SHUFFLE_LIMIT = 52;

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
                int numInput = InputUtil.readIntInput(keyboardInput);
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
                int numInput = InputUtil.readIntInput(keyboardInput);
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
                games = InputUtil.readIntInput(keyboardInput);
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
     * This reads the number of Faro Shuffles to be run by the program using the given Scanner object.
     *
     * <p>
     * The user input must be of type {@code int} and be greater than {@code 0}
     * </p>
     *
     * @param keyboardInput the given Scanner object
     * @return the number of Shuffles
     */
    public static int readNumFaroShuffle(Scanner keyboardInput, Shuffle shuffleType) {
        int shuffles = 0;
        boolean validInput = false;
        while (!validInput) {
            int maxShuffle;
            if(shuffleType == Shuffle.OUT_FARO) {
                maxShuffle = OUT_SHUFFLE_LIMIT;
            } else {
                maxShuffle = IN_SHUFFLE_LIMIT;
            }
            System.out.println("-----------------------------------------");
            System.out.println("ENTER AMOUNT OF Shuffles (Max. " + maxShuffle + ")");
            try {
                shuffles = InputUtil.readIntInput(keyboardInput);
                if (shuffles < 1 || shuffles > maxShuffle) {
                    throw new InvalidInputException();
                }
                validInput = true;
            } catch (InvalidInputException iie) {
                System.out.println("Please enter an amount from 1 to " + maxShuffle);
            }
        }
        return shuffles;
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
                int numInput = InputUtil.readIntInput(keyboardInput);
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
                int numInput = InputUtil.readIntInput(keyboardInput);
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
}
