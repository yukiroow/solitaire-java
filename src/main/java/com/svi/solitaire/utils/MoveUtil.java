package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveUtil {
    private static final int PRIORITY_MOVE = 2;
    private static final int NORMAL_MOVE = 1;

    public static boolean moveManoeuvreToManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile) {
        List<Card> visibleCards = new ArrayList<>();
        Iterator<Card> iterator = sourcePile.getVisibleCardsIterator();

        while (iterator.hasNext()) {
            visibleCards.add(iterator.next());
        }

        Card targetCard = targetPile.peek();

        int highestScore = 0;
        int bestCardIndex = -1;

        for (int i = 0; i < visibleCards.size(); i++) {
            Card currCard = visibleCards.get(i);
            if (!currCard.isAppendableTo(targetCard)) {
                continue;
            }
            int score = (i == 0) ? PRIORITY_MOVE : NORMAL_MOVE;
            if (score > highestScore) {
                highestScore = score;
                bestCardIndex = i;
            }
        }

        if (bestCardIndex == -1) {
            return false;
        }

        iterator = sourcePile.getVisibleCardsIterator();
        for (int currCardIndex = 0; currCardIndex < bestCardIndex; currCardIndex++) {
            iterator.next();
        }

        boolean moveMade = false;
        while (iterator.hasNext()) {
            Card currCard = iterator.next();
            if (targetPile.appendCard(currCard)) {
                System.out.println("(M-M) MOVED: " + currCard + " -> Pile " + targetPile.getPileId());
                iterator.remove();
                moveMade = true;
            }
        }

        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard();
        }

        return moveMade;
    }

    public static void moveManoeuvreToFoundation(ManoeuvrePile sourcePile, FoundationPile targetPile) {
        Iterator<Card> sourceIterator = sourcePile.getVisibleCardsIterator();
        if (!sourceIterator.hasNext()) {
            return;
        }
        Card currCard = null;
        while (sourceIterator.hasNext()) currCard = sourceIterator.next();
        if (targetPile.appendCard(currCard)) {
            System.out.println("(M-F) MOVED: " + currCard + " -> " + targetPile.peek());
            sourceIterator.remove();
        }
        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard();
        }
    }

    public static void moveTalonToManoeuvre(TalonPile sourcePile, ManoeuvrePile targetPile) {
        Card sourceCard = sourcePile.peek();
        Card targetCard = targetPile.peek();
        if (sourceCard == null) {
            return;
        }
        if (!sourceCard.isAppendableTo(targetCard)) {
            return;
        }
        sourceCard = sourcePile.playTopWaste();
        if (targetPile.appendCard(sourceCard)) {
            System.out.println("(T-M) MOVED: " + sourceCard + " -> " + targetCard);
        }
    }

    public static void moveTalonToFoundation(TalonPile sourcePile, FoundationPile targetPile) {
        Iterator<Card> sourceIterator = sourcePile.getWastePileIterator();
        if (!sourceIterator.hasNext()) {
            return;
        }
        Card currCard = sourceIterator.next();
        if (targetPile.appendCard(currCard)) {
            System.out.println("(T-F) MOVED: " + currCard + " -> " + targetPile.peek());
            sourceIterator.remove();
        }
    }
}
