package com.svi.solitaire;

import com.svi.solitaire.exceptions.InvalidDeckFileException;
import com.svi.solitaire.state.GameManager;

/**
 * Class that serves as the primary entry point for the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class App {
    /**
     * Starts the application.
     *
     * @param args the program arguments
     */
    static void main(String[] args) {
        try {
            GameManager gameManager = new GameManager(args);
            gameManager.run();
        } catch (InvalidDeckFileException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
