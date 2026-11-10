package com.svi.solitaire.models.stack;

import com.svi.solitaire.models.Card;

public interface CardStack {
    Card peek();
    void appendCard(Card card);
}
