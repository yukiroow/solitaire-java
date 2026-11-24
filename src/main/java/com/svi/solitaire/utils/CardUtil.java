package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.pile.ManoeuvrePile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for Card Operations.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class CardUtil {
    /**
     * This returns a new instance of a given Deck that has been randomly shuffled using the {@code Collections} package.
     *
     * @param deck the Deck to be shuffled
     * @return the shuffled Deck
     */
    public static Deck doRandomShuffle(Deck deck) {
        Deck shuffledDeck = new Deck(deck);
        Collections.shuffle(shuffledDeck.getCards());
        return shuffledDeck;
    }

    /**
     * This returns a new instance of a given Deck that has been Faro shuffled.
     *
     * <p>
     * The Faro shuffle depends on the given {@code ShuffleType}.
     * </p>
     *
     * @param deck        the Deck to be shuffled
     * @param shuffleType the given ShuffleType
     * @return the shuffled Deck
     */
    public static Deck doFaroShuffle(Deck deck, Shuffle shuffleType) {
        int mid = deck.getCards().size() / 2;
        ArrayList<Card> newDeck = new ArrayList<>();
        ArrayList<Card> firstHalf = new ArrayList<>(deck.getCards().subList(0, mid));
        ArrayList<Card> secondHalf = new ArrayList<>(deck.getCards().subList(mid, deck.getCards().size()));
        for (int i = 0; i < mid; i++) {
            if (shuffleType == Shuffle.OUT_FARO) {
                newDeck.add(firstHalf.get(i));
                newDeck.add(secondHalf.get(i));
            } else if (shuffleType == Shuffle.IN_FARO) {
                newDeck.add(secondHalf.get(i));
                newDeck.add(firstHalf.get(i));
            }
        }
        return new Deck(newDeck);
    }

    /**
     * This deals the Cards from the given Deck to the given array of Manoeuvre Piles.
     *
     * <p>
     * The remaining Cards not dealt are returned as a Deck object.
     * </p>
     *
     * @param deck            the given Deck
     * @param manoeuvreStacks the given array of Manoeuvre Piles
     * @return the remaining Cards
     */
    public static Deck dealManoeuvreCards(Deck deck, ManoeuvrePile[] manoeuvreStacks) {
        List<Card> remainingCards;
        int stackIndex = 0;
        int fullStacks = 0;
        int cutoffIndex = 0;

        for (int i = 0; i < deck.getCards().size(); i++) {
            ManoeuvrePile currPile = manoeuvreStacks[stackIndex];
            if (currPile.isSetupComplete()) {
                fullStacks++;
                if (fullStacks == manoeuvreStacks.length) {
                    cutoffIndex = i;
                    break;
                }
                i--;
            } else {
                Card card = deck.getCards().get(i);
                boolean visible = currPile.getPileId() - 1 == currPile.getHiddenCardsSize();
                currPile.appendCard(card, visible);
                fullStacks = 0;
            }
            stackIndex = (stackIndex + 1) % manoeuvreStacks.length;
        }

        remainingCards = deck.getCards().subList(cutoffIndex, deck.getCards().size());

        return new Deck(remainingCards);
    }
}
