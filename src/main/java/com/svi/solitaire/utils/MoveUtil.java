package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveUtil {
    public static void moveManoeuvreToManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile) {

        List<Card> visibleCards = new ArrayList<>();
        Iterator<Card> iterator = sourcePile.getVisibleCardsIterator();

        while (iterator.hasNext()) {
            visibleCards.add(iterator.next());
        }

        Card targetCard = targetPile.peek();

        int highestScore = 0;
        int bestMoveIndex = -1;

        // Evaluate all possible moves
        for (int i = 0; i < visibleCards.size(); i++) {
            Card currCard = visibleCards.get(i);

            if (!currCard.isAppendableTo(targetCard)) {
                continue;
            }

            // Score the possible move
            int score = (i == 0) ? 2 : 1;

            if (score > highestScore) {
                highestScore = score;
                bestMoveIndex = i;
            }
        }

        // No valid moves available
        if (bestMoveIndex == -1) {
            return;
        }

        // Execute the highest-scoring move
        iterator = sourcePile.getVisibleCardsIterator();

        for (int i = 0; i < bestMoveIndex; i++) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            Card currCard = iterator.next();

            if (targetPile.appendCard(currCard)) {
                System.out.println("(M-M) MOVED: " + currCard + " -> Pile " + targetPile.getPileId());

                iterator.remove();
            }
        }

        // Reveal the next card if all visible cards were moved
        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard();
        }
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

    public static boolean moveTalonToManoeuvre(TalonPile sourcePile, ManoeuvrePile targetPile) {
        Card sourceCard = sourcePile.peek();
        Card targetCard = targetPile.peek();
        if (sourceCard == null) {
            return false;
        }
        if (!sourceCard.isAppendableTo(targetCard)) {
            return false;
        }
        sourceCard = sourcePile.playTopWaste();
        if (targetPile.appendCard(sourceCard)) {
            System.out.println("(T-M) MOVED: " + sourceCard + " -> " + targetCard);
            return true;
        }
        return false;
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
