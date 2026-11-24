package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;

/**
 * Interface that represents a Card Pile in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public interface CardPile {
    /**
     * This returns a Card from this Pile
     *
     * @return the Card
     */
    Card peek();

    /**
     * This adds the given Card to this Pile
     *
     * @param card the given Card
     * @return {@code true} if the Card was added, {@code false} otherwise
     */
    boolean appendCard(Card card);
}
