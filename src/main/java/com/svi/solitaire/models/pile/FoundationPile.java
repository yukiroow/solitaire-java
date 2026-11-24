package com.svi.solitaire.models.pile;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class FoundationPile implements CardPile {
    private Deque<Card> cards;
    private Suit pileSuit;

    public FoundationPile() {
        this.cards = new ArrayDeque<>();
        pileSuit = null;
    }

    public Suit getPileSuit() {
        return pileSuit;
    }

    @Override
    public Card peek() {
        return cards.peekLast();
    }

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

    @Override
    public String toString() {
        Card topCard = peek();
        return topCard == null ? "[ - ]" : ("[" + topCard + "]");
    }
}
