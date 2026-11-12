package com.svi.solitaire.models;

import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.CardUtil;
import com.svi.solitaire.utils.FileUtil;

public class Game {
    private Deck deck;
    private FoundationPile[] foundationStacks;
    private ManoeuvrePile[] manoeuvreStacks;
    private TalonPile talonStack;
    private int rounds;

    public Game(Deck deck, FoundationPile[] foundationStacks,  ManoeuvrePile[] manoeuvreStacks, TalonPile talonStack) {
        this.deck = deck;
        this.foundationStacks = foundationStacks;
        this.manoeuvreStacks = manoeuvreStacks;
        this.talonStack = talonStack;
        this.rounds = 1;
    }
}
