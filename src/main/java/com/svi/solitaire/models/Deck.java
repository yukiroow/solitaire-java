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

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public Deck(Deck otherDeck) {
        this.cards = new ArrayList<>(otherDeck.cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
