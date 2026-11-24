package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;

/**
 * Interface that represents a Card Pile in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public interface CardPile {
    Card peek();
    boolean appendCard(Card card);
}
