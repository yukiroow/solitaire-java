package com.svi.solitaire.models;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;

public class GameSettings {
    private int drawCount;
    private Shuffle shuffleType;
    private CardVisibility cardVisibilitySetting;
    private GameType gameType;
    private int gameCount;

    public GameSettings(int drawCount, Shuffle shuffleType, CardVisibility cardVisibilitySetting, GameType gameType) {
        this.drawCount = drawCount;
        this.shuffleType = shuffleType;
        this.cardVisibilitySetting = cardVisibilitySetting;
        this.gameType = gameType;
    }

    public GameSettings(int drawCount, Shuffle shuffleType, CardVisibility cardVisibilitySetting, GameType gameType, int gameCount) {
        this.drawCount = drawCount;
        this.shuffleType = shuffleType;
        this.cardVisibilitySetting = cardVisibilitySetting;
        this.gameType = gameType;
        this.gameCount = gameCount;
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

    public GameType getGameType() {
        return gameType;
    }

    public int getGameCount() {
        return gameCount;
    }
}
