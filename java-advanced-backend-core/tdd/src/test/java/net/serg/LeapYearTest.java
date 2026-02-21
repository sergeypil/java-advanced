package net.serg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeapYearTest {

    @Test
    void testLeapYear() {
        assertTrue(LeapYear.isLeap(2000));
    }

    @Test
    void testNotLeapYear() {
        assertFalse(LeapYear.isLeap(1900));
    }

    @Test
    void testLeapYear4() {
        assertTrue(LeapYear.isLeap(2012));
    }

    @Test
    void testNotLeapYearNormal() {
        assertFalse(LeapYear.isLeap(2019));
    }
}