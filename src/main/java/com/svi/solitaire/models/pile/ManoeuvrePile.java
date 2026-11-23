package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class ManoeuvrePile implements CardPile {
    private int stackId;
    private Deque<Card> hiddenCards;
    private Deque<Card> visibleCards;

    public ManoeuvrePile(int stackId) {
        this.stackId = stackId;
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    public int getStackId() {
        return stackId;
    }

    public boolean isVisibleCardsEmpty() {
        return visibleCards.isEmpty();
    }

    public void revealCard() {
        Card newCard = hiddenCards.pollLast();
        visibleCards.push(newCard);
    }

    public Iterator<Card> getVisibleCardsIterator() {
        return visibleCards.iterator();
    }

    public int getHiddenCardsSize() {
        return hiddenCards.size();
    }

    public boolean isSetupComplete() {
        return hiddenCards.size() + visibleCards.size() == stackId;
    }

    @Override
    public Card peek() {
        return visibleCards.peekLast();
    }

    @Override
    public void appendCard(Card card) {
        visibleCards.add(card);
    }

    public void appendCard(Card card, boolean visible) {
        if (visible) {
            visibleCards.add(card);
            return;
        }
        hiddenCards.push(card);
    }

    @Override
    public String toString() {
        return hiddenCards.reversed() + " | " + visibleCards.toString();
    }
}
