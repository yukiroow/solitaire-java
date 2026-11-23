package com.svi.solitaire;

import com.svi.solitaire.state.GameManager;

public class App {
    static void main(String[] args) {
        GameManager gameManager = new GameManager(args);
        gameManager.run();
    }
}
