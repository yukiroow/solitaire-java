package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;

public interface CardPile {
    Card peek();
    boolean appendCard(Card card);
}
