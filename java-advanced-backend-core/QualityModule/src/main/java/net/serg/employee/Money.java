package net.serg.employee;

/**
 * Simple Money class for demonstration.
 */
public record Money(double amount) {

    @Override
    public String toString() {
        return String.format("$%.2f", amount);
    }
}