package com.svi.solitaire.state;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.exceptions.InvalidDeckFileException;
import com.svi.solitaire.io.InputReader;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.utils.ArgsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GameManager {
    private final Scanner keyboardInput;
    private Game gameInstance;
    private Deck gameDeck;
    private boolean gameRunning;
    private int drawCount;
    private Shuffle shuffleType;
    private CardVisibility cardVisibilitySetting;
    private String[] gameArgs;

    public GameManager(String[] args) {
        this.gameArgs = args;
        keyboardInput = new Scanner(System.in);
        gameRunning = true;
    }

    public void run() {
        while (gameRunning) {
            initGameInstance();



            gameRunning = InputReader.readExitGameChoice(keyboardInput);
        }
    }

    private void initGameInstance() {
        InputStream inputStream;
        try {
            inputStream = ArgsUtil.createInputStream(gameArgs);
        } catch (IOException ioe) {
            throw new InvalidDeckFileException("Specified Deck file not found.");
        }
        drawCount = InputReader.readDrawCount(keyboardInput);
        shuffleType = InputReader.readShuffleType(keyboardInput);

        cardVisibilitySetting = ArgsUtil.parseCardVisibility(gameArgs);
        gameDeck = DeckFactory.createDeck(inputStream, shuffleType);
        gameInstance = GameFactory.createGame(gameDeck, drawCount, cardVisibilitySetting);
    }
}
