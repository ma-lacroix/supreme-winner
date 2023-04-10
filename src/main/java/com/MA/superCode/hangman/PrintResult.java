package com.MA.superCode.hangman;

import java.util.List;

public class PrintResult {

    // this class will print out the game results
    public PrintResult() {
    }

    public void printHangMan(int healthPoint) {
        // TODO: make the hangman appear based on health points left
    }

    public void printGuesses(List<Character> guesses) {
        for (Character c: guesses) {
            System.out.print(c + " ");
        }
        System.out.println("\n ******** ");
    }
}
