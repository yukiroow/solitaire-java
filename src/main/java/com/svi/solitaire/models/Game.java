package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

public class Game {
    private FoundationPile[] foundationPiles;
    private ManoeuvrePile[] manoeuvrePiles;
    private TalonPile talonPile;
    private int rounds;

    public Game(FoundationPile[] foundationStacks, ManoeuvrePile[] manoeuvreStacks, TalonPile talonStack) {
        this.foundationPiles = foundationStacks;
        this.manoeuvrePiles = manoeuvreStacks;
        this.talonPile = talonStack;
        this.rounds = 1;
    }

    public ManoeuvrePile[] getManoeuvrePiles() {
        return manoeuvrePiles;
    }

    public FoundationPile[] getFoundationPiles() {
        return foundationPiles;
    }

    public TalonPile getTalonPile() {
        return talonPile;
    }

    public int getRounds() {
        return rounds;
    }

    public void nextRound() {
        rounds++;
    }

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
}
