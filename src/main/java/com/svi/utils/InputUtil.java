package com.svi.utils;

import java.util.Scanner;

public class InputUtil {
    public static int readSettingInput(String prompt, Scanner keyboardInput) {
        int choice;
        while (true) {
            System.out.print(prompt + "\n> ");
            try {
                choice = Integer.parseInt(keyboardInput.nextLine().trim());
                if (choice < 1 || choice > 2) {
                    System.out.println("Please select a valid option.");
                    continue;
                }
                return choice;
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid value.");
            }
        }
    }
}
