package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCalculatorTest {

    @Test
    void testEmpty() {
        assertEquals(0, StringCalculator.add(""));
    }

    @Test
    void testOneNumber() {
        assertEquals(5, StringCalculator.add("5"));
    }

    @Test
    void testTwoNumbers() {
        assertEquals(8, StringCalculator.add("3,5"));
    }

    @Test
    void testNewline() {
        assertEquals(6, StringCalculator.add("1\n2,3"));
    }
}