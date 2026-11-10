package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputUtil {
    public static int readSettingInput(String prompt, Scanner keyboardInput) {
        int choice;
        while (true) {
            System.out.print(prompt + "\n> ");
            try {
                choice = Integer.parseInt(keyboardInput.nextLine().trim());
                if (choice < 1 || choice > 2) {
                    System.out.println("Please select a valid option.");
                    continue;
                }
                return choice;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid value.");
            }
        }
    }

    public static Deck loadDeck(InputStream deckStream) {
        Scanner fileScanner;
        ArrayList<Card> deck = new ArrayList<>();
        try {
            fileScanner = new Scanner(deckStream);
            String[] rawCards = fileScanner.nextLine().split(",");
            for (String card : rawCards) {
                String[] splitCard = card.split("-");
                String strSuit = splitCard[0];
                String strRank = splitCard[1];
                deck.add(new Card(Suit.valueOf(strSuit), Rank.valueOf(strRank)));
            }
        } catch (NoSuchElementException nsee) {
            System.out.println("There was a problem with the input file." + nsee);
        }
        return new Deck(deck);
    }
}
