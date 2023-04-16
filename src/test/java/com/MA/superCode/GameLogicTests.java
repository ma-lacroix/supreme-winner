package com.MA.superCode;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.MA.superCode.hangman.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameLogicTests {

    GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
    }

    @Test
    void test_initWords() {
        assertNotNull(gameLogic.initWords());
    }

    @Test
    void test_initGuesses() {
        assertNotNull(gameLogic.initGuesses());
    }


}
