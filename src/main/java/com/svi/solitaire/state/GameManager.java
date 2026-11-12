package com.svi.solitaire.state;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.utils.InputUtil;

import java.util.Scanner;

public class GameManager {
    private Game gameInstance;
    private Deck gameDeck;
    private final Scanner keyboardInput;
    private boolean gameRunning;
    private int drawCount;
    private Shuffle shuffleType;

    public GameManager() {
        keyboardInput = new Scanner(System.in);
        gameRunning = false;
    }

    public void run(String[] args) {
        while (gameRunning) {
            readGameSettings();
            gameDeck = DeckFactory.createDeck(args, shuffleType);
            gameInstance = GameFactory.createGame(gameDeck, drawCount);
        }
    }

    private void readGameSettings() {
        drawCount = InputUtil.readDrawCount(keyboardInput);
        shuffleType = InputUtil.readShuffleType(keyboardInput);
    }
}
