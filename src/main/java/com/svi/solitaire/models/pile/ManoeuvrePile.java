package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Represents the Manoeuvre Pile in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class ManoeuvrePile implements CardPile {
    private final int pileId;
    private final Deque<Card> hiddenCards;
    private final Deque<Card> visibleCards;
    private final CardVisibility cardVisibilitySetting;

    /**
     * Construct a Manoeuvre Pile from the given ID number and Card Visibility setting
     *
     * @param pileId                the given ID number
     * @param cardVisibilitySetting the given Card Visibility setting
     */
    public ManoeuvrePile(int pileId, CardVisibility cardVisibilitySetting) {
        this.pileId = pileId;
        this.cardVisibilitySetting = cardVisibilitySetting;
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    /**
     * This returns the ID of this Manoeuvre Pile
     *
     * @return the ID number
     */
    public int getPileId() {
        return pileId;
    }

    /**
     * Determines if the Visible Cards in this Manoeuvre Pile is empty
     *
     * @return {@code true} if the Visible Cards is empty, {@code false} otherwise
     */
    public boolean isVisibleCardsEmpty() {
        return visibleCards.isEmpty();
    }

    /**
     * Removes the tail card from the Hidden Cards and adds it the Visible Cards
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param verbose the print flag
     */
    public void revealCard(boolean verbose) {
        if (hiddenCards.isEmpty()) {
            return;
        }

        Card newCard = hiddenCards.pollLast();
        visibleCards.add(newCard);

        if (verbose) {
            System.out.println("PILE " + getPileId() + " CARD REVEALED");
        }
    }

    /**
     * This returns the Iterator of this Manoeuvre Pile's Visible Cards
     *
     * @return the iterator
     */
    public Iterator<Card> getVisibleCardsIterator() {
        return visibleCards.iterator();
    }

    /**
     * This returns the Iterator of this Manoeuvre Pile's Visible Cards
     *
     * @return the iterator
     */
    public Iterator<Card> getHiddenCardsIterator() {
        return hiddenCards.iterator();
    }

    /**
     * Returns the size of this Manoeuvre Pile's Hidden Cards
     *
     * @return the size
     */
    public int getHiddenCardsSize() {
        return hiddenCards.size();
    }

    /**
     * Determines if the Card population for this Manoeuvre Pile is complete
     *
     * @return {@code true} if the setup is complete, {@code false} otherwise
     */
    public boolean isSetupComplete() {
        return hiddenCards.size() + visibleCards.size() == pileId;
    }

    /**
     * This returns the last Card in this Manoeuvre Pile's Visible Cards
     *
     * @return the last Card
     */
    @Override
    public Card peek() {
        return visibleCards.peekLast();
    }

    /**
     * This appends the given Card to the end of the Visible Cards
     *
     * @param card the given Card
     * @return {@code true} if the Card was added, {@code false} otherwise
     */
    @Override
    public boolean appendCard(Card card) {
        return visibleCards.add(card);
    }

    /**
     * This appends the given Card to the end of the Visible Cards if the {@code visible} flag is {@code true}, otherwise it is appended to the Hidden Cards
     *
     * @param card    the given Card
     * @param visible the Card visibility flag
     */
    public void appendCard(Card card, boolean visible) {
        if (visible) {
            visibleCards.add(card);
            return;
        }

        hiddenCards.add(card);
    }

    /**
     * This returns a formatted String representation of this Manoeuvre Pile
     *
     * @return the formatted String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int hiddenCardsSize = getHiddenCardsSize();
        Iterator<Card> visiblePileIterator = getVisibleCardsIterator();
        Iterator<Card> hiddenPileIterator = getHiddenCardsIterator();

        if (cardVisibilitySetting == CardVisibility.VISIBLE) {
            while (hiddenPileIterator.hasNext()) {
                Card currCard = hiddenPileIterator.next();
                sb.append("(").append(currCard).append(")");
            }
            sb.append("|");
        } else {
            sb.repeat("[ - ] ", hiddenCardsSize);
        }

        while (visiblePileIterator.hasNext()) {
            Card currCard = visiblePileIterator.next();
            sb.append("[").append(currCard).append("] ");
        }

        return sb.toString();
    }
}
