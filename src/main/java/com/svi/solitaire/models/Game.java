package com.svi.solitaire.models;

import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.Iterator;

public class Game {
    private Deck deck;
    private FoundationPile[] foundationPiles;
    private ManoeuvrePile[] manoeuvrePiles;
    private TalonPile talonPile;
    private int rounds;

    public Game(Deck deck, FoundationPile[] foundationStacks, ManoeuvrePile[] manoeuvreStacks, TalonPile talonStack) {
        this.deck = deck;
        this.foundationPiles = foundationStacks;
        this.manoeuvrePiles = manoeuvreStacks;
        this.talonPile = talonStack;
        this.rounds = 1;
        printRoundState();
    }

    // Reveals hidden card - 3 pts, Move card - 1 pt, Move to foundation - 2 pts
    public void run() {
        while (true) {
            printRoundState();

            int highestScore = 0;


        }
    }

    public void printRoundState() {
        System.out.println("-----------------------------------------");
        System.out.println("ROUND " + rounds);
        System.out.println("STOCK: " + talonPile.getStockPileSize() + " Cards");
        System.out.println("WASTE: " + talonPile.getWastePile());

        System.out.println("FOUNDATION PILES");
        for (FoundationPile foundationPile : foundationPiles) {
            Card topCard = foundationPile.peek();
            String cardDisplay = topCard == null ? "[  -  ]" : ("[" + topCard + "]");
            System.out.printf("%-6s", cardDisplay);
        }

        System.out.println("\nMANOEUVRE PILES");
        for (ManoeuvrePile manoeuvrePile : manoeuvrePiles) {
            int hiddenCardsSize = manoeuvrePile.getHiddenCardsSize();
            Iterator<Card> visiblePileIterator = manoeuvrePile.getVisibleCardsIterator();

            System.out.print("[" + (manoeuvrePile.getStackId()) + "] : ");

            for (int cardIndex = 0; cardIndex < hiddenCardsSize; cardIndex++) {
                System.out.print("[ ? ]");
            }

            while (visiblePileIterator.hasNext()) {
                Card currCard = visiblePileIterator.next();
                System.out.printf("%-6s", "[" + currCard + "]");
            }

            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }


}
