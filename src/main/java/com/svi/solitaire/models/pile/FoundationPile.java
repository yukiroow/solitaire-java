package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Represents the Foundation Pile in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class FoundationPile implements CardPile {
    private Deque<Card> cards;
    private Suit pileSuit;

    /**
     * Construct an empty Foundation Pile
     */
    public FoundationPile() {
        this.cards = new ArrayDeque<>();
        pileSuit = null;
    }

    /**
     * This returns the last Card of this Foundation Pile
     *
     * @return the last Card
     */
    @Override
    public Card peek() {
        return cards.peekLast();
    }

    /**
     * This adds the given Card to this Foundation Pile
     *
     * <p>
     * A Card can only be appended if it has the same Suit as this Foundation Pile
     * </p>
     * <p>
     * If this Foundation Pile is still empty and the given Card is of Rank Ace, the {@code pileSuit} is assigned to the Suit of the given Card and is appended.
     * </p>
     *
     * @param card the given Card
     * @return {@code true} if the Card was appended, {@code false} otherwise
     */
    @Override
    public boolean appendCard(Card card) {
        if (cards.isEmpty()) {
            if (card.rank() == Rank.A) {
                this.pileSuit = card.suit();
                cards.add(card);
                return true;
            }
            return false;
        }
        if (card.suit() != pileSuit) {
            return false;
        }
        int topCardRank = peek().rank().getValue();
        int givenCardRank = card.rank().getValue();
        if (topCardRank == givenCardRank - 1) {
            cards.add(card);
            return true;
        }
        return false;
    }

    /**
     * This returns a formatted String representation of this Foundation Pile
     *
     * @return the formatted String
     */
    @Override
    public String toString() {
        Card topCard = peek();
        return topCard == null ? "[ - ]" : ("[" + topCard + "]");
    }
}
