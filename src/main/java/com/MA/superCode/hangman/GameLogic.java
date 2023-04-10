package com.MA.superCode.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameLogic {
    // Handles the game loop
    private boolean gameOn;
    private int healthPoints;
    private int correctGuesses;
    private List<String> words;
    private List<Character> guesses;
    private List<Character> misses;
    private final String word;
    private final Map<Character, List<Integer>> inv;
    private final Random randomizer = new Random();
    private PrintResult printResult;

    public GameLogic() {
        this.gameOn = true;
        this.healthPoints = 8;
        this.words = initWords();
        this.word = words.get(randomizer.nextInt(words.size()));
        this.correctGuesses = word.length() - 1;
        this.guesses = initGuesses();
        this.misses = new ArrayList<>();
        this.inv = initInv();
        this.printResult = new PrintResult();
    }

    public List<String> initWords() {
        // TODO: fetch data somewhere to add more words
         return new ArrayList<>(Arrays
                 .asList("hello","yellow","something", "jumbo"));
    }

    public List<Character> initGuesses() {
        List<Character> guesses = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            guesses.add('-');
        }
        return guesses;
    }

    private Map<Character, List<Integer>> initInv() {
        Map<Character, List<Integer>> inv = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            if (!inv.containsKey(word.charAt(i))) {
                inv.put(word.charAt(i), new ArrayList<>(List.of(i)));
            }else{
                inv.get(word.charAt(i)).add(i);
            }
        }
        return inv;
    }

    public boolean getGameOn() {
        return gameOn;
    }

    public void initialPrint() {
        printResult.printList(guesses);
    }

    public void handleUserInput(char c) {
        if (healthPoints == 0) {
            System.out.println("You lose!");
            gameOn = false;
        }
        if (correctGuesses == 0) {
            System.out.println("You win!");
            gameOn = false;
        }
        doesWordContainChar(c);
        printResultsInConsole();
    }

    public void doesWordContainChar(char c) {
        if (inv.containsKey(c)) {
            for (int ind: inv.get(c)) {
                guesses.set(ind, c);
                correctGuesses--;
            }
        } else {
            System.out.println("Try again!");
            misses.add(c);
            healthPoints--;
        }
    }

    public void printResultsInConsole() {
        System.out.println("Your word so far: ");
        printResult.printList(guesses);
        System.out.println("Your misses so far: ");
        printResult.printList(misses);
        printResult.printHangMan(healthPoints);
    }
}
