package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class ManoeuvrePile implements CardPile {
    private int pileId;
    private Deque<Card> hiddenCards;
    private Deque<Card> visibleCards;
    private CardVisibility cardVisibilitySetting;

    public ManoeuvrePile(int pileId, CardVisibility cardVisibilitySetting) {
        this.pileId = pileId;
        this.cardVisibilitySetting = cardVisibilitySetting;
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    public int getPileId() {
        return pileId;
    }

    public boolean isVisibleCardsEmpty() {
        return visibleCards.isEmpty();
    }

    public void revealCard() {
        if (hiddenCards.isEmpty()) {
            return;
        }
        Card newCard = hiddenCards.pollLast();
        visibleCards.add(newCard);
        System.out.println("PILE " + getPileId() + " CARD REVEALED");
    }

    public Iterator<Card> getVisibleCardsIterator() {
        return visibleCards.iterator();
    }

    public Iterator<Card> getHiddenCardsIterator() {
        return hiddenCards.iterator();
    }

    public int getHiddenCardsSize() {
        return hiddenCards.size();
    }

    public boolean isSetupComplete() {
        return hiddenCards.size() + visibleCards.size() == pileId;
    }

    @Override
    public Card peek() {
        return visibleCards.peekLast();
    }

    @Override
    public boolean appendCard(Card card) {
        return visibleCards.add(card);
    }

    public void appendCard(Card card, boolean visible) {
        if (visible) {
            visibleCards.add(card);
            return;
        }
        hiddenCards.add(card);
    }

    @Override
    public String toString() {
        return hiddenCards.reversed() + " | " + visibleCards.toString();
    }
}
