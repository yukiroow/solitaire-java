package com.svi.solitaire.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Card Deck in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Construct a Deck object from the given List of Cards
     *
     * @param cards the given List of Cards
     */
    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Construct a Deck object from another given Deck object
     *
     * @param otherDeck the other Deck object
     */
    public Deck(Deck otherDeck) {
        this.cards = new ArrayList<>(otherDeck.cards);
    }

    /**
     * This returns the Cards of this Deck
     *
     * @return the Cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * This uses the {@code Collections} library to shuffle the Cards of this Deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }
}
