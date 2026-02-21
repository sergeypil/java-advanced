package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    @Test
    void testAllZeros() {
        BowlingGame g = new BowlingGame();
        rollMany(g, 20, 0);
        assertEquals(0, g.score());
    }

    @Test
    void testAllOnes() {
        BowlingGame g = new BowlingGame();
        rollMany(g, 20, 1);
        assertEquals(20, g.score());
    }

    @Test
    void testSpare() {
        BowlingGame g = new BowlingGame();
        g.roll(5);
        g.roll(5);
        g.roll(3);
        rollMany(g, 17, 0);
        assertEquals(16, g.score());
    }

    @Test
    void testStrike() {
        BowlingGame g = new BowlingGame();
        g.roll(10);
        g.roll(3);
        g.roll(4);
        rollMany(g, 16, 0);
        assertEquals(24, g.score());
    }

    @Test
    void testPerfectGame() {
        BowlingGame g = new BowlingGame();
        rollMany(g, 12, 10);
        assertEquals(300, g.score());
    }

    private void rollMany(BowlingGame g, int n, int pins) {
        for (int i = 0; i < n; i++) {
            g.roll(pins);
        }
    }
}