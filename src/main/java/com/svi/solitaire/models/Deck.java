package com.svi.solitaire.models;

import java.util.List;

public record Deck(List<Card> cards) {
    public Deck(Deck deck) {
        this(deck.cards());
    }
}
