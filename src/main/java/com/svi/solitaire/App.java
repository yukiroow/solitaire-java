package com.svi.solitaire;

import com.svi.solitaire.state.GameManager;

/**
 * Class that serves as the primary entry point for the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class App {
    static void main(String[] args) {
        GameManager gameManager = new GameManager(args);
        gameManager.run();
    }
}
