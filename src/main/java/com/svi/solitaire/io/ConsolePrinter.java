package com.svi.solitaire.io;

import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.GameSettings;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

public class ConsolePrinter {
    public static void printDeckState(Game gameInstance) {
        int round = gameInstance.getRounds();
        ManoeuvrePile[] manoeuvrePiles = gameInstance.getManoeuvrePiles();
        FoundationPile[] foundationPiles = gameInstance.getFoundationPiles();
        TalonPile talonPile = gameInstance.getTalonPile();

        System.out.println("-----------------------------------------");
        System.out.println("ROUND " + round);
        System.out.println("STOCK: " + talonPile.getStockPileSize() + " Cards");
        System.out.println("WASTE: " + talonPile);
        System.out.print("FOUNDATION PILES: ");
        for (FoundationPile foundationPile : foundationPiles) {
            System.out.printf("%-7s", foundationPile);
        }
        System.out.print("\nMANOEUVRE PILES: \n");
        for (ManoeuvrePile manoeuvrePile : manoeuvrePiles) {
            System.out.print("[" + (manoeuvrePile.getPileId()) + "] : ");
            System.out.println(manoeuvrePile);
        }
    }

    public static void printGameSettings(GameSettings gameSettings) {
        String drawCountDisplay = gameSettings.getDrawCount() == 1 ? "1 Card" : "3 Cards";
        System.out.println("-----------------------------------------");
        System.out.println("GAME SETTINGS");
        System.out.printf("%-20s%s%n", "Draw Count:", drawCountDisplay);
        System.out.printf("%-20s%s%n", "Shuffle Type:", gameSettings.getShuffleType());
        System.out.printf("%-20s%s%n", "Card Visibility:", gameSettings.getCardVisibilitySetting());
    }

    public static void printGameSummary(Game gameInstance) {
        System.out.println("-----------------------------------------");
        System.out.println("GAME SUMMARY");
        System.out.println("Total Rounds: " + gameInstance.getRounds());
        System.out.print("FOUNDATION PILES: ");
        for (FoundationPile foundationPile : gameInstance.getFoundationPiles()) {
            System.out.printf("%-7s", foundationPile);
        }
        System.out.println();
    }
}
