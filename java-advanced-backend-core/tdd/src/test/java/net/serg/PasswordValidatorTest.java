package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    @Test
    void testShort() {
        assertFalse(PasswordValidator.isValid("Abc1"));
    }

    @Test
    void testNoUpper() {
        assertFalse(PasswordValidator.isValid("abcdefg1"));
    }

    @Test
    void testNoLower() {
        assertFalse(PasswordValidator.isValid("ABCDEFG1"));
    }

    @Test
    void testNoDigit() {
        assertFalse(PasswordValidator.isValid("Abcdefgh"));
    }

    @Test
    void testValid() {
        assertTrue(PasswordValidator.isValid("Abcdefg1"));
    }
}