package com.svi.solitaire.models;

import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

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
    }

    public FoundationPile[] getFoundationPiles() {
        return foundationPiles;
    }

    public ManoeuvrePile[] getManoeuvrePiles() {
        return manoeuvrePiles;
    }

    public TalonPile getTalonPile() {
        return talonPile;
    }

    public void printGameDeck() {
        System.out.printf("< ROUND %d >%n", rounds);
        System.out.printf("%-15s%10d cards","STOCK:", talonPile.getWastePileSize());
        System.out.printf("%-15s[ %s ][ %s ][ %s ][ %s ]","FOUNDATIONS:", foundationPiles[0].peek(), foundationPiles[1].peek(), foundationPiles[2].peek(), foundationPiles[3].peek());
        System.out.printf("%-15s","MANOEUVRES");
    }

    public void printTableState() {
        System.out.printf("");
    }

    public void nextRound() {
        rounds++;
    }
}
