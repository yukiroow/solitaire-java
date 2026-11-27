package com.svi.solitaire.io;

import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

/**
 * Utility class for displaying Game messages to the console.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class ConsolePrinter {
    /**
     * This prints the current state of the given Game Instance.
     *
     * @param gameInstance the given Game Instance
     */
    public static void printDeckState(Game gameInstance) {
        int round = gameInstance.getRounds();
        ManoeuvrePile[] manoeuvrePiles = gameInstance.getManoeuvrePiles();
        FoundationPile[] foundationPiles = gameInstance.getFoundationPiles();
        TalonPile talonPile = gameInstance.getTalonPile();

        System.out.println("-----------------------------------------");
        System.out.println("ROUND " + round);
        System.out.println("STOCK: " + talonPile.getStockPileSize() + " Cards");
        System.out.println("WASTE: " + talonPile);
        System.out.print("FOUNDATION PILES: ");
        for (FoundationPile foundationPile : foundationPiles) {
            System.out.printf("%-7s", foundationPile);
        }
        System.out.print("\nMANOEUVRE PILES: \n");
        for (ManoeuvrePile manoeuvrePile : manoeuvrePiles) {
            System.out.print("[" + (manoeuvrePile.getPileId()) + "] : ");
            System.out.println(manoeuvrePile);
        }
        System.out.println("-----------------------------------------");
        System.out.println("MOVES");
    }

    /**
     * This prints the values of the given Game Settings object.
     *
     * @param gameSettings the given Game Settings
     */
    public static void printGameSettings(GameSettings gameSettings) {
        String drawCountDisplay = gameSettings.getDrawCount() == 1 ? "1 Card" : "3 Cards";
        System.out.println("-----------------------------------------");
        System.out.println("GAME SETTINGS");
        System.out.printf("%-20s%s%n", "Game Type:", gameSettings.getGameType());
        System.out.printf("%-20s%s%n", "Draw Count:", drawCountDisplay);
        System.out.printf("%-20s%s%n", "Shuffle Type:", gameSettings.getShuffleType());
        System.out.printf("%-20s%s%n", "Card Visibility:", gameSettings.getCardVisibilitySetting());
        if (gameSettings.getGameCount() != 0) {
            System.out.printf("%-20s%d%s%n", "Game Count:", gameSettings.getGameCount(), " Games");
        }
    }

    /**
     * This prints the summary of the given Game Instance.
     *
     * @param gameInstance the given Game instance
     * @param isWin        the flag for the Game win state
     */
    public static void printSingleGameSummary(Game gameInstance, boolean isWin) {
        System.out.println("-----------------------------------------");
        System.out.println(isWin ? "GAME WON!" : "GAME LOST!");
        System.out.println("SUMMARY");
        System.out.println("Total Rounds: " + gameInstance.getRounds());
        System.out.print("FOUNDATION PILES: ");
        for (FoundationPile foundationPile : gameInstance.getFoundationPiles()) {
            System.out.printf("%-7s", foundationPile);
        }
        System.out.println();
    }

    /**
     * This prints the summary of a Multi Game run.
     *
     * @param gameCount the number of games
     * @param wins      the number of winning games
     * @param losses    the number of lost games
     * @param winRate   the computed win rate
     */
    public static void printMultiGameSummary(int gameCount, int wins,
                                             int losses, double winRate) {
        System.out.println("-----------------------------------------");
        System.out.println("RUN COMPLETED");
        System.out.println("SUMMARY");
        System.out.printf("%-20s%d%n", "Game Count:", gameCount);
        System.out.printf("%-20s%d%n", "Wins:", wins);
        System.out.printf("%-20s%d%n", "Losses:", losses);
        System.out.printf("%-20s%.2f%%%n", "Win Rate:", winRate);
    }

    /**
     * This prints the Game introduction
     */
    public static void printGameIntroduction() {
        System.out.println("""
                -----------------------------------------
                WELCOME TO KLONDIKE SOLITAIRE
                
                Types:
                    Single Game - Run one time
                    Multi Game  - Run multiple times and show win percentage (Only available in Random Shuffle)
                Glossary:
                    (M-M) Manoeuvre to Manoeuvre move
                    (M-F) Manoeuvre to Foundation move
                    (T-M) Talon Waste to Manoeuvre move
                    (T-F) Talon Waste to Foundation move""");
    }

    /**
     * This prints the usage guide for this program.
     */
    public static void printUsageGuide() {
        System.out.println("""
                Usage: java -jar harrydom-solitaire.jar <deck-file> [-visible | -hidden]
                
                A command-line solitaire game using a custom deck file.
                
                Arguments:
                  <deck-file>    Path to the deck file (required)
                
                Options:
                  -visible       Start the game with all Manoeuvre cards shown
                  -hidden        Start the game with face-down Manoeuvre cards hidden (default)
                
                Examples:
                  java -jar harrydom-solitaire.jar deck.txt
                  java -jar harrydom-solitaire.jar deck.txt -visible
                """);
    }
}
