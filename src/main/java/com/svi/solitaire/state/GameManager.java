package com.svi.solitaire.state;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.io.ConsolePrinter;
import com.svi.solitaire.io.InputReader;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.ArgsUtil;
import com.svi.solitaire.utils.MoveUtil;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents the State Manager for the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class GameManager {
    private Scanner keyboardInput;
    private boolean gameRunning;
    private GameSettings gameSettings;
    private String[] gameArgs;
    private CardVisibility cardVisibilitySetting;

    /**
     * Construct a new Game Manager with the given program arguments.
     *
     * @param args the given program arguments
     */
    public GameManager(String[] args) {
        this.gameArgs = args;
        keyboardInput = new Scanner(System.in);
        gameRunning = true;
    }

    /**
     * Start the Manager Runtime.
     *
     * <p>
     * The main program loop is hosted here.
     * </p>
     */
    public void run() {
        while (gameRunning) {
            cardVisibilitySetting = ArgsUtil.parseCardVisibility(gameArgs);
            InputStream deckInputStream = ArgsUtil.createInputStream(gameArgs);
            ConsolePrinter.printGameIntroduction();
            initGameSettings();
            Deck gameDeck = DeckFactory.createDeck(deckInputStream, gameSettings);

            switch (gameSettings.getGameType()) {
                case SINGLE -> {
                    Game singleGameInstance = GameFactory.createGame(gameDeck, gameSettings);
                    startSingleGameInstance(singleGameInstance, true);
                }
                case MULTI -> startMultiGameInstance(gameDeck);
            }

            gameRunning = InputReader.readExitGameChoice(keyboardInput);
        }
    }

    /**
     * Start a Game Instance from the given Game object.
     *
     * @param gameInstance the given Game Object.
     * @param verbose      the print flag
     * @return {@code true} if the Game is won, {@code false} otherwise
     */
    private boolean startSingleGameInstance(Game gameInstance, boolean verbose) {
        FoundationPile[] foundationPiles = gameInstance.getFoundationPiles();
        ManoeuvrePile[] manoeuvrePiles = gameInstance.getManoeuvrePiles();
        TalonPile talonPile = gameInstance.getTalonPile();
        boolean instanceRunning = true;
        boolean isWin = false;

        while (instanceRunning) {
            if (verbose) {
                ConsolePrinter.printDeckState(gameInstance);
            }

            moveManoeuvre(manoeuvrePiles, verbose);
            moveToFoundation(manoeuvrePiles, foundationPiles, verbose);
            moveTalon(talonPile, manoeuvrePiles, foundationPiles, verbose);

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
     */
    private void startMultiGameInstance(Deck gameDeck) {
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

    /**
     * Execute a move within the given array of Manoeuvre Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param manoeuvrePiles the given Manoeuvre Piles
     * @param verbose        the print flag
     */
    private void moveManoeuvre(ManoeuvrePile[] manoeuvrePiles, boolean verbose) {
        Set<String> moves = new HashSet<>();

        for (int currManPileIndex = 0; currManPileIndex < manoeuvrePiles.length; currManPileIndex++) {
            ManoeuvrePile currManPile = manoeuvrePiles[currManPileIndex];

            for (int targetManPileIndex = 0; targetManPileIndex < manoeuvrePiles.length; targetManPileIndex++) {
                ManoeuvrePile targetManPile = manoeuvrePiles[targetManPileIndex];
                String moveEntry = Math.min(currManPileIndex, targetManPileIndex) + "->" + Math.max(currManPileIndex, targetManPileIndex);

                if (currManPileIndex == targetManPileIndex) {
                    continue;
                }
                if (moves.contains(moveEntry)) {
                    continue;
                }

                boolean moveMade = MoveUtil.moveManoeuvreToManoeuvre(currManPile, targetManPile, verbose);
                if (moveMade) {
                    moves.add(moveEntry);
                }
            }
        }
    }

    /**
     * Execute a move from the given array of Manoeuvre Piles to the given array of Foundation Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param manoeuvrePiles  the given Manoeuvre Piles
     * @param foundationPiles the given Foundation Piles
     * @param verbose         the print flag
     */
    private void moveToFoundation(ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles, boolean verbose) {
        for (ManoeuvrePile currManPile : manoeuvrePiles) {
            for (FoundationPile targetPile : foundationPiles) {
                MoveUtil.moveManoeuvreToFoundation(currManPile, targetPile, verbose);
            }
        }
    }

    /**
     * Execute a move from the given Talon Pile to the given array of Manoeuvre Piles and Foundation Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param talonPile       the given Talon Pile
     * @param manoeuvrePiles  the given Manoeuvre Pile
     * @param foundationPiles the given Foundation Pile
     * @param verbose         the print flag
     */
    private void moveTalon(TalonPile talonPile, ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles, boolean verbose) {
        boolean hasDrawnCards = false;

        if (talonPile.isStockEmpty()) {
            talonPile.resetPile();
        }

        if (talonPile.isWasteEmpty()) {
            talonPile.drawCards(verbose);
            hasDrawnCards = true;
        }

        if (!hasDrawnCards) {
            talonPile.drawCards(verbose);
        }

        for (ManoeuvrePile targetPile : manoeuvrePiles) {
            MoveUtil.moveTalonToManoeuvre(talonPile, targetPile, verbose);
        }

        for (FoundationPile targetPile : foundationPiles) {
            MoveUtil.moveTalonToFoundation(talonPile, targetPile, verbose);
        }
    }

    /**
     * Initialize all the necessary fields for the Game Settings.
     */
    private void initGameSettings() {
        int drawCount = InputReader.readDrawCount(keyboardInput);
        Shuffle shuffleType = InputReader.readShuffleType(keyboardInput);
        GameType gameType = GameType.SINGLE;
        int numFaroShuffle = 0;

        switch (shuffleType) {
            case RANDOM -> gameType = InputReader.readGameType(keyboardInput);
            case OUT_FARO, IN_FARO -> numFaroShuffle = InputReader.readNumFaroShuffle(keyboardInput, shuffleType);
        }

        switch (gameType) {
            case SINGLE -> gameSettings = new GameSettings(drawCount, numFaroShuffle, shuffleType, cardVisibilitySetting, gameType);
            case MULTI -> {
                int gameCount = InputReader.readGameCount(keyboardInput);
                gameSettings = new GameSettings(drawCount, shuffleType, cardVisibilitySetting, gameType, gameCount);
            }
        }

        ConsolePrinter.printGameSettings(gameSettings);
    }
}
