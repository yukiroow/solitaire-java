package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

import java.util.Objects;

/**
 * Represents a Card in the War Cards game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public record Card(Rank rank, Suit suit) {
    /**
     * Compares two Cards for order based on their rank and suit.
     *
     * <p>
     * The primary criteria is the card's rank. If the rank's are equal, they are compared by their suit.
     * </p>
     *
     * @param a the first Card to be compared
     * @param b the second Card to be compared
     * @return {@code 1} if Card {@code a} is greater than card {@code b};
     * {@code -1} if Card {@code a} is less than card {@code b};
     * {@code 0} if Card {@code a} is equal to card {@code b};
     */
    public static int compare(Card a, Card b) {
        if (a.rank().getValue() > b.rank().getValue()) {
            return 1;
        } else if (a.rank().getValue() < b.rank().getValue()) {
            return -1;
        }

        if (a.suit().getValue() > b.suit().getValue()) {
            return 1;
        } else if (a.suit().getValue() < b.suit().getValue()) {
            return -1;
        }

        return 0;
    }

    /**
     * Returns a colored and symboled string representation of this Card.
     *
     * <p>
     * The output includes terminal ANSI escape codes to dynamically style the card's
     * appearance based on its suit:
     * <ul>
     *   <li>Red suits are rendered with a crimson background and black text.</li>
     *   <li>Black suits are rendered with a dark background and white text.</li>
     * </ul>
     *
     * @return a rich-text ANSI string representing the card's suit symbol, name, and rank
     */
    @Override
    public String toString() {
        String symbol = switch (suit) {
            case Suit.D -> "♦";
            case Suit.H -> "♥";
            case Suit.S -> "♠";
            case Suit.C -> "♣";
        };
        return (suit.getValue() > 2 ? "\u001B[48;2;224;17;95m\u001B[30m" : "\u001B[40m\u001B[37m") + symbol + suit + "-" + (rank.getValue() < 11 ? rank.getValue() : rank) + "\u001B[0m";
    }

    /**
     * Compares the memory address and Card-Suit of the given card.
     *
     * @param o the reference object with which to compare.
     * @return {@code true} if this Card is equal to given object.
     * {@code false} if this Card is not equal to given object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    /**
     * Returns a Hash generated from the Rank and Suit to ensure Card integrity.
     *
     * @return the generated hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    public boolean isAppendableTo(Card otherCard) {
        if (!this.suit().equals(otherCard.suit())) {
            return false;
        }
        int cardRank = this.rank().getValue();
        int otherCardRank = otherCard.rank().getValue();
        return cardRank - otherCardRank == 1;
    }
}
