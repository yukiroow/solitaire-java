package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.pile.FoundationPile;
import com.svi.solitaire.models.pile.ManoeuvrePile;

import java.util.Iterator;

public class MoveUtil {
    public static void moveManoeuvreToManoeuvre(ManoeuvrePile sourcePile, ManoeuvrePile targetPile) {
        Iterator<Card> sourceIterator = sourcePile.getVisibleCardsIterator();
        Card targetCard = targetPile.peek();
        while (sourceIterator.hasNext()) {
            Card currCard = sourceIterator.next();
            if (!currCard.isAppendableTo(targetCard)) {
                continue;
            }
            targetPile.appendCard(currCard);
            sourceIterator.remove();
            if (sourcePile.isVisibleCardsEmpty()) {
                sourcePile.revealCard();
                break;
            }
        }
    }

    public static void moveManoeuvreToFoundation(ManoeuvrePile sourcePile, FoundationPile targetPile) {
        // TODO: Implement moko boy
    }
}
