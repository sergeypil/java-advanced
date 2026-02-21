package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RomanConverterTest {

    @Test
    void test1() {
        assertEquals("I", RomanConverter.toRoman(1));
    }

    @Test
    void test4() {
        assertEquals("IV", RomanConverter.toRoman(4));
    }

    @Test
    void test9() {
        assertEquals("IX", RomanConverter.toRoman(9));
    }

    @Test
    void test58() {
        assertEquals("LVIII", RomanConverter.toRoman(58));
    }
}