package com.MA.superCode;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.MA.superCode.hangman.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLogicTests {

    GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(1);
    }

    @Test
    void test_initWords() {
        assertNotNull(gameLogic.initWords());
    }

    @Test
    void test_initGuesses() {
        assertNotNull(gameLogic.initGuesses());
    }

    @Test
    void test_initInv() {
        assertNotNull(gameLogic.initInv());
    }

    @Test
    void test_handleUserInput_gameover() {
        GameLogic gl = new GameLogic(0);
        gl.handleUserInput('*');
        assertFalse(gl.getGameOn());
    }

    @Test
    void test_handleUserInput_lose() {
        GameLogic gl = new GameLogic(0);
        gl.handleUserInput('*');
        assertFalse(gl.getGameOn());
    }

    @Test
    void test_handleUserInput_win() {
        GameLogic gl = new GameLogic(0);
        gl.handleUserInput('*');
        assertFalse(gl.getGameOn());
    }
}
