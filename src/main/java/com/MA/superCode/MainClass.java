package com.MA.superCode;

import com.MA.superCode.hangman.GameLogic;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic(8);
        Scanner s = new Scanner(System.in);
        System.out.println("Start Game\nHere is your word: ");
        gameLogic.initialPrint();
        do {
            System.out.println("\nEnter a new character: \n");
            try {
                char c = s.findInLine(".").charAt(0);
                gameLogic.handleUserInput(c);
            } catch (Exception e) {
                System.out.println("Error - Enter a character and press enter!");
            }
            s.nextLine();
        }
        while (gameLogic.getGameOn());
    }
}
