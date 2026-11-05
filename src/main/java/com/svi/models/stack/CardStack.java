package com.svi.models.stack;

import com.svi.models.Card;

public interface CardStack {
    Card peek();
    void appendCard(Card card);
}
