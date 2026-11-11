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

    public Game() {
        // TODO: Read from config
        deck = FileUtil.loadDeckFromFile("/deck.csv");
        manoeuvreStacks = new ManoeuvrePile[7];

        for (int i = 1; i <= manoeuvreStacks.length; i++) {
            manoeuvreStacks[i - 1] = new ManoeuvrePile(i);
        }

        Deck remainingCards = CardUtil.dealManoeuvreCards(deck, manoeuvreStacks);
        talonStack = new TalonPile(remainingCards, 3); // TODO: Read drawSetting from input

        for (ManoeuvrePile manoeuvreStack : manoeuvreStacks) {
            System.out.println(manoeuvreStack);
        }
    }
}
