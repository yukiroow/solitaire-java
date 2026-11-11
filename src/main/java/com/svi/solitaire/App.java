package com.svi.solitaire;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.utils.FileUtil;
import com.svi.solitaire.utils.InputUtil;

import java.util.Scanner;

public class App {
    static void main(String[] args) {
//        if(args.length != 1) {
//            System.out.println("Usage: java -jar harrydom-solitaire <deck file>");
//            System.exit(1);
//        }
        Scanner keyboardInput;
        try {
            keyboardInput = new Scanner(System.in);
            while (true) {
                Deck deck = FileUtil.loadDeckFromFile("src/main/resources/deck.csv");
                int shuffleType = InputUtil.readSettingInput("1. Random Shuffle\n2. Out-Faro Shuffle", keyboardInput);
                int drawType = InputUtil.readSettingInput("1. Draw One\n2. Draw Three", keyboardInput);

//                Game game = new Game(deck);

                break;
            }
        } catch (Exception e) {
            System.out.println("There was an unexpected problem." + e);
        }
    }
}
