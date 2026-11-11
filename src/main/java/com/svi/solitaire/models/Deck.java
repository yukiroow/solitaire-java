package com.svi.solitaire.models;

import java.util.ArrayList;
import java.util.List;

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
}
