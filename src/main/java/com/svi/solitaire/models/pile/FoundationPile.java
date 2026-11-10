package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.Deque;

public class FoundationPile implements CardPile {
    private Deque<Card> cards;
    private Suit pileSuit;
    @Override
    public Card peek() {
        return null;
    }

    @Override
    public void appendCard(Card card) {

    }
}
