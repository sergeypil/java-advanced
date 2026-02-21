package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimeCheckerTest {

    @Test
    void testPrime() {
        assertTrue(PrimeChecker.isPrime(5));
    }

    @Test
    void testNotPrime() {
        assertFalse(PrimeChecker.isPrime(1));
    }

    @Test
    void testNegative() {
        assertFalse(PrimeChecker.isPrime(-7));
    }

    @Test
    void testTwo() {
        assertTrue(PrimeChecker.isPrime(2));
    }
}