package com.svi.utils;

import com.svi.models.Deck;
import com.svi.models.stack.FoundationStack;
import com.svi.models.stack.ManoeuvreStack;
import com.svi.models.stack.TalonStack;

public class CardUtil {
    public static Deck doRandomShuffle(Deck deck) {
        return null;
    }

    public static Deck doFaroShuffle(Deck deck) {
        return null;
    }

    public static void dealCards(Deck deck, ManoeuvreStack manoeuvreStack, TalonStack talonStack) {
        Deck remainingCards = dealManoeuvreCards(deck, manoeuvreStack);
        dealTalonCards(remainingCards, talonStack);
    }

    private static Deck dealManoeuvreCards(Deck deck, ManoeuvreStack manoeuvre) {
        return null;
    }

    private static void dealTalonCards(Deck deck, TalonStack talon) {}
}
