package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class FoundationPile implements CardPile {
    private Deque<Card> cards;
    private Suit pileSuit;

    public FoundationPile() {
        this.cards = new ArrayDeque<>();
        pileSuit = null;
    }

    @Override
    public Card peek() {
        return cards.peek();
    }

    @Override
    public void appendCard(Card card) {
        if (pileSuit == null && cards.isEmpty()) {
            this.pileSuit = card.suit();
            cards.push(card);
            return;
        }

        if (!card.suit().equals(pileSuit)) {
            return;
        }

        Card topCard = cards.peek();
        if (card.isAppendableTo(topCard)) {
            cards.push(card);
        }
    }
}
