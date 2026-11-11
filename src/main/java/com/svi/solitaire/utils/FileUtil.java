package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.io.*;
import java.util.*;

public class FileUtil {
    /**
     * This returns a Deck object parsed from the given input file.
     *
     * @param inputFileName the InputStream of the input file
     * @return the Deck object parsed from the file
     */
    public static Deck loadDeckFromFile(String inputFileName) {
        Scanner fileScanner;
        String rawFile = "";
        File deckFile = new File(inputFileName);
        try (InputStream rawFileStream = new FileInputStream(deckFile)) {
            fileScanner = new Scanner(Objects.requireNonNull(rawFileStream));
            rawFile = fileScanner.nextLine();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Input file not found. Please verify and try again.");
            System.exit(1);
        } catch (NullPointerException | IOException e) {
            System.out.println("There was an unexpected problem with the input file. Please verify and try again.");
            System.exit(1);
        } catch (NoSuchElementException nsee) {
            System.out.println("The input file is empty.\nPlease verify resources directory.");
            System.exit(1);
        }

        return parseToDeck(rawFile);
    }

    /**
     * This parses the content of the given string to Card Objects and returns it all as a Deck object.
     *
     * <p>
     * {@code StringTokenizer} is used to separate the card values (,) as well as the Suit-Rank values (-)
     * </p>
     *
     * @param rawFile the given string
     * @return the Deck object parsed from the string
     */
    private static Deck parseToDeck(String rawFile) {
        ArrayList<Card> parsedCards = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(rawFile, ",");
        while (tokenizer.hasMoreTokens()) {
            StringTokenizer innerTokenizer = new StringTokenizer(tokenizer.nextToken(), "-");
            String suitVal = innerTokenizer.nextToken().trim();
            String rawRank = innerTokenizer.nextToken().trim();
            Card currCard = parseCard(suitVal, rawRank);
            parsedCards.add(currCard);
        }

        if (!validateLoadedDeck(parsedCards)) {
            System.out.println("There was a discrepancy in the amount of Cards loaded.\nPlease verify the input.txt file and reload.");
            System.exit(0);
        }

        return new Deck(parsedCards);
    }

    /**
     * Returns a Card object constructed from a given Suit and rank String.
     *
     * @param rawSuit the given Suit String
     * @param rawRank the given rank String
     * @return the constructed Card
     */
    private static Card parseCard(String rawSuit, String rawRank) {
        Card tempCard = null;
        Suit suitVal;
        Rank rankVal;
        try {
            suitVal = Suit.valueOf(rawSuit);
            try {
                rankVal = Rank.fromInt(Integer.parseInt(rawRank));
            } catch (NumberFormatException nfe) {
                rankVal = Rank.valueOf(rawRank);
            }
            tempCard = new Card(rankVal, suitVal);
        } catch (IllegalArgumentException iae) {
            System.out.println("Illegal characters have been detected.\nPlease verify the input.txt file and reload.");
            System.exit(0);
        }
        return tempCard;
    }

    /**
     * This validates the given list of Cards.
     *
     * <p>
     * The given list of cards must have 52 unique Card objects.
     * </p>
     *
     * @param parsedCards the given list of Cards
     * @return {@code true} if the given list of Cards is valid
     * {@code false} if the given list of Cards is invalid
     */
    private static boolean validateLoadedDeck(ArrayList<Card> parsedCards) {
        if (parsedCards.size() != 52) {
            return false;
        }

        List<Card> filteredCards = new ArrayList<>(parsedCards);

        filteredCards = filteredCards.stream()
                .distinct()
                .toList();

        return filteredCards.size() == 52;
    }
}
