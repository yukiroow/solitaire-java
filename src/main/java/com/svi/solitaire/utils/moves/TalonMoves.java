package com.svi.solitaire.utils.moves;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.Iterator;
/**
 * Utility class for Talon Pile Card movement.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class TalonMoves {
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
    public static void moveTalonToManoeuvre(TalonPile sourcePile, ManoeuvrePile targetPile, boolean verbose) {
        String move = "";
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
            move = "(T-M) MOVED: " + sourceCard + " -> " + targetCard;
        }

        if (verbose && !move.isEmpty()) {
            System.out.println(move);
        }
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
}
