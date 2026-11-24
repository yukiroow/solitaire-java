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

public class GameManager {
    private Scanner keyboardInput;
    private boolean gameRunning;
    private GameSettings gameSettings;
    private String[] gameArgs;
    private InputStream deckInputStream;

    public GameManager(String[] args) {
        this.gameArgs = args;
        keyboardInput = new Scanner(System.in);
        gameRunning = true;
    }

    public void run() {
        ConsolePrinter.printGameIntroduction();

        while (gameRunning) {
            deckInputStream = ArgsUtil.createInputStream(gameArgs);
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

            do {
                moveManoeuvre(manoeuvrePiles, verbose);
                moveFoundation(manoeuvrePiles, foundationPiles, verbose);
            } while (moveTalon(talonPile, manoeuvrePiles, foundationPiles, verbose));

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
        double winRate = (double) wins / losses;
        ConsolePrinter.printMultiGameSummary(gameSettings.getGameCount(), wins, losses, winRate);
    }

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

    private void moveFoundation(ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles, boolean verbose) {
        for (ManoeuvrePile currManPile : manoeuvrePiles) {
            for (FoundationPile targetPile : foundationPiles) {
                MoveUtil.moveManoeuvreToFoundation(currManPile, targetPile, verbose);
            }
        }
    }

    private boolean moveTalon(TalonPile talonPile, ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles, boolean verbose) {
        boolean hasDrawnCards = false;
        boolean moveMade = false;
        if (talonPile.isStockEmpty()) {
            talonPile.resetPile();
        }

        if (talonPile.isWasteEmpty()) {
            talonPile.drawCards();
            hasDrawnCards = true;
        }

        if (!hasDrawnCards) {
            talonPile.drawCards();
        }

        for (ManoeuvrePile targetPile : manoeuvrePiles) {
            moveMade = MoveUtil.moveTalonToManoeuvre(talonPile, targetPile, verbose);
        }

        for (FoundationPile targetPile : foundationPiles) {
            MoveUtil.moveTalonToFoundation(talonPile, targetPile, verbose);
        }

        return moveMade;
    }

    private void initGameSettings() {
        int drawCount = InputReader.readDrawCount(keyboardInput);
        Shuffle shuffleType = InputReader.readShuffleType(keyboardInput);
        GameType gameType = GameType.SINGLE;
        CardVisibility cardVisibilitySetting = ArgsUtil.parseCardVisibility(gameArgs);

        if (shuffleType == Shuffle.RANDOM) {
            gameType = InputReader.readGameType(keyboardInput);
        }

        switch (gameType) {
            case SINGLE -> {
                gameSettings = new GameSettings(drawCount, shuffleType, cardVisibilitySetting, gameType);
            }
            case MULTI -> {
                int gameCount = InputReader.readGameCount(keyboardInput);
                gameSettings = new GameSettings(drawCount, shuffleType, cardVisibilitySetting, gameType, gameCount);
            }
        }

        ConsolePrinter.printGameSettings(gameSettings);
    }
}
