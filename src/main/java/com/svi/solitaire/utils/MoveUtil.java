package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Rank;
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
        Card targetCard = targetPile.peek();
        List<Card> visibleCards = new ArrayList<>();
        Iterator<Card> iterator = sourcePile.getVisibleCardsIterator();

        iterator.forEachRemaining(visibleCards::add);

        int bestCardIndex = getBestMoveCardIndex(visibleCards, targetCard);

        if (bestCardIndex == -1) {
            return false;
        }

        iterator = sourcePile.getVisibleCardsIterator();
        for (int currCardIndex = 0; currCardIndex < bestCardIndex; currCardIndex++) {
            iterator.next();
        }

        boolean moveMade = false;
        Card prevCard = null;
        while (iterator.hasNext()) {
            Card currCard = iterator.next();
            if (isSkippable(prevCard, currCard, targetCard)) {
                continue;
            }
            if (targetPile.appendCard(currCard)) {
                System.out.println("(M-M) MOVED: " + currCard + " -> Pile " + targetPile.getPileId());
                iterator.remove();
                moveMade = true;
            }
            prevCard = currCard;
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
        Card prevFoundationCard = targetPile.peek();
        if (targetPile.appendCard(currCard)) {
            System.out.println("(M-F) MOVED: " + currCard + " -> " + prevFoundationCard);
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
        Card prevFoundationCard = targetPile.peek();
        if (targetPile.appendCard(currCard)) {
            System.out.println("(T-F) MOVED: " + currCard + " -> " + prevFoundationCard);
            sourceIterator.remove();
        }
    }

    private static int getBestMoveCardIndex(List<Card> cards, Card targetCard) {
        int highestScore = 0;
        int bestCardIndex = -1;

        for (int currCardIndex = 0; currCardIndex < cards.size(); currCardIndex++) {
            Card currCard = cards.get(currCardIndex);
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

    private static boolean isSkippable(Card prevCard, Card currCard, Card targetCard) {
        // Prevent Move of Card to Same Rank Parent
        if ((prevCard != null && targetCard != null) && prevCard.rank() == targetCard.rank()) {
            return true;
        }

        // Prevent Move of King to Another Empty Pile
        if (currCard.rank() == Rank.K && targetCard == null) {
            return true;
        }

        return false;
    }
}
