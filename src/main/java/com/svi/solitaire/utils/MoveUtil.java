package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class for Pile operations.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class MoveUtil {
    private static final int PRIORITY_MOVE = 2;
    private static final int NORMAL_MOVE = 1;

    public static boolean moveManoeuvreToManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile, boolean verbose) {
        String move = "";
        Iterator<Card> sourceVisibleCardIterator = sourcePile.getVisibleCardsIterator();
        Card targetCard = targetPile.peek();
        int bestCardIndex = getBestMoveCardIndex(sourceVisibleCardIterator, targetCard);

        if (bestCardIndex == -1) {
            return false;
        }

        sourceVisibleCardIterator = sourcePile.getVisibleCardsIterator();
        for (int currCardIndex = 0; currCardIndex < bestCardIndex; currCardIndex++) {
            sourceVisibleCardIterator.next();
        }

        boolean moveMade = false;
        Card prevCard = null;
        while (sourceVisibleCardIterator.hasNext()) {
            Card currCard = sourceVisibleCardIterator.next();
            if (isSkippable(prevCard, targetCard)) {
                continue;
            }
            if (targetPile.appendCard(currCard)) {
                move = "(M-M) MOVED: " + currCard + " -> Pile " + targetPile.getPileId();
                sourceVisibleCardIterator.remove();
                moveMade = true;
            }
            prevCard = currCard;
        }

        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard(verbose);
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }

        return moveMade;
    }

    public static void moveManoeuvreToFoundation(ManoeuvrePile sourcePile, FoundationPile targetPile, boolean verbose) {
        String move = "";
        Iterator<Card> sourceIterator = sourcePile.getVisibleCardsIterator();
        if (!sourceIterator.hasNext()) {
            return;
        }
        Card currCard = null;
        while (sourceIterator.hasNext()) currCard = sourceIterator.next();
        Card prevFoundationCard = targetPile.peek();
        if (targetPile.appendCard(currCard)) {
            move = "(M-F) MOVED: " + currCard + " -> " + prevFoundationCard;
            sourceIterator.remove();
        }
        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard(verbose);
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }
    }

    public static boolean moveTalonToManoeuvre(TalonPile sourcePile, ManoeuvrePile targetPile, boolean verbose) {
        String move = "";
        boolean moveMade = false;
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
            move = "(T-M) MOVED: " + sourceCard + " -> " + targetCard;
            moveMade = true;
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }

        return moveMade;
    }

    public static void moveTalonToFoundation(TalonPile sourcePile, FoundationPile targetPile, boolean verbose) {
        String move = "";
        Iterator<Card> sourceIterator = sourcePile.getWastePileIterator();
        if (!sourceIterator.hasNext()) {
            return;
        }
        Card currCard = sourceIterator.next();
        Card prevFoundationCard = targetPile.peek();
        if (targetPile.appendCard(currCard)) {
            move = "(T-F) MOVED: " + currCard + " -> " + prevFoundationCard;
            sourceIterator.remove();
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }
    }

    private static int getBestMoveCardIndex(Iterator<Card> iterator, Card targetCard) {
        List<Card> visibleCards = new ArrayList<>();
        int highestScore = 0;
        int bestCardIndex = -1;

        iterator.forEachRemaining(visibleCards::add);

        for (int currCardIndex = 0; currCardIndex < visibleCards.size(); currCardIndex++) {
            Card currCard = visibleCards.get(currCardIndex);
            if (!currCard.isAppendableTo(targetCard)) {
                continue;
            }
            int currScore = (currCardIndex == 0) ? PRIORITY_MOVE : NORMAL_MOVE;
            if (currScore > highestScore) {
                highestScore = currScore;
                bestCardIndex = currCardIndex;
            }
        }

        return bestCardIndex;
    }

    private static boolean isSkippable(Card prevCard, Card targetCard) {
        if (prevCard == null && targetCard == null) return true;
        return (prevCard != null && targetCard != null) && prevCard.rank() == targetCard.rank();
    }
}
