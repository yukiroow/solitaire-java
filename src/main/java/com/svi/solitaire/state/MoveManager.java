package com.svi.solitaire.state;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;
import com.svi.solitaire.models.pile.TalonPile;

public class MoveManager {
    public static void moveInManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile) {
        // TODO: Implement moko boy
    }

    public static void moveManoeuvreToFoundation(ManoeuvrePile sourcePile, FoundationPile targetPile) {
        // TODO: Implement moko boy
    }

    public static void moveTalonToFoundation(TalonPile sourcePile, FoundationPile targetPile) {
        // TODO: Implement moko boy
    }

    public static void moveTalonToManoeuvre(TalonPile sourcePile, ManoeuvrePile targetPile) {
        // TODO: Implement moko boy
    }

    public static boolean canMoveToManoeuvre(Card sourceCard, ManoeuvrePile targetPile) {
        // TODO: Implement moko boy
        return true;
    }

    public static boolean canMoveToFoundation(Card sourceCard, FoundationPile targetPile) {
        // TODO: Implement moko boy
        return true;
    }
}
