package com.MA.superCode.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HandleGameInput {
    // Before the game starts, this class is called to handle the input of the game
    // Let's say it's a collection of random words
    private boolean gameOn;
    private int healthPoints;
    private int correctGuesses;
    private List<String> words = new ArrayList<>(Arrays
            .asList("hello","yellow","something"));
    private List<Character> guesses;
    private String word;
    private Random randomizer = new Random();

    public HandleGameInput() {
        this.gameOn = true;
        this.healthPoints = 10;
        this.words = words;
        this.word = words.get(randomizer.nextInt(words.size()));
        this.correctGuesses = word.length() - 1;
        this.guesses = initGuesses();
    }

    public String getWord() {
        return word;
    }

    public List<Character> getGuesses() {
        return guesses;
    }

    public List<Character> initGuesses() {
        List<Character> guesses = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            guesses.add('-');
        }
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
        // TODO: if character is found more than once, won't work!
        int ind = word.indexOf(c);
        if (ind != -1) {
            guesses.set(ind, c);
            correctGuesses--;
        } else {
            healthPoints--;
        }
    }
}
