package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

import java.util.Objects;

/**
 * Represents a Card in the Solitaire game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class Card implements Comparable<Card> {
    private Rank rank;
    private Suit suit;

    /**
     *
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Compares this Card and a given card for order based on their rank and suit.
     *
     * <p>
     * The primary criteria is the card's rank. If the ranks are equal, they are compared by their suit.
     * </p>
     *
     * @param otherCard the second Card to be compared
     * @return {@code 1} if Card {@code a} is greater than card {@code b};
     * {@code -1} if Card {@code a} is less than card {@code b};
     * {@code 0} if Card {@code a} is equal to card {@code b};
     */
    @Override
    public int compareTo(Card otherCard) {
        if (this.rank().getValue() > otherCard.rank().getValue()) {
            return 1;
        } else if (this.rank().getValue() < otherCard.rank().getValue()) {
            return -1;
        }

        if (this.suit().getValue() > otherCard.suit().getValue()) {
            return 1;
        } else if (this.suit().getValue() < otherCard.suit().getValue()) {
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
        String rankDisplay = switch (rank.getValue()) {
            case 1, 11, 12, 13 -> rank.toString();
            default -> String.valueOf(rank.getValue());
        };
        return suit + "-" + rankDisplay;
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
        if (otherCard == null) {
            return this.rank() == Rank.K;
        }
        String cardColor = this.suit().getColor();
        String otherCardColor = otherCard.suit().getColor();
        if (cardColor.compareTo(otherCardColor) == 0) {
            return false;
        }
        int cardRank = this.rank().getValue();
        int otherCardRank = otherCard.rank().getValue();

        return otherCardRank - cardRank == 1;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }
}
