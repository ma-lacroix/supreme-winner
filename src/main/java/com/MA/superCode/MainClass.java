package com.MA.superCode;

import com.MA.superCode.hangman.HandleGameInput;
import com.MA.superCode.hangman.PrintResult;

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        System.out.println("Start Game");
        HandleGameInput handleGameInput = new HandleGameInput();
        PrintResult printResult = new PrintResult();
        Scanner s = new Scanner(System.in);
        System.out.println(handleGameInput.getWord());
        do {
            System.out.println("Enter a new character: \n");
            char c = s.findInLine(".").charAt(0);
            handleGameInput.handleUserInput(c);
            printResult.printGuesses(handleGameInput.getGuesses());
            s.nextLine();
        }
        while (handleGameInput.getGameOn());
    }
}
