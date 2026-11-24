package com.svi.solitaire.models;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.enums.GameType;
import com.svi.solitaire.enums.Shuffle;

/**
 * Represents the Game Settings inputted by the User.
 *
 * @author Harry Dominguez
 * @since 1.0
 */
public class GameSettings {
    private int drawCount;
    private Shuffle shuffleType;
    private CardVisibility cardVisibilitySetting;
    private GameType gameType;
    private int gameCount;

    /**
     * Construct a Game Settings object for a Single Game Instance.
     *
     * @param drawCount             the DrawType
     * @param shuffleType           the ShuffleType
     * @param cardVisibilitySetting the VisibilitySetting
     * @param gameType              the GameType
     */
    public GameSettings(int drawCount, Shuffle shuffleType, CardVisibility cardVisibilitySetting, GameType gameType) {
        this.drawCount = drawCount;
        this.shuffleType = shuffleType;
        this.cardVisibilitySetting = cardVisibilitySetting;
        this.gameType = gameType;
    }

    /**
     * Construct a Game Settings object for a Multi Game Instance.
     *
     * @param drawCount             the number of Card draws
     * @param shuffleType           the ShuffleType
     * @param cardVisibilitySetting the VisibilitySetting
     * @param gameType              the GameType
     * @param gameCount             the number of games
     */
    public GameSettings(int drawCount, Shuffle shuffleType, CardVisibility cardVisibilitySetting, GameType gameType, int gameCount) {
        this.drawCount = drawCount;
        this.shuffleType = shuffleType;
        this.cardVisibilitySetting = cardVisibilitySetting;
        this.gameType = gameType;
        this.gameCount = gameCount;
    }

    /**
     * This returns the number of Card draws
     *
     * @return the number of Card draws
     */
    public int getDrawCount() {
        return drawCount;
    }

    /**
     * This returns the Shuffle Type
     *
     * @return the ShuffleType
     */
    public Shuffle getShuffleType() {
        return shuffleType;
    }

    /**
     * This returns the Card Visibility Setting
     *
     * @return the CardVisibility
     */
    public CardVisibility getCardVisibilitySetting() {
        return cardVisibilitySetting;
    }

    /**
     * This returns the Game Type
     *
     * @return the GameType
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * This returns the number of Games
     *
     * @return the number of Games
     */
    public int getGameCount() {
        return gameCount;
    }
}
