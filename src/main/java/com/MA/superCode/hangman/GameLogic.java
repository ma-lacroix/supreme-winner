package com.MA.superCode.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameLogic {
    // Before the game starts, this class is called to handle the input of the game
    // Let's say it's a collection of random words
    private boolean gameOn;
    private int healthPoints;
    private int correctGuesses;
    private List<String> words = new ArrayList<>(Arrays
            .asList("hello","yellow","something"));
    private List<Character> guesses;
    private String word;
    private Map<Character, List<Integer>> inv;
    private Random randomizer = new Random();

    public GameLogic() {
        this.gameOn = true;
        this.healthPoints = 5;
        this.words = words;
        this.word = words.get(randomizer.nextInt(words.size()));
        this.correctGuesses = word.length() - 1;
        this.guesses = initGuesses();
        this.inv = initInv();
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
                inv.put(word.charAt(i), new ArrayList<>(Arrays.asList(i)));
            }else{
                inv.get(word.charAt(i)).add(i);
            }
        }
        return inv;
    }

    public String getWord() {
        return word;
    }

    public List<Character> getGuesses() {
        return guesses;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean getGameOn() {
        return gameOn;
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
    }

    public void doesWordContainChar(char c) {
        if (inv.containsKey(c)) {
            for (int ind: inv.get(c)) {
                guesses.set(ind, c);
                correctGuesses--;
            }
        } else {
            healthPoints--;
        }
    }
}
