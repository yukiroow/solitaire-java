package com.svi.solitaire.utils.moves;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.ManoeuvrePile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Helper class for Manoeuvre Pile Card movement decisions.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class MoveHelper {
    private static final int PRIORITY_MOVE = 2;
    private static final int NORMAL_MOVE = 1;

    /**
     * This progresses the given Visible Cards Iterator to the position of the Best Card
     *
     * @param sourcePile                the source Manoeuvre Pile
     * @param sourceVisibleCardIterator the given Visible Cards Iterator
     * @param targetCard                the target Card
     * @return the progressed Iterator
     */
    public static Iterator<Card> updateIteratorToBestMoveCard(ManoeuvrePile sourcePile,
                                                              Iterator<Card> sourceVisibleCardIterator,
                                                              Card targetCard) {
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
     *     <li>Don't Skip if the current Card is King, the target Card is blank, and the current pile has Hidden Cards</li>
     *     <li>Skip if the rank of the previous and target Cards are the same</li>
     * </ul>
     *
     * @param prevCard   the previous card
     * @param targetCard the target card
     * @return {@code true} if the current Card is skippable, {@code false} if otherwise
     */
    public static boolean isSkippable(Card prevCard, Card targetCard, boolean prevHasHidden) {
        boolean currIsKing = prevCard == null;
        boolean targetIsBlank = targetCard == null;
        boolean prevAndTargetIsSameRank = (!currIsKing && !targetIsBlank) && prevCard.rank() == targetCard.rank();

        if (currIsKing && targetIsBlank && prevHasHidden) return false;

        return prevAndTargetIsSameRank;
    }
}
