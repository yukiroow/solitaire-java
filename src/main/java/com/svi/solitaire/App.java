package com.svi.solitaire;

import com.svi.solitaire.models.Game;

import java.util.Scanner;

public class App {
    static void main() {
        Scanner keyboardInput;
        System.out.println("Welcome to Solitaire!\n");
        try {
            keyboardInput = new Scanner(System.in);
            while (true) {
//                System.out.println("GAME CONFIGURATION");
//                int shuffleType = InputUtil.readSettingInput("1. Random Shuffle\n2. Faro Shuffle", keyboardInput);
//                int drawType = InputUtil.readSettingInput("1. Draw One\n2. Draw Three", keyboardInput);
//                Deck deck = InputUtil.loadDeck(App.class.getResourceAsStream("/deck.csv"));

                Game game = new Game();

                break;
            }
        } catch (Exception e) {
            System.out.println("There was an unexpected problem." + e);
        }
    }
}
