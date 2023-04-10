package com.MA.superCode;

import com.MA.superCode.hangman.GameLogic;
import com.MA.superCode.hangman.PrintResult;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("Start Game");
        GameLogic gameLogic = new GameLogic();
        PrintResult printResult = new PrintResult();
        Scanner s = new Scanner(System.in);
        System.out.println(gameLogic.getWord());
        do {
            System.out.println("Enter a new character: \n");
            char c = s.findInLine(".").charAt(0);
            gameLogic.handleUserInput(c);
            printResult.printGuesses(gameLogic.getGuesses());
            s.nextLine();
        }
        while (gameLogic.getGameOn());
    }
}
