package com.svi.solitaire.state;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.utils.CardUtil;
import com.svi.solitaire.io.FileLoader;

import java.io.InputStream;

public class DeckFactory {
    public static Deck createDeck(InputStream deckFile, Shuffle shuffleType) {
        Deck deck = FileLoader.loadDeckFromFile(deckFile);
        return switch (shuffleType) {
            case Shuffle.OUT_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.OUT_FARO);
            case Shuffle.IN_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.IN_FARO);
            case Shuffle.RANDOM -> CardUtil.doRandomShuffle(deck);
            case Shuffle.AS_IS -> deck;
        };
    }
}
