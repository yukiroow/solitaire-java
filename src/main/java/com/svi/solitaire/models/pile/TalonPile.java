package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Represents the Talon Pile, containing the Stock and Waste Piles, in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class TalonPile implements CardPile {
    private final int drawCount;
    private Deque<Card> stockPile;
    private Deque<Card> wastePile;

    /**
     * Construct a Talon Pile from the given Deck and draw count
     *
     * @param deck      the given Deck
     * @param drawCount the given draw count
     */
    public TalonPile(Deck deck, int drawCount) {
        this.stockPile = new ArrayDeque<>(deck.getCards());
        this.wastePile = new ArrayDeque<>();
        this.drawCount = drawCount;
    }

    /**
     * This returns the top Card in this Talon Pile's Waste Cards
     *
     * @return the top Card of the Waste Cards
     */
    @Override
    public Card peek() {
        return wastePile.peek();
    }

    /**
     * This appends the given Card to the back of this Talon Pile's Stock Cards
     *
     * @param card the given Card
     * @return {@code true} if the Card was appended, {@code false} otherwise
     */
    @Override
    public boolean appendCard(Card card) {
        return stockPile.add(card);
    }

    /**
     * Return and remove the top Card of this Talon Pile's Waste Cards
     *
     * @return the top Card of the Waste Cards
     */
    public Card playTopWaste() {
        return wastePile.poll();
    }

    /**
     * Determine if this Talon Pile's Stock Cards is empty
     *
     * @return {@code true} if the Stock Cards is empty, {@code false} otherwise
     */
    public boolean isStockEmpty() {
        return stockPile.isEmpty();
    }

    /**
     * Determine if this Talon Pile's Waste Cards is empty
     *
     * @return {@code true} if the Waste Cards is empty, {@code false} otherwise
     */
    public boolean isWasteEmpty() {
        return wastePile.isEmpty();
    }

    /**
     * This returns an Iterator of this Talon Pile's Waste Cards
     *
     * @return the Iterator
     */
    public Iterator<Card> getWastePileIterator() {
        return wastePile.iterator();
    }

    /**
     * This returns the size of this Talon Pile's Stock Cards
     *
     * @return the size
     */
    public int getStockPileSize() {
        return stockPile.size();
    }

    /**
     * Puts all the Cards in the Waste to the back of the Stock
     */
    public void resetPile() {
        while (!wastePile.isEmpty()) {
            stockPile.add(wastePile.pollLast());
        }
    }

    /**
     * Draw Cards from the top of the Stock Cards to the Waste Cards
     */
    public void drawCards(boolean verbose) {
        int actualDrawCount = 0;
        if (stockPile.isEmpty()) {
            return;
        }
        for (int count = 0; count < drawCount; count++) {
            Card currCard = stockPile.poll();

            if (currCard != null) {
                actualDrawCount++;
                wastePile.push(currCard);
            }
        }

        if (verbose && actualDrawCount > 0) {
            System.out.println(actualDrawCount + " CARD/s DRAWN FROM STOCK");
        }
    }

    /**
     * Returns a formatted String representation of this Talon Pile.
     *
     * @return the formatted String
     */
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

