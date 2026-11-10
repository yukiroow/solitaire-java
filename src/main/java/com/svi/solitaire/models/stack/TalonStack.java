package com.svi.solitaire.models.stack;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;

public class TalonStack implements CardStack {
    private Deque<Card> cards;

    public TalonStack(Deck deck) {
        cards = new ArrayDeque<>(deck.cards());
    }

    @Override
    public Card peek() {
        return null;
    }

    @Override
    public void appendCard(Card card) {

    }
}

