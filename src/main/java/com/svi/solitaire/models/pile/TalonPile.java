package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;

public class TalonPile implements CardPile {
    private final int drawSetting;
    private Deque<Card> stockPile;
    private Deque<Card> wastePile;

    public TalonPile(Deck deck, int drawSetting) {
        this.stockPile = new ArrayDeque<>(deck.getCards());
        this.wastePile = new ArrayDeque<>();
        this.drawSetting = drawSetting;
    }

    @Override
    public Card peek() {
        return null;
    }

    @Override
    public void appendCard(Card card) {
        stockPile.addLast(card);
    }

    public void resetPile() {
        for (Card card : wastePile) {
            stockPile.addLast(card);
        }
        wastePile.clear();
    }

    public void drawCards() {
        for (int count = 0; count < drawSetting; count++) {
            Card currCard = stockPile.poll();
            wastePile.addFirst(currCard);
        }
    }
}

