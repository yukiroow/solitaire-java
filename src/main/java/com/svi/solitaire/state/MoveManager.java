package com.svi.solitaire.state;

import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.moves.ManoeuvreMoves;
import com.svi.solitaire.utils.moves.TalonMoves;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for performing Card moves in the Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class MoveManager {
    /**
     * Execute a move within the given array of Manoeuvre Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param manoeuvrePiles the given Manoeuvre Piles
     * @param verbose        the print flag
     */
    public static void moveManoeuvre(ManoeuvrePile[] manoeuvrePiles, boolean verbose) {
        Set<String> moves = new HashSet<>();

        for (int currManPileIndex = 0; currManPileIndex < manoeuvrePiles.length; currManPileIndex++) {
            ManoeuvrePile currManPile = manoeuvrePiles[currManPileIndex];

            for (int targetManPileIndex = 0; targetManPileIndex < manoeuvrePiles.length; targetManPileIndex++) {
                ManoeuvrePile targetManPile = manoeuvrePiles[targetManPileIndex];
                String moveEntry = Math.min(currManPileIndex, targetManPileIndex) + "->"
                        + Math.max(currManPileIndex, targetManPileIndex);

                if (currManPileIndex == targetManPileIndex) {
                    continue;
                }
                if (moves.contains(moveEntry)) {
                    continue;
                }

                boolean moveMade =
                        ManoeuvreMoves.moveManoeuvreToManoeuvre(currManPile, targetManPile, verbose);
                if (moveMade) {
                    moves.add(moveEntry);
                }
            }
        }
    }

    /**
     * Execute a move from the given array of Manoeuvre Piles to the given array of Foundation Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param manoeuvrePiles  the given Manoeuvre Piles
     * @param foundationPiles the given Foundation Piles
     * @param verbose         the print flag
     */
    public static void moveToFoundation(ManoeuvrePile[] manoeuvrePiles, FoundationPile[]
            foundationPiles, boolean verbose) {
        for (ManoeuvrePile currManPile : manoeuvrePiles) {
            for (FoundationPile targetPile : foundationPiles) {
                ManoeuvreMoves.moveManoeuvreToFoundation(currManPile, targetPile, verbose);
            }
        }
    }

    /**
     * Execute a move from the given Talon Pile to the given array of Manoeuvre Piles and Foundation Piles.
     *
     * <p>
     * The {@code verbose} flag determines whether the method will print operations to the console.
     * </p>
     *
     * @param talonPile       the given Talon Pile
     * @param manoeuvrePiles  the given Manoeuvre Pile
     * @param foundationPiles the given Foundation Pile
     * @param verbose         the print flag
     */
    public static void moveTalon(TalonPile talonPile, ManoeuvrePile[] manoeuvrePiles,
                                 FoundationPile[] foundationPiles, boolean verbose) {
        boolean hasDrawnCards = false;

        if (talonPile.isStockEmpty()) {
            talonPile.resetPile();
        }

        if (talonPile.isWasteEmpty()) {
            talonPile.drawCards(verbose);
            hasDrawnCards = true;
        }

        if (!hasDrawnCards) {
            talonPile.drawCards(verbose);
        }

        for (ManoeuvrePile targetPile : manoeuvrePiles) {
            TalonMoves.moveTalonToManoeuvre(talonPile, targetPile, verbose);
        }

        for (FoundationPile targetPile : foundationPiles) {
            TalonMoves.moveTalonToFoundation(talonPile, targetPile, verbose);
        }
    }
}
