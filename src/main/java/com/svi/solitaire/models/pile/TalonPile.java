package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

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
        return wastePile.peek();
    }

    @Override
    public boolean appendCard(Card card) {
        return stockPile.add(card);
    }

    public Card playTopWaste() {
        return wastePile.poll();
    }

    public boolean isStockEmpty() {
        return stockPile.isEmpty();
    }

    public boolean isWasteEmpty() {
        return wastePile.isEmpty();
    }

    public Iterator<Card> getWastePileIterator() {
        return wastePile.iterator();
    }

    public int getStockPileSize() {
        return stockPile.size();
    }

    public void resetPile() {
        for (Card card : wastePile) {
            appendCard(card);
        }
        wastePile.clear();
    }

    public void drawCards() {
        for (int count = 0; count < drawSetting; count++) {
            Card currCard = stockPile.poll();
            if (currCard != null) {
                wastePile.push(currCard);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Card> wastePileIterator = wastePile.descendingIterator();
        if (!wastePileIterator.hasNext()) {
            sb.append("[ - ]");
        } else {
            while (wastePileIterator.hasNext()) {
                Card currCard = wastePileIterator.next();
                sb.append("[").append(currCard).append("] ");
            }
        }
        return sb.toString();
    }
}

