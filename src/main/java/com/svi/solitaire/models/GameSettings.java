package com.svi.solitaire.models;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.Shuffle;

public class GameSettings {
    private int drawCount;
    private Shuffle shuffleType;
    private CardVisibility cardVisibilitySetting;

    public GameSettings(int drawCount, Shuffle shuffleType, CardVisibility cardVisibilitySetting) {
        this.drawCount = drawCount;
        this.shuffleType = shuffleType;
        this.cardVisibilitySetting = cardVisibilitySetting;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public Shuffle getShuffleType() {
        return shuffleType;
    }

    public CardVisibility getCardVisibilitySetting() {
        return cardVisibilitySetting;
    }
}
