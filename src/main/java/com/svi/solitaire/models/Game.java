package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an instance of a Solitaire Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class Game {
    private Map<String, Integer> gameStateFrequencies;
    private FoundationPile[] foundationPiles;
    private ManoeuvrePile[] manoeuvrePiles;
    private TalonPile talonPile;
    private int rounds;

    /**
     * Construct a Game Instance from the given Foundation Piles, Manoeuvre Piles, and Talon Pile.
     *
     * @param foundationPiles the given Foundation Piles
     * @param manoeuvrePiles  the given Manoeuvre Piles
     * @param talonPile       the Talon Pile
     */
    public Game(FoundationPile[] foundationPiles, ManoeuvrePile[] manoeuvrePiles, TalonPile talonPile) {
        gameStateFrequencies = new HashMap<>();
        this.foundationPiles = foundationPiles;
        this.manoeuvrePiles = manoeuvrePiles;
        this.talonPile = talonPile;
        this.rounds = 1;
    }

    /**
     * This returns this Game's Manoeuvre Piles
     *
     * @return the Manoeuvre Piles
     */
    public ManoeuvrePile[] getManoeuvrePiles() {
        return manoeuvrePiles;
    }

    /**
     * This returns this Game's Foundation Piles
     *
     * @return the Foundation Piles
     */
    public FoundationPile[] getFoundationPiles() {
        return foundationPiles;
    }

    /**
     * This returns this Game's Talon Pile
     *
     * @return the Talon Pile
     */
    public TalonPile getTalonPile() {
        return talonPile;
    }

    /**
     * This returns this Game's number of rounds
     *
     * @return the number of rounds
     */
    public int getRounds() {
        return rounds;
    }

    /**
     * This increments the round counter
     */
    public void nextRound() {
        rounds++;
    }

    /**
     * This determines if the Game is already won.
     *
     * <p>
     * A game is won if the top Card of every Foundation Pile is a {@code KING}
     * </p>
     *
     * @return {@code true} if the Game is won, {@code false} otherwise
     */
    public boolean isGameWon() {
        int score = 0;

        for (FoundationPile foundationPile : this.foundationPiles) {
            Card topCard = foundationPile.peek();
            if (topCard == null) {
                return false;
            }
            if (topCard.rank() == Rank.K) {
                score++;
            }
        }

        return score == 4;
    }

    /**
     * This determines if the Game is already lost.
     *
     * <p>
     * A game is lost if an exact state of the Game is repeated more than 2 times, indicating a deadlock.
     * </p>
     *
     * @return {@code true} if the Game is won, {@code false} otherwise
     */
    public boolean isGameOver() {
        boolean hasPingPong = false;
        List<Integer> frequencies = gameStateFrequencies.values().stream().toList();

        for (int frequency : frequencies) {
            if (frequency > 2) {
                hasPingPong = true;
                break;
            }
        }

        return hasPingPong;
    }

    /**
     * Record the exact state of the Game to the {@code gameStateFrequencies} Map.
     *
     * <p>
     * A Game state contains a Stringified version of every Manoeuvre Pile, Foundation Pile, and Talon Pile.
     * </p>
     */
    public void recordStateEntry() {
        StringBuilder stateEntry = new StringBuilder();

        for (ManoeuvrePile manoeuvrePile : manoeuvrePiles) {
            stateEntry.append(manoeuvrePile);
        }
        for (FoundationPile foundationPile : foundationPiles) {
            stateEntry.append(foundationPile);
        }
        stateEntry.append(talonPile);
        stateEntry.append("STOCK:").append(talonPile.getStockPileSize());

        String stateEntryString = stateEntry.toString();

        if (gameStateFrequencies.containsKey(stateEntryString)) {
            gameStateFrequencies.merge(stateEntryString, 1, Integer::sum);
        } else {
            gameStateFrequencies.put(stateEntryString, 1);
        }
    }
}
