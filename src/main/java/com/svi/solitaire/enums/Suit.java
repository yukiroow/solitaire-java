package com.svi.solitaire.enums;

/**
 * Represents the Suit of a Card.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public enum Suit {
    C(1, "BLACK"), S(2, "BLACK"), H(3, "RED"), D(4, "RED");

    private final int value;
    private final String color;

    /**
     * Constructs a Suit with an associated numeric value.
     *
     * @param value the numeric value of the card
     */
    Suit(int value, String color) {
        this.value = value;
        this.color = color;
    }

    /**
     * Returns the numeric value of this Suit.
     *
     * @return the integer value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the color String of this Suit
     *
     * @return the color String
     */
    public String getColor() {
        return color;
    }
}
