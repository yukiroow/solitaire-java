package com.svi.solitaire.state;

import com.svi.solitaire.enums.CardVisibility;
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

public class GameManager {
    private final Scanner keyboardInput;
    private Game gameInstance;
    private boolean gameRunning;
    private GameSettings gameSettings;
    private String[] gameArgs;

    public GameManager(String[] args) {
        this.gameArgs = args;
        keyboardInput = new Scanner(System.in);
        gameRunning = true;
    }

    public void run() {
        ConsolePrinter.printGameIntroduction();

        while (gameRunning) {
            initGameSettings();
            initGameInstance();
            startGameInstance();
            gameRunning = InputReader.readExitGameChoice(keyboardInput);
        }
    }

    private void startGameInstance() {
        FoundationPile[] foundationPiles = gameInstance.getFoundationPiles();
        ManoeuvrePile[] manoeuvrePiles = gameInstance.getManoeuvrePiles();
        TalonPile talonPile = gameInstance.getTalonPile();
        boolean instanceRunning = true;
        boolean isWin = false;

        while (instanceRunning) {
            ConsolePrinter.printDeckState(gameInstance);

            moveManoeuvre(manoeuvrePiles);
            moveFoundation(manoeuvrePiles, foundationPiles);
            moveTalon(talonPile, manoeuvrePiles, foundationPiles);

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

        ConsolePrinter.printGameSummary(gameInstance, isWin);
    }

    private void moveManoeuvre(ManoeuvrePile[] manoeuvrePiles) {
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

                boolean moveMade = MoveUtil.moveManoeuvreToManoeuvre(currManPile, targetManPile);
                if (moveMade) {
                    moves.add(moveEntry);
                }
            }
        }
    }

    private void moveFoundation(ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles) {
        for (ManoeuvrePile currManPile : manoeuvrePiles) {
            for (FoundationPile targetPile : foundationPiles) {
                MoveUtil.moveManoeuvreToFoundation(currManPile, targetPile);
            }
        }
    }

    private void moveTalon(TalonPile talonPile, ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles) {
        boolean hasDrawnCards = false;
        if (talonPile.isStockEmpty()) {
            talonPile.resetPile();
        }

        if (talonPile.isWasteEmpty()) {
            talonPile.drawCards();
            hasDrawnCards = true;
        }

        for (ManoeuvrePile targetPile : manoeuvrePiles) {
            MoveUtil.moveTalonToManoeuvre(talonPile, targetPile);
        }

        for (FoundationPile targetPile : foundationPiles) {
            MoveUtil.moveTalonToFoundation(talonPile, targetPile);
        }

        if (!hasDrawnCards) {
            talonPile.drawCards();
        }
    }

    private void initGameSettings() {
        int drawCount = InputReader.readDrawCount(keyboardInput);
        Shuffle shuffleType = InputReader.readShuffleType(keyboardInput);
        CardVisibility cardVisibilitySetting = ArgsUtil.parseCardVisibility(gameArgs);
        gameSettings = new GameSettings(drawCount, shuffleType, cardVisibilitySetting);

        ConsolePrinter.printGameSettings(gameSettings);
    }

    private void initGameInstance() {
        InputStream inputStream = ArgsUtil.createInputStream(gameArgs);
        Deck gameDeck = DeckFactory.createDeck(inputStream, gameSettings);
        gameInstance = GameFactory.createGame(gameDeck, gameSettings);
    }
}
