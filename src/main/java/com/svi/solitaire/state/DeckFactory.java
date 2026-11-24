package com.svi.solitaire.state;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.io.FileLoader;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.utils.CardUtil;

import java.io.InputStream;

/**
 * Factory that constructs a Deck object.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class DeckFactory {
    /**
     * Construct a Deck object from the given Input Stream and Game Settings object.
     *
     * <p>
     * The order of Cards in the Deck depends on the {@code ShuffleType} field in the Game Settings object.
     * </p>
     *
     * @param deckFile     the given Input Stream
     * @param gameSettings the given Game Settings object
     * @return the constructed Deck object
     */
    public static Deck createDeck(InputStream deckFile, GameSettings gameSettings) {
        Deck deck = FileLoader.loadDeckFromFile(deckFile);

        return switch (gameSettings.getShuffleType()) {
            case Shuffle.OUT_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.OUT_FARO);
            case Shuffle.IN_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.IN_FARO);
            case Shuffle.RANDOM -> CardUtil.doRandomShuffle(deck);
            case Shuffle.AS_IS -> deck;
        };
    }
}
