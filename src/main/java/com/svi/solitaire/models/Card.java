package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

public record Card(Suit suit, Rank rank) {
    @Override
    public String toString() {
        return suit.toString() + "-" + rank.toString();
    }
}
