package com.svi.solitaire.models.stack;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class ManoeuvreStack implements CardStack {
    private int stackId;
    private Deque<Card> hiddenCards;
    private Deque<Card> visibleCards;

    public ManoeuvreStack(int stackId) {
        this.stackId = stackId;
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    public int getStackId() {
        return stackId;
    }

    public int getHiddenCardsSize() {
        return hiddenCards.size();
    }

    public boolean isSetupComplete() {
        return hiddenCards.size() + visibleCards.size() == stackId;
    }

    @Override
    public Card peek() {
        return visibleCards.peek();
    }

    @Override
    public void appendCard(Card card) {
        visibleCards.push(card);
    }

    public void appendCard(Card card, boolean visible) {
        if (visible) {
            visibleCards.push(card);
            return;
        }
        hiddenCards.push(card);
    }

    @Override
    public String toString() {
        return hiddenCards.reversed() + " | " + visibleCards.toString();
    }
}
