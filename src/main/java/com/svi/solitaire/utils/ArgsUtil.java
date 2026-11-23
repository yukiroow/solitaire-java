package com.svi.solitaire.utils;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.exceptions.InvalidDeckFileException;
import com.svi.solitaire.exceptions.InvalidVisibilityArgumentException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ArgsUtil {
    public static InputStream createInputStream(String[] args) {
        if (args.length < 1) {
            printUsageGuide();
            throw new InvalidDeckFileException("Wrong number of arguments");
        }
        InputStream deckInputStream;
        try {
            String inputFileName = args[0];
            File deckFile = new File(inputFileName);
            deckInputStream = new FileInputStream(deckFile);
        } catch (FileNotFoundException fnfe) {
            throw new InvalidDeckFileException("Specified Deck File not found");
        }
        return deckInputStream;
    }

    public static CardVisibility parseCardVisibility(String[] args) {
        CardVisibility setting;
        try {
            String visibility = args[1].substring(1);
            setting = CardVisibility.valueOf(visibility);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return CardVisibility.HIDDEN;
        } catch (IllegalArgumentException iae) {
            printUsageGuide();
            throw new InvalidVisibilityArgumentException("Invalid Deck visibility argument provided.");
        }
        return setting;
    }

    private static void printUsageGuide() {
        System.out.println("""
                Usage: java -jar harrydom-solitaire.jar <deck-file> [-visible | -hidden]
                
                A command-line solitaire game using a custom deck file.
                
                Arguments:
                  <deck-file>    Path to the deck file (required)
                
                Options:
                  -visible       Start the game with all Manoeuvre cards shown
                  -hidden        Start the game with face-down Manoeuvre cards hidden (default)
                
                Examples:
                  java -jar harrydom-solitaire.jar deck.txt
                  java -jar harrydom-solitaire.jar deck.txt -visible
                """);
    }
}
