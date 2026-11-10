package com.svi.solitaire.models;

import com.svi.solitaire.App;
import com.svi.solitaire.models.stack.FoundationStack;
import com.svi.solitaire.models.stack.ManoeuvreStack;
import com.svi.solitaire.models.stack.TalonStack;
import com.svi.solitaire.utils.CardUtil;
import com.svi.solitaire.utils.InputUtil;

public class Game {
    private Deck deck;
    private FoundationStack[] foundationStacks;
    private ManoeuvreStack[] manoeuvreStacks;
    private TalonStack talonStack;

    public Game() {
        deck = InputUtil.loadDeck(App.class.getResourceAsStream("/deck.csv"));
        manoeuvreStacks = new ManoeuvreStack[7];

        for (int i = 1; i <= manoeuvreStacks.length; i++) {
            manoeuvreStacks[i - 1] = new ManoeuvreStack(i);
        }

        Deck remainingCards = CardUtil.dealManoeuvreCards(deck, manoeuvreStacks);
        talonStack = new TalonStack(remainingCards);

        for (ManoeuvreStack manoeuvreStack : manoeuvreStacks) {
            System.out.println(manoeuvreStack);
        }
    }
}
