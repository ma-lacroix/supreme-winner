package com.MA.superCode.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintResult {
    // a utils class that prints out game elements

    private static final List<String> drawing = new ArrayList<>(Arrays
            // ASCII art supposed to be a hang man...
            .asList("______",
                    " |   |",
                    " 0   |",
                    "---  |",
                    " |   |",
                    " ^   |",
                    " ||  |",
                    "     |",
                    "   ___"
            ));

    public static void printHangMan(int healthPoint) {
        for (int i = 0; i < (8 - healthPoint); i++) {
            System.out.println(drawing.get(i));
        }
    }

    public static void printList(List<Character> arr) {
        for (Character c: arr) {
            System.out.print(c + " ");
        }
        System.out.println("\n");
    }
}
