package com.svi.solitaire.state;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;
import com.svi.solitaire.utils.CardUtil;

/**
 * Factory that constructs an instance of a Solitaire Game.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class GameFactory {
    public static Game createGame(Deck shuffledDeck, GameSettings gameSettings) {
        FoundationPile[] foundationPiles = new FoundationPile[4];
        ManoeuvrePile[] manoeuvrePiles = new ManoeuvrePile[7];
        for (int i = 1; i <= manoeuvrePiles.length; i++) {
            if (i < 5) {
                foundationPiles[i - 1] = new FoundationPile();
            }
            manoeuvrePiles[i - 1] = new ManoeuvrePile(i, gameSettings.getCardVisibilitySetting());
        }

        Deck remainingCards = CardUtil.dealManoeuvreCards(shuffledDeck, manoeuvrePiles);
        TalonPile talonPile = new TalonPile(remainingCards, gameSettings.getDrawCount());

        return new Game(foundationPiles, manoeuvrePiles, talonPile);
    }
}
