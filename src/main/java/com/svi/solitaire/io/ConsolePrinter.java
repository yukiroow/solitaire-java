package com.svi.solitaire.io;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.Iterator;

public class ConsolePrinter {
    public static void printDeckState(int round, TalonPile talonPile, ManoeuvrePile[] manoeuvrePiles, FoundationPile[] foundationPiles) {
        System.out.println("-----------------------------------------");
        System.out.println("ROUND " + round);

        System.out.println("STOCK: " + talonPile.getStockPileSize() + " Cards");

        System.out.print("WASTE: ");
        Iterator<Card> wastePileIterator = talonPile.getWastePileIterator();
        if (!wastePileIterator.hasNext()) {
            System.out.print("[ - ]");
        } else {
            while (wastePileIterator.hasNext()) {
                Card currCard = wastePileIterator.next();
                System.out.printf("%-6s", "[" + currCard + "]");
            }
        }

        System.out.println("\nFOUNDATION PILES");
        for (FoundationPile foundationPile : foundationPiles) {
            Card topCard = foundationPile.peek();
            String cardDisplay = topCard == null ? "[  -  ]" : ("[" + topCard + "]");
            System.out.printf("%-7s", cardDisplay);
        }

        System.out.println("\nMANOEUVRE PILES");
        for (ManoeuvrePile manoeuvrePile : manoeuvrePiles) {
            int hiddenCardsSize = manoeuvrePile.getHiddenCardsSize();
            Iterator<Card> visiblePileIterator = manoeuvrePile.getVisibleCardsIterator();

            System.out.print("[" + (manoeuvrePile.getPileId()) + "] : ");

            for (int cardIndex = 0; cardIndex < hiddenCardsSize; cardIndex++) {
                System.out.print("[ ? ]");
            }

            Iterator<Card> hdc = manoeuvrePile.getHiddenCardsIterator();
            while (hdc.hasNext()) {
                Card currCard = hdc.next();
                System.out.printf("%-7s", "(" + currCard + ")");
            }

            System.out.print(" | ");

            while (visiblePileIterator.hasNext()) {
                Card currCard = visiblePileIterator.next();
                System.out.printf("%-7s", "[" + currCard + "]");
            }

            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }
}
