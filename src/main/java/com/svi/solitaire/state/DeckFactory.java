package com.svi.solitaire.state;

import com.svi.solitaire.enums.Shuffle;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.utils.CardUtil;
import com.svi.solitaire.utils.FileUtil;

public class DeckFactory {
    public static Deck createDeck(String[] args, Shuffle shuffleType) {
        Deck deck = FileUtil.loadDeckFromFile(args);
        return switch (shuffleType) {
            case Shuffle.OUT_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.OUT_FARO);
            case Shuffle.IN_FARO -> CardUtil.doFaroShuffle(deck, Shuffle.IN_FARO);
            case Shuffle.RANDOM -> CardUtil.doRandomShuffle(deck);
            case Shuffle.AS_IS -> deck;
        };
    }
}
