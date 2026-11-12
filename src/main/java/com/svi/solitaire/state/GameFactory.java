package com.svi.solitaire.state;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.CardUtil;

public class GameFactory {
    public static Game createGame(Deck shuffledDeck, int drawCount) {
        FoundationPile[] foundationPiles = new FoundationPile[4];
        ManoeuvrePile[] manoeuvrePiles = new ManoeuvrePile[7];
        for (int i = 1; i <= manoeuvrePiles.length; i++) {
            if (i < 5) {
                foundationPiles[i - 1] = new FoundationPile();
            }
            manoeuvrePiles[i - 1] = new ManoeuvrePile(i);
        }

        Deck remainingCards = CardUtil.dealManoeuvreCards(shuffledDeck, manoeuvrePiles);
        TalonPile talonPile = new TalonPile(remainingCards, drawCount);

        return new Game(shuffledDeck, foundationPiles, manoeuvrePiles, talonPile);
    }
}
