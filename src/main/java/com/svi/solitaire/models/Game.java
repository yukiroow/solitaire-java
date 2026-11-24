package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Map<String, Integer> gameStateFrequencies;
    private FoundationPile[] foundationPiles;
    private ManoeuvrePile[] manoeuvrePiles;
    private TalonPile talonPile;
    private int rounds;

    public Game(FoundationPile[] foundationStacks, ManoeuvrePile[] manoeuvreStacks, TalonPile talonStack) {
        gameStateFrequencies = new HashMap<>();
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
