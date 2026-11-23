package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.MoveUtil;

import java.util.Iterator;

public class Game {
    private FoundationPile[] foundationPiles;
    private ManoeuvrePile[] manoeuvrePiles;
    private TalonPile talonPile;
    private int rounds;

    public Game(FoundationPile[] foundationStacks, ManoeuvrePile[] manoeuvreStacks, TalonPile talonStack) {
        this.foundationPiles = foundationStacks;
        this.manoeuvrePiles = manoeuvreStacks;
        this.talonPile = talonStack;
        this.rounds = 1;
    }

    public ManoeuvrePile[] getManoeuvrePiles() {
        return manoeuvrePiles;
    }

    public FoundationPile[] getFoundationPiles() {
        return foundationPiles;
    }

    public TalonPile getTalonPile() {
        return talonPile;
    }

    public int getRounds() {
        return rounds;
    }

    public void run() {
        boolean instanceRunning = true;
        while (instanceRunning) {
            printRoundState();

            if (rounds == 1000) System.exit(0);

            moveManoeuvre();
            moveFoundation();
            moveTalon();

            if (gameWon()) {
                instanceRunning = false;
            }
            rounds++;
        }
        printRoundState();
    }

    public void moveManoeuvre() {
        for (int currManPileIndex = 0; currManPileIndex < this.manoeuvrePiles.length; currManPileIndex++) {
            ManoeuvrePile currManPile = this.manoeuvrePiles[currManPileIndex];
            for (int targetManPileIndex = 0; targetManPileIndex < this.manoeuvrePiles.length; targetManPileIndex++) {
                if (currManPileIndex == targetManPileIndex) {
                    continue;
                }
                ManoeuvrePile targetManPile = this.manoeuvrePiles[targetManPileIndex];
                MoveUtil.moveManoeuvreToManoeuvre(currManPile, targetManPile);
            }
        }
    }

    public void moveFoundation() {
        for (ManoeuvrePile currManPile : this.manoeuvrePiles) {
            for (FoundationPile targetFounPile : this.foundationPiles) {
                MoveUtil.moveManoeuvreToFoundation(currManPile, targetFounPile);
            }
        }
    }

    public void moveTalon() {
        boolean hasDrawnCards = false;
        if (talonPile.isStockEmpty()) {
            talonPile.resetPile();
        }

        if (talonPile.isWasteEmpty()) {
            talonPile.drawCards();
            hasDrawnCards = true;
        }

        for (ManoeuvrePile targetManPile : this.manoeuvrePiles) {
            MoveUtil.moveTalonToManoeuvre(talonPile, targetManPile);
        }

        for (FoundationPile targetFounPile : this.foundationPiles) {
            MoveUtil.moveTalonToFoundation(talonPile, targetFounPile);
        }

        if (!hasDrawnCards) {
            talonPile.drawCards();
        }
    }

    public boolean gameWon() {
        int score = 0;
        for (FoundationPile foundationPile : this.foundationPiles) {
            Card topCard = foundationPile.peek();
            if (topCard == null) {
                return false;
            }
            if (topCard.rank() == Rank.K) {
                score++;
            }
        }
        return score == 4;
    }

    public void printRoundState() {
        System.out.println("-----------------------------------------");
        System.out.println("ROUND " + rounds);

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
