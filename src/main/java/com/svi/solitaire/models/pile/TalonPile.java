package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;

public class TalonPile implements CardPile {
    private final int drawSetting;
    private Deque<Card> talonPile;
    private Deque<Card> wastePile;

    public TalonPile(Deck deck, int drawSetting) {
        talonPile = new ArrayDeque<>(deck.getCards());
        this.drawSetting = drawSetting;
    }

    @Override
    public Card peek() {
        return null;
    }

    @Override
    public void appendCard(Card card) {
        talonPile.addLast(card);
    }

    public void resetPile() {
        for (Card card : wastePile) {
            talonPile.addLast(card);
        }
        wastePile.clear();
    }

    public void drawCards() {
        for (int count = 0; count < drawSetting; count++) {
            Card currCard = talonPile.poll();
            wastePile.addFirst(currCard);
        }
    }
}

