package com.svi.solitaire.utils.moves;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;

import java.util.Iterator;

/**
 * Utility class for Manoeuvre Pile Card movement.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class ManoeuvreMoves {
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
        sourceVisibleCardIterator = MoveHelper.updateIteratorToBestMoveCard(sourcePile, sourceVisibleCardIterator, targetCard);
        if (sourceVisibleCardIterator == null) return false;

        boolean prevHasHidden = sourcePile.getHiddenCardsSize() > 0;
        boolean moveMade = false;
        Card prevCard = null;
        while (sourceVisibleCardIterator.hasNext()) {
            Card currCard = sourceVisibleCardIterator.next();
            if (MoveHelper.isSkippable(prevCard, targetCard, prevHasHidden)) {
                continue;
            }
            if (targetPile.appendCard(currCard)) {
                move = "(M-M) MOVED: " + currCard + " -> Pile " + targetPile.getPileId();
                sourceVisibleCardIterator.remove();
                moveMade = true;
            }
            prevCard = currCard;
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }

        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard(verbose);
        }

        return moveMade;
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
            move = "(M-F) MOVED: " + currCard + " -> " + (prevFoundationCard != null ? prevFoundationCard : "Empty Foundation Pile");
            sourceIterator.remove();
        }

        if (sourcePile.isVisibleCardsEmpty()) {
            sourcePile.revealCard(verbose);
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }
    }
}
