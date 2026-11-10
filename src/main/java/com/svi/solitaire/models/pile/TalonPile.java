package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;

public class TalonPile implements CardPile {
    private Deque<Card> talonPile;
    private Deque<Card> wastePile;

    public TalonPile(Deck deck) {
        talonPile = new ArrayDeque<>(deck.cards());
    }

    @Override
    public Card peek() {
        return null;
    }

    @Override
    public void appendCard(Card card) {

    }
}

