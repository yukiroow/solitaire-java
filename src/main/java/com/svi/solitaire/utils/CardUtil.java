package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.pile.ManoeuvrePile;

import java.util.ArrayList;
import java.util.Collections;

public class CardUtil {
    public static Deck doRandomShuffle(Deck deck) {
        Deck shuffledDeck = new Deck(deck);
        Collections.shuffle(shuffledDeck.getCards());
        return shuffledDeck;
    }

    public static Deck doOutFaroShuffle(Deck deck) {
        int mid = deck.getCards().size() / 2;
        ArrayList<Card> newDeck = new ArrayList<>();
        ArrayList<Card> firstHalf = new ArrayList<>(deck.getCards().subList(0, mid));
        ArrayList<Card> secondHalf = new ArrayList<>(deck.getCards().subList(mid, deck.getCards().size()));
        for (int i = 0; i < mid; i++) {
            newDeck.add(firstHalf.get(i));
            newDeck.add(secondHalf.get(i));
        }
        return new Deck(newDeck);
    }

    public static Deck dealManoeuvreCards(Deck deck, ManoeuvrePile[] manoeuvreStacks) {
        int stackIndex = 0;
        int fullStacks = 0;
        int cutoffIndex = 0;

        for (int i = 0; i < deck.getCards().size(); i++) {
            ManoeuvrePile stack = manoeuvreStacks[stackIndex];
            if (stack.isSetupComplete()) {
                fullStacks++;
                if (fullStacks == manoeuvreStacks.length) {
                    cutoffIndex = i;
                    break;
                }
                i--;
            } else {
                Card card = deck.getCards().get(i);
                boolean visible = stack.getStackId() - 1 == stack.getHiddenCardsSize();
                stack.appendCard(card, visible);
                fullStacks = 0;
            }
            stackIndex = (stackIndex + 1) % manoeuvreStacks.length;
        }
        return new Deck(deck.getCards().subList(cutoffIndex, deck.getCards().size()));
    }
}
