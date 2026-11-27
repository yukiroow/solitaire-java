package com.svi.solitaire.state;

import com.svi.solitaire.io.ConsolePrinter;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

/**
 * Utility class for starting Solitaire Game Instances.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class InstanceManager {
    /**
     * Start a Game Instance from the given Game object.
     *
     * @param gameInstance the given Game Object.
     * @param verbose      the print flag
     * @return {@code true} if the Game is won, {@code false} otherwise
     */
    public static boolean startSingleGameInstance(Game gameInstance, boolean verbose) {
        FoundationPile[] foundationPiles = gameInstance.getFoundationPiles();
        ManoeuvrePile[] manoeuvrePiles = gameInstance.getManoeuvrePiles();
        TalonPile talonPile = gameInstance.getTalonPile();
        boolean instanceRunning = true;
        boolean isWin = false;

        while (instanceRunning) {
            if (verbose) {
                ConsolePrinter.printDeckState(gameInstance);
            }

            MoveManager.moveManoeuvre(manoeuvrePiles, verbose);
            MoveManager.moveToFoundation(manoeuvrePiles, foundationPiles, verbose);
            MoveManager.moveTalon(talonPile, manoeuvrePiles, foundationPiles, verbose);

            gameInstance.recordStateEntry();

            if (gameInstance.isGameWon()) {
                instanceRunning = false;
                isWin = true;
            }
            if (gameInstance.isGameOver()) {
                instanceRunning = false;
            }
            gameInstance.nextRound();
        }

        if (verbose) {
            ConsolePrinter.printSingleGameSummary(gameInstance, isWin);
        }

        return isWin;
    }

    /**
     * Start a Multi Game instance with the given Deck object.
     *
     * <p>
     * A Multi Game instance runs multiple games and computes the win rate of every Game ran.
     * </p>
     *
     * @param gameDeck the given Deck object
     * @param gameSettings the Game configuration
     */
    public static void startMultiGameInstance(Deck gameDeck, GameSettings gameSettings) {
        int wins = 0;
        int losses = 0;

        for (int gameIndex = 0; gameIndex < gameSettings.getGameCount(); gameIndex++) {
            Game currGameInstance = GameFactory.createGame(gameDeck, gameSettings);
            boolean currGameWin = startSingleGameInstance(currGameInstance, false);
            if (currGameWin) {
                wins++;
            } else {
                losses++;
            }
            gameDeck.shuffle();
        }

        double winRate = ((double) wins / gameSettings.getGameCount()) * 100;
        ConsolePrinter.printMultiGameSummary(gameSettings.getGameCount(), wins, losses, winRate);
    }
}
