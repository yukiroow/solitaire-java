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

    /**
     * Performs possible moves from a source {@code ManoeuvrePile} to a target {@code ManoeuvrePile}.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param sourcePile the source pile
     * @param targetPile the target pile
     * @param verbose    the print flag
     * @return {@code true} if a move was made, {@code false} otherwise.
     */
    public static boolean moveManoeuvreToManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile, boolean verbose) {
        String move = "";
        Iterator<Card> sourceVisibleCardIterator = sourcePile.getVisibleCardsIterator();
        Card targetCard = targetPile.peek();
        sourceVisibleCardIterator = updateIteratorToBestMoveCard(sourcePile, sourceVisibleCardIterator, targetCard);
        if (sourceVisibleCardIterator == null) return false;

        boolean prevHasHidden = sourcePile.getHiddenCardsSize() > 0;
        boolean moveMade = false;
        Card prevCard = null;
        while (sourceVisibleCardIterator.hasNext()) {
            Card currCard = sourceVisibleCardIterator.next();
            if (isSkippable(prevCard, targetCard, prevHasHidden)) {
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

    /**
     * This progresses the given Visible Cards Iterator to the position of the Best Card
     *
     * @param sourcePile                the source Manoeuvre Pile
     * @param sourceVisibleCardIterator the given Visible Cards Iterator
     * @param targetCard                the target Card
     * @return the progressed Iterator
     */
    private static Iterator<Card> updateIteratorToBestMoveCard(ManoeuvrePile sourcePile, Iterator<Card> sourceVisibleCardIterator, Card targetCard) {
        int bestCardIndex = getBestMoveCardIndex(sourceVisibleCardIterator, targetCard);

        if (bestCardIndex == -1) {
            return null;
        }

        sourceVisibleCardIterator = sourcePile.getVisibleCardsIterator();
        for (int currCardIndex = 0; currCardIndex < bestCardIndex; currCardIndex++) {
            sourceVisibleCardIterator.next();
        }
        return sourceVisibleCardIterator;
    }

    /**
     * Performs possible moves from a source {@code ManoeuvrePile} to a target {@code FoundationPile}.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param sourcePile the source pile
     * @param targetPile the target pile
     * @param verbose    the print flag
     */
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

    /**
     * Performs possible moves from a source {@code TalonPile} to a target {@code ManoeuvrePile}.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param sourcePile the source pile
     * @param targetPile the target pile
     * @param verbose    the print flag
     */
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

    /**
     * Performs possible moves from a source {@code TalonPile} to a target {@code FoundationPile}.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param sourcePile the source pile
     * @param targetPile the target pile
     * @param verbose    the print flag
     */
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

    /**
     * This determines the index of the Card from the given iterator with the best possible move.
     *
     * <p>
     * A Card that reveals a hidden Card is considered the best move. This Card is also the head of the given Iterator.
     * </p>
     * <p>
     * This method follows a Greedy approach where all the moves of the Cards in the iterator are checked.
     * </p>
     *
     * @param iterator   the given iterator
     * @param targetCard the target Card
     * @return the index of the Card with the best move
     */
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

    /**
     * This determines if the current Card can be skipped given the previous card and the target card.
     *
     * <p>
     * Special Conditions:
     * </p>
     * <ul>
     *     <li>Don't Skip if the previous Card is King, the target Card is blank, and the current pile has Hidden Cards</li>
     *     <li>Skip if the rank of the previous and target Cards are the same</li>
     * </ul>
     *
     * @param prevCard   the previous card
     * @param targetCard the target card
     * @return {@code true} if the current Card is skippable, {@code false} if otherwise
     */
    private static boolean isSkippable(Card prevCard, Card targetCard, boolean prevHasHidden) {
        boolean prevIsKing = prevCard == null;
        boolean targetIsBlank = targetCard == null;
        boolean prevAndTargetIsSameRank = (!prevIsKing && !targetIsBlank) && prevCard.rank() == targetCard.rank();

        if (prevIsKing && targetIsBlank && prevHasHidden) return false;

        return prevAndTargetIsSameRank;
    }
}
