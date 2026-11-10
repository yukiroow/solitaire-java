package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.stack.ManoeuvreStack;

import java.util.ArrayList;
import java.util.Collections;

public class CardUtil {
    public static Deck doRandomShuffle(Deck deck) {
        Deck shuffledDeck = new Deck(deck);
        Collections.shuffle(shuffledDeck.cards());
        return shuffledDeck;
    }

    public static Deck doOutFaroShuffle(Deck deck) {
        int mid = deck.cards().size() / 2;
        ArrayList<Card> newDeck = new ArrayList<>();
        ArrayList<Card> firstHalf = new ArrayList<>(deck.cards().subList(0, mid));
        ArrayList<Card> secondHalf = new ArrayList<>(deck.cards().subList(mid, deck.cards().size()));
        for (int i = 0; i < mid; i++) {
            newDeck.add(firstHalf.get(i));
            newDeck.add(secondHalf.get(i));
        }
        return new Deck(newDeck);
    }

    public static Deck dealManoeuvreCards(Deck deck, ManoeuvreStack[] manoeuvreStacks) {
        int stackIndex = 0;
        int fullStacks = 0;
        int cutoffIndex = 0;

        for (int i = 0; i < deck.cards().size(); i++) {
            ManoeuvreStack stack = manoeuvreStacks[stackIndex];
            if (stack.isSetupComplete()) {
                fullStacks++;
                if (fullStacks == manoeuvreStacks.length) {
                    cutoffIndex = i;
                    break;
                }
                i--;
            } else {
                Card card = deck.cards().get(i);
                boolean visible = stack.getStackId() - 1 == stack.getHiddenCardsSize();
                stack.appendCard(card, visible);
                fullStacks = 0;
            }
            stackIndex = (stackIndex + 1) % manoeuvreStacks.length;
        }
        return new Deck(deck.cards().subList(cutoffIndex, deck.cards().size()));
    }
}
