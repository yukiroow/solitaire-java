package com.svi.solitaire.utils;

import com.svi.solitaire.enums.CardVisibility;
import com.svi.solitaire.exceptions.InvalidDeckFileException;
import com.svi.solitaire.exceptions.InvalidVisibilityArgumentException;
import com.svi.solitaire.io.ConsolePrinter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ArgsUtil {
    public static InputStream createInputStream(String[] args) {
        if (args.length < 1) {
            ConsolePrinter.printUsageGuide();
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
            String visibility = args[1].substring(1).toUpperCase();
            setting = CardVisibility.valueOf(visibility);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return CardVisibility.HIDDEN;
        } catch (IllegalArgumentException iae) {
            ConsolePrinter.printUsageGuide();
            throw new InvalidVisibilityArgumentException("Invalid Deck visibility argument provided.");
        }
        return setting;
    }
}
