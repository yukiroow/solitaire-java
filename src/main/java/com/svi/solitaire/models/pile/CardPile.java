package com.svi.solitaire.models.pile;

import com.svi.solitaire.models.Card;

public interface CardPile {
    Card peek();
    void appendCard(Card card);
}
