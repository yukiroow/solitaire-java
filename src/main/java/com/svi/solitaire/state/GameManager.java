package com.svi.solitaire.state;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.io.ConsolePrinter;
import com.svi.solitaire.io.InputReader;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.utils.ArgsUtil;
import com.svi.solitaire.utils.DeckUtil;

import java.io.InputStream;
import java.util.Scanner;

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
        InputStream deckInputStream = ArgsUtil.createInputStream(gameArgs);
        if(!DeckUtil.validateDeckFile(deckInputStream)) {
            throw new RuntimeException("");
        }

        while (gameRunning) {
            deckInputStream = ArgsUtil.createInputStream(gameArgs);
            cardVisibilitySetting = ArgsUtil.parseCardVisibility(gameArgs);

            ConsolePrinter.printGameIntroduction();
            initGameSettings();
            Deck gameDeck = DeckFactory.createDeck(deckInputStream, gameSettings);
            ConsolePrinter.printGameSettings(gameSettings);

            switch (gameSettings.getGameType()) {
                case SINGLE -> {
                    Game singleGameInstance = GameFactory.createGame(gameDeck, gameSettings);
                    InstanceManager.startSingleGameInstance(singleGameInstance, true);
                }
                case MULTI -> InstanceManager.startMultiGameInstance(gameDeck, gameSettings);
            }

            gameRunning = InputReader.readExitGameChoice(keyboardInput);
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
            case OUT_FARO, IN_FARO -> numFaroShuffle =
                    InputReader.readNumFaroShuffle(keyboardInput, shuffleType);
        }

        switch (gameType) {
            case SINGLE ->
                    gameSettings =
                            new GameSettings(drawCount, numFaroShuffle, shuffleType, cardVisibilitySetting, gameType);
            case MULTI -> {
                int gameCount = InputReader.readGameCount(keyboardInput);
                gameSettings =
                        new GameSettings(drawCount, shuffleType, cardVisibilitySetting, gameType, gameCount);
            }
        }
    }
}
