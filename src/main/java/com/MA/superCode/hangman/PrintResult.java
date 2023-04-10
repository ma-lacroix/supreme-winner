package com.MA.superCode.hangman;

import java.util.List;

public class PrintResult {

    // a utils class that prints out game elements

    public static void printHangMan(int healthPoint) {
        // TODO: make the hangman appear based on health points left
    }

    public static void printGuesses(List<Character> guesses) {
        for (Character c: guesses) {
            System.out.print(c + " ");
        }
        System.out.println("\n");
    }
}
