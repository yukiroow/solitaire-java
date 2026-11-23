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
import java.util.Scanner;

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
        while (instanceRunning) {
            ConsolePrinter.printDeckState(gameInstance);

            System.out.println("""
                    -----------------------------------------
                    MOVES""");
            moveManoeuvre(manoeuvrePiles);
            moveFoundation(manoeuvrePiles, foundationPiles);
            moveTalon(talonPile, manoeuvrePiles, foundationPiles);

            if (gameInstance.isGameWon()) {
                instanceRunning = false;
            }
            gameInstance.nextRound();
        }
        ConsolePrinter.printGameSummary(gameInstance);
    }

    private void moveManoeuvre(ManoeuvrePile[] manoeuvrePiles) {
        for (int currManPileIndex = 0; currManPileIndex < manoeuvrePiles.length; currManPileIndex++) {
            ManoeuvrePile currManPile = manoeuvrePiles[currManPileIndex];
            for (int targetManPileIndex = 0; targetManPileIndex < manoeuvrePiles.length; targetManPileIndex++) {
                if (currManPileIndex == targetManPileIndex) {
                    continue;
                }
                ManoeuvrePile targetManPile = manoeuvrePiles[targetManPileIndex];
                MoveUtil.moveManoeuvreToManoeuvre(currManPile, targetManPile);
            }
        }
    }

    private void moveFoundation(ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles) {
        for (ManoeuvrePile currManPile : manoeuvrePiles) {
            for (FoundationPile targetFounPile : foundationPiles) {
                MoveUtil.moveManoeuvreToFoundation(currManPile, targetFounPile);
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

        for (ManoeuvrePile targetManPile : manoeuvrePiles) {
            MoveUtil.moveTalonToManoeuvre(talonPile, targetManPile);
        }

        for (FoundationPile targetFounPile : foundationPiles) {
            MoveUtil.moveTalonToFoundation(talonPile, targetFounPile);
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
