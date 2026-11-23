package com.svi.solitaire.state;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.io.InputReader;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;

import java.util.Scanner;

public class GameManager {
    private final Scanner keyboardInput;
    private Game gameInstance;
    private Deck gameDeck;
    private boolean gameRunning;
    private int drawCount;
    private Shuffle shuffleType;

    public GameManager() {
        keyboardInput = new Scanner(System.in);
        gameRunning = true;
    }

    public void run(String[] args) {
        while (gameRunning) {
            readGameSettings();
            gameDeck = DeckFactory.createDeck(args, shuffleType);
            gameInstance = GameFactory.createGame(gameDeck, drawCount);
            gameInstance.run();
            gameRunning = InputReader.readExitGameChoice(keyboardInput);
        }
    }

    private void readGameSettings() {
        drawCount = InputReader.readDrawCount(keyboardInput);
        shuffleType = InputReader.readShuffleType(keyboardInput);
    }
}
